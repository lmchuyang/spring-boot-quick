/*
 * Zenlayer.com Inc.
 * Copyright (c) 2014-2019 All Rights Reserved.
 */
package cloud.service;

import cloud.model.TradeDO;

import java.util.List;

public interface TradeService {

    public void insertTrade(List<TradeDO> info);

    public TradeDO getTrade(String id);

    boolean updateTrade(TradeDO info);

    boolean cancleTrade(TradeDO info);

    boolean getCompletecondition();

}
