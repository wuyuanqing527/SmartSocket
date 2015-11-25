package com.wuyuanqing.smartsocket.model;

import java.util.List;

public class ResSocketLsit {

	private String username;
	private List<Socketer> socketers;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<Socketer> getSocketers() {
		return socketers;
	}
	public void setSocketers(List<Socketer> socketers) {
		this.socketers = socketers;
	}
}
