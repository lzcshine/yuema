package com.thesis.yuema.common;

import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.thesis.yuema.chatserver.ChatRoomServer;


/**
 * @author:lzc
 * 2015-2-2 下午2:00:43
 */

public class ChatRoomServerListener implements ServletContextListener {

	private ChatRoomServerThread thread;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		thread = new ChatRoomServerThread();
		thread.start();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		try {
			thread.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private class ChatRoomServerThread extends Thread{

		public void close() throws IOException{
			ChatRoomServer.getInstance().close();
		}
		
		@Override
		public void run() {
			try {
				ChatRoomServer.getInstance().start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
