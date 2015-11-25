package com.wuyuanqing.smartsocket.model;

import java.sql.Date;

public class Socketer {

	private int socketId;
	private String socketName;//名字
	private String userName;//所属用户名字
	private float current;//电流
	private float voltage;//电压
	private float power;//功率
	private float energy;//电能
	private Date openTime;//打开时间
	private Date closeTime;//关闭时间
	private int workTime;//工作时间 /s
	private boolean onOff;//开关
	private String info;//插座信息
	
	
	public float getCurrent() {
		return current;
	}
	public void setCurrent(float current) {
		this.current = current;
	}
	public float getVoltage() {
		return voltage;
	}
	public void setVoltage(float voltage) {
		this.voltage = voltage;
	}
	public String getSocketName() {
		return socketName;
	}
	public void setSocketName(String socketName) {
		this.socketName = socketName;
	}
	public float getPower() {
		return current*voltage;
	}
	public void setPower(float power) {
		this.power = power;
	}
	public float getEnergy() {
		return energy;
	}
	public void setEnergy(float energy) {
		this.energy = energy;
	}
	public boolean isOnOff() {
		return onOff;
	}
	public void setOnOff(boolean onOff) {
		this.onOff = onOff;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public Date getOpenTime() {
		return openTime;
	}
	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}
	public Date getCloseTime() {
		return closeTime;
	}
	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}
	public int getWorkTime() {
		return workTime;
	}
	public void setWorkTime(int workTime) {
		this.workTime = workTime;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getSocketId() {
		return socketId;
	}
	public void setSocketId(int socketId) {
		this.socketId = socketId;
	}
}
