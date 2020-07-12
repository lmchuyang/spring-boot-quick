/*
 * Zenlayer.com Inc.
 * Copyright (c) 2014-2019 All Rights Reserved.
 */
package cloud.dao;

import cloud.model.CompleteConditionDO;
import cloud.model.TradeDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TradeMapper {

    public void insertTrade(@Param("info") TradeDO trade);

    public TradeDO getTrade(@Param("id") String id);

    public List<TradeDO> getSiteModel(Map<String, String> paramMap);

    public List<CompleteConditionDO> getCompletecondition(Map<String, String> paramMap);

}
