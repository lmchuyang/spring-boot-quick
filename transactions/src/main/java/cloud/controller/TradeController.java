/*
 * Zenlayer.com Inc.
 * Copyright (c) 2014-2019 All Rights Reserved.
 */
package cloud.controller;

import cloud.model.TradeDO;
import cloud.service.impl.TradeServiceImpl;
import io.lettuce.core.dynamic.annotation.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/trade")
public class TradeController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    TradeServiceImpl tradeService;

    @RequestMapping(value = "/insert", method = RequestMethod.GET)
    @ResponseBody
    public String insertTrrade(@Param("tradelist") List param) {
        try {
            tradeService.insertTrade(param);
        }catch (Exception es){
            es.printStackTrace();
            return "false";
        }
        return "OK";
    }

    @RequestMapping(value = "/getTrade", method = RequestMethod.GET)
    @ResponseBody
    public String getTrade(String id) {

        try {
            tradeService.getTrade(id);
        }catch (Exception es){
            es.printStackTrace();
            return "false";
        }
        return "OK";
    }

    @RequestMapping(value = "/updateTrade", method = RequestMethod.POST)
    @ResponseBody
    public String updateTrade(TradeDO param) {
        tradeService.updateTrade(param);
        return "OK";
    }

    @RequestMapping(value = "/cancleTrade", method = RequestMethod.GET)
    @ResponseBody
    public String cancleTrade(TradeDO param) {
        System.out.println("method: get_site");
        tradeService.cancleTrade(param);
        return "OK";
    }


}
