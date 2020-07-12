/*
 * Zenlayer.com Inc.
 * Copyright (c) 2014-2019 All Rights Reserved.
 */
package cloud.dao;

import cloud.model.CompleteConditionDO;
import cloud.model.PositionDO;
import cloud.model.TradeDO;
import org.apache.ibatis.annotations.Param;

import javax.swing.text.Position;
import java.util.List;
import java.util.Map;

public interface PositionMapper {

    public void insertPosotion(@Param("post") PositionDO postinfo);

    public void updatePosition(@Param("post") PositionDO postinfo);

    public PositionDO getPosition(@Param("type") String type);
}
