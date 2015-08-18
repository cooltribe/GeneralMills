package com.searun.GIS.attribute;

import java.sql.Timestamp;

/**
 * SysGps entity. @author MyEclipse Persistence Tools
 */
public class SysGpsDto implements java.io.Serializable {

	// Fields    

	/**
	 * 
	 */
	private static final long serialVersionUID = 391918118170279769L;
	private String dataId;
	
	/**
	 * 纬度
	 */
	private Double lat;
	
	/**
	 * 经度
	 */
	private Double lng;
	
	/**
	 * 调度单id
	 */
	private String dispatchInfoId;
	
	/**
	 * 调度单编号
	 */
	private String dispatchInfoNum;
	
	/**
	 * 车牌号
	 */
	private String vehicleNum;
	
	/**
	 * GPS上传时间
	 */
	private Timestamp gpsTime;

	// Constructors

	/** default constructor */
	public SysGpsDto() {
	}

	/** minimal constructor */
	public SysGpsDto(String dataId) {
		this.dataId = dataId;
	}

	/** full constructor */
	public SysGpsDto(String dataId, Double lat, Double lng, String dispatchInfoNum, String vehicleNum, Timestamp gpsTime) {
		this.dataId = dataId;
		this.lat = lat;
		this.lng = lng;
		this.dispatchInfoNum = dispatchInfoNum;
		this.vehicleNum = vehicleNum;
		this.gpsTime = gpsTime;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public String getDispatchInfoNum() {
		return dispatchInfoNum;
	}

	public void setDispatchInfoNum(String dispatchInfoNum) {
		this.dispatchInfoNum = dispatchInfoNum;
	}

	public String getVehicleNum() {
		return vehicleNum;
	}

	public void setVehicleNum(String vehicleNum) {
		this.vehicleNum = vehicleNum;
	}

	public Timestamp getGpsTime() {
		return gpsTime;
	}

	public void setGpsTime(Timestamp gpsTime) {
		this.gpsTime = gpsTime;
	}

	public String getDispatchInfoId() {
		return dispatchInfoId;
	}

	public void setDispatchInfoId(String dispatchInfoId) {
		this.dispatchInfoId = dispatchInfoId;
	}

}