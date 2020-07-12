/*
 * Zenlayer.com Inc.
 * Copyright (c) 2014-2019 All Rights Reserved.
 */
package cloud.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author lmc
 * @date 2019-09-25 17:51:49
 * @version $ Id: CompleteConditionDO.java, v 0.1  admin Exp $
 */
public class CompleteConditionDO {

/*  注解@JsonFormat主要是后台到前台的时间格式的转换
    注解@DataFormAT主要是前后到后台的时间格式的转换 */

    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date    acquisitionTime;//采集时间
    private Float   batteryVoltage;//电池电压
    private Float   engineSpeed;//发动机转速
    private Float   fuel;//燃料
    private Double  latitude;//纬度
    private Double  longitude;//经度
    private Float   totalDistance;//总里程
    private Float   vehBatSOC;//电池电量
    private Float   vehicleSpeed;//车速
    private String  vin;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date timestamp;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private String  plateLicenseNo;//车牌号
    //  private Date    searchTime;//查询时间，用于在Redis中表明当日前的历史最新一条记录是哪一天更新的
    private Float   temperature1;//温度备用
    private Integer humidity1;//湿度
    private Integer photoreceptor1;//光感

    public Integer getHumidity1() {
        return humidity1;
    }

    public void setHumidity1(Integer humidity1) {
        this.humidity1 = humidity1;
    }

    public Integer getPhotoreceptor1() {
        return photoreceptor1;
    }

    public void setPhotoreceptor1(Integer photoreceptor1) {
        this.photoreceptor1 = photoreceptor1;
    }

    public Date getTemptimestamp1() {
        return temptimestamp1;
    }

    public void setTemptimestamp1(Date temptimestamp1) {
        this.temptimestamp1 = temptimestamp1;
    }

    private Date temptimestamp1;//温湿度设备时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getEngineSpeed() {
        return engineSpeed;
    }

    public void setEngineSpeed(Float engineSpeed) {
        this.engineSpeed = engineSpeed;
    }

    public Float getFuel() {
        return fuel;
    }

    public void setFuel(Float fuel) {
        this.fuel = fuel;
    }

    public Float getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(Float totalDistance) {
        this.totalDistance = totalDistance;
    }

    public Float getVehBatSOC() {
        return vehBatSOC;
    }

    public void setVehBatSOC(Float vehBatSOC) {
        this.vehBatSOC = vehBatSOC;
    }

    public Float getVehicleSpeed() {
        return vehicleSpeed;
    }

    public void setVehicleSpeed(Float vehicleSpeed) {
        this.vehicleSpeed = vehicleSpeed;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getAcquisitionTime() {
        return acquisitionTime;
    }

    public void setAcquisitionTime(Date acquisitionTime) {
        this.acquisitionTime = acquisitionTime;
    }

    public Float getBatteryVoltage() {
        return batteryVoltage;
    }

    public void setBatteryVoltage(Float batteryVoltage) {
        this.batteryVoltage = batteryVoltage;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /*public Date getSearchTime() {
        return searchTime;
    }

    public void setSearchTime(Date searchTime) {
        this.searchTime = searchTime;
    }
*/
    public String getPlateLicenseNo() {
        return plateLicenseNo;
    }

    public void setPlateLicenseNo(String plateLicenseNo) {
        this.plateLicenseNo = plateLicenseNo;
    }

    public Float getTemperature1() {
        return temperature1;
    }

    public void setTemperature1(Float temperature1) {
        this.temperature1 = temperature1;
    }

}
