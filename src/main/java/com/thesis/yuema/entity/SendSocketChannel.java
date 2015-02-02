package com.thesis.yuema.entity;

import java.nio.channels.SocketChannel;

/**
 * @author:lzc
 * 2015-1-25 下午8:58:40
 */

public class SendSocketChannel {

	private String username;
	private SocketChannel socketChannel;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public SocketChannel getSocketChannel() {
		return socketChannel;
	}
	public void setSocketChannel(SocketChannel socketChannel) {
		this.socketChannel = socketChannel;
	}
	@Override
	public String toString() {
		return "SendSocketChannel [username=" + username + ", socketChannel="
				+ socketChannel + "]";
	}
}
