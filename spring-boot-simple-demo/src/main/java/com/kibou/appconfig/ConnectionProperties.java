package com.kibou.appconfig;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

//@Component 
//现由 @EnableConfigurationProperties 来启动该设置 所以可以不指定@Component在当前配置类上
@ConfigurationProperties(prefix = "connection")
public class ConnectionProperties {

	private String username;

	private List<InetAddress> remoteAddresses = new ArrayList<>();

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public List<InetAddress> getRemoteAddresses() {
		return remoteAddresses;
	}

	public void setRemoteAddresses(List<InetAddress> remoteAddresses) {
		this.remoteAddresses = remoteAddresses;
	}

}