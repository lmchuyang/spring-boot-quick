/*
 * Zenlayer.com Inc.
 * Copyright (c) 2014-2019 All Rights Reserved.
 */
package cloud.service.impl;

import cloud.dao.PositionMapper;
import cloud.dao.TradeMapper;
import cloud.model.PositionDO;
import cloud.model.TradeDO;
import cloud.service.TradeService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.swing.text.Position;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class TradeServiceImpl implements TradeService {

    // @Autowired
    //  RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RedissonClient redisson;

    @Autowired
    private TradeMapper    tradeMapper;
    @Autowired
    private PositionMapper positionMapper;

    @Resource
    private RedisTemplate redisTemplate;


    @Override
    @Transactional(rollbackFor=Exception.class)
    public void insertTrade(List<TradeDO> tradelist) {
        if (tradelist.size()!=2){
            return;
        }
//买和卖两条数据
        TradeDO tradeOn = tradelist.get(0);
        TradeDO tradesUp = tradelist.get(1);
        tradeOn.setVersion(tradeOn.getVersion()+1);
        tradesUp.setVersion(tradesUp.getVersion()+1);
        insertTradeInfo(tradeOn);
        insertTradeInfo(tradesUp);
//更新
        updatePositionInfo(tradeOn);
        updatePositionInfo(tradesUp);

    }

    private void insertTradeInfo(TradeDO trade){
        trade.setVersion(trade.getVersion()+1);
        tradeMapper.insertTrade(trade);
    }

    //分布式锁
    private void updatePositionInfo(TradeDO trade){

        PositionDO posiDo=null;
        PositionDO position =  positionMapper.getPosition(trade.getSecurityCode());
        RLock lock = redisson.getLock("position:id:"+position.getType());
        lock.lock(2, TimeUnit.SECONDS);
        boolean tryLock = lock.tryLock();
        try {
            if (tryLock) {
                if (trade.getTradeType().equals("buy")){
                    posiDo = new PositionDO();
                    posiDo.setAmount(posiDo.getAmount()+trade.getQuantity());
                }else if(trade.getTradeType().equals("sell")){
                    posiDo = new PositionDO();
                    posiDo.setAmount(posiDo.getAmount()-trade.getQuantity());
                }
                positionMapper.updatePosition(posiDo);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (tryLock) {
                lock.unlock();
            }
        }

    }

    @Override
    public TradeDO getTrade(String id) {
        Map paramMap = new HashMap();
        paramMap.put("name", "lmc");
        tradeMapper.getTrade(id);
        return null;
    }

    @Override
    public boolean updateTrade(TradeDO info) {
        info.setActionStatus("update");
        insertTradeInfo(info);
        return true;
    }

    @Override
    public boolean cancleTrade(TradeDO info) {
        TradeDO trade =  tradeMapper.getTrade(info.getId());
        RLock lock = redisson.getLock("position:id:"+trade.getId());
        lock.lock(2, TimeUnit.SECONDS);
        boolean tryLock = lock.tryLock();
        try {
            if (tryLock) {
                insertTradeInfo(info);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (tryLock) {
                lock.unlock();
            }
        }
        return true;
    }

    @Override
    public boolean getCompletecondition() {
        return false;
    }

}
	
	

