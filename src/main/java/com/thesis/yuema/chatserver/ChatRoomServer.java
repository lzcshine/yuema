package com.thesis.yuema.chatserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Controller;

import com.thesis.yuema.util.JPushUtil;
import com.thesis.yuema.util.JsonUtil;

/**
 * @author:lzc 2015-1-25 下午3:54:38
 */
@Controller
public class ChatRoomServer {
	
	private static ChatRoomServer chatRoomServer = null;
	
	private Connection conn = null;

	private Selector selector = null;
	private ServerSocketChannel server = null;
	private static final int port = 9999;
	private static boolean flag = true;
	private Charset charset = Charset.forName("UTF-8");
	// 用来记录在线人数，以及昵称
	private static Map<String,SocketChannel> onlineMap = Collections.synchronizedMap(new HashMap<String, SocketChannel>());
	
	private ExecutorService service = null;
	
	private ChatRoomServer(){
		
	}
	
	public static ChatRoomServer getInstance(){
		if (chatRoomServer == null){
			chatRoomServer = new ChatRoomServer();
		}
		return chatRoomServer;
	}
	
	public void start() throws IOException{
		getInstance().init();
	}
	
	public void close() throws IOException{
		System.out.println("server is closing...");
		flag = false;
		server.close();
		selector.close();
	}
	
	private void sqlConnectionInit(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost:3306/yuema";
			conn = DriverManager.getConnection(url, "lzc", "123");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void init() throws IOException {
		selector = Selector.open();
		server = ServerSocketChannel.open();
		server.bind(new InetSocketAddress(port));
		// 非阻塞的方式
		server.configureBlocking(false);
		// 注册到选择器上，设置为监听状态
		server.register(selector, SelectionKey.OP_ACCEPT);

		System.out.println("Server is listening now...");

		service = Executors.newFixedThreadPool(2);
		
		sqlConnectionInit();

		while (flag) {
			int readyChannels = selector.select();
//			System.out.println("onlineMap : "+onlineMap.size());
			if (readyChannels == 0)
				continue;
			Set<SelectionKey> selectedKeys = selector.selectedKeys(); // 可以通过这个方法，知道可用通道的集合
			Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
			while (keyIterator.hasNext()) {
				SelectionKey sk = (SelectionKey) keyIterator.next();
				keyIterator.remove();
				if (sk.isAcceptable()) {
					SocketChannel sc = server.accept();
					sc.configureBlocking(false);
					// 注册选择器，并设置为读取模式，收到一个连接请求，然后起一个SocketChannel，并注册到selector上，之后这个连接的数据，就由这个SocketChannel处理
					sc.register(selector, SelectionKey.OP_READ);
					System.out.println("Server is listening from client :"
							+ sc.getRemoteAddress());
					Map<String,Object> map = new HashMap<String, Object>();
					map.put("type", "init");
					sc.write(charset.encode(JsonUtil.toJson(map)));
//					service.execute(new AcceptHandler(sc,sk));
				}
				// 处理来自客户端的数据读取请求
				if (sk.isValid() && sk.isReadable()) {
//					service.execute(new MessageHandler(sk));
					SocketChannel sc = (SocketChannel) sk.channel();
					ByteBuffer buff = ByteBuffer.allocate(1024);
					StringBuilder content = new StringBuilder();
					try {
						while (sc.read(buff) > 0) {
							buff.flip();
							content.append(charset.decode(buff));

						}
						System.out.println("Server is listening from client "
								+ sc.getRemoteAddress() + " data rev is: " + content.toString());
						// 将此对应的channel设置为准备下一次接受数据
						sk.interestOps(SelectionKey.OP_READ);
					} catch (IOException io) {
						sk.cancel();
						if (sk.channel() != null) {
							try {
								sk.channel().close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
					if (content.length() > 0) {
						Map<String,Object> map = JsonUtil.toObject(content.toString(), Map.class);
						if (map.get("type").equals("register")){
							synchronized(onlineMap){
								String username = (String)map.get("username");
								if (onlineMap.containsKey(username)){
									if (onlineMap.get(username) != sc){
										onlineMap.put(username, sc);
									}
								}
								else{
									onlineMap.put(username, sc);
								}
							}
						}
						else if (map.get("type").equals("message")){
							try {
								BroadCast(sc, map);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}

				}
			}
		}
	}

	public void BroadCast(SocketChannel except, Map<String,Object> map) throws IOException {
		PreparedStatement ps = null;
		ResultSet set = null;
		try {
			String sql = "select u.username from chat_member c,user_info u where u.id=c.user_id and c.chat_id=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, (Integer)map.get("chatId"));
			set = ps.executeQuery();
			String username = (String)map.get("username");
			while(set.next()){
				String user = set.getString(1);
				if (!user.equals(username)){
					synchronized(onlineMap){
						if (onlineMap.containsKey(user)){
							onlineMap.get(user).write(charset.encode(JsonUtil.toJson(map)));
						}
						else{
							JPushUtil.pushCustomMessageToOne(user, map);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				set.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
////		List<String> users = chatServiceImpl.getUsernamesByChatId((Integer)map.get("chatId"));
//		List<String> users = new ArrayList<String>();
//		users.add("18825152222");
//		users.add("18825156526");
//		String username = (String)map.get("username");
//		if (users != null && users.size() > 1){
//			for (String user : users){
//				if (!user.equals(username)){
//					synchronized(onlineMap){
//						if (onlineMap.containsKey(user)){
//							onlineMap.get(user).write(charset.encode(JsonUtil.toJson(map)));
//						}
//						else{
//							JPushUtil.pushCustomMessageToOne(user, map);
//						}
//					}
//				}
//			}
//		}
	}
	
	public void deleteChatMsg(List<String> users, Map<String,Object> map) throws IOException{
		for (String user : users){
			synchronized(onlineMap){
				if (onlineMap.containsKey(user)){
					onlineMap.get(user).write(charset.encode(JsonUtil.toJson(map)));
				}
				else{
					JPushUtil.pushCustomMessageToOne(user, map);
				}
			}
		}
	}
	
	public void deleteUserChannel(List<String> users, Map<String,Object> map) throws IOException{
		for (String user : users){
			synchronized(onlineMap){
				if (onlineMap.containsKey(user)){
					onlineMap.get(user).write(charset.encode(JsonUtil.toJson(map)));
				}
				else{
					JPushUtil.pushCustomMessageToOne(user, map);
				}
			}
		}
	}

	private class AcceptHandler implements Runnable {

		private SocketChannel sc;
		
		private SelectionKey sk;

		public AcceptHandler(SocketChannel sc, SelectionKey sk) {
			this.sc = sc;
			this.sk = sk;
		}

		@Override
		public void run() {
			try {
				System.out.println("Server is listening from client :"
						+ sc.getRemoteAddress());
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("type", "init");
				sc.write(charset.encode(JsonUtil.toJson(map)));
			} catch (ClosedChannelException e) {
				sk.cancel();
				if (sk.channel() != null) {
					try {
						sc.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private class MessageHandler implements Runnable {

		private SelectionKey sk;

		public MessageHandler(SelectionKey sk) {
			this.sk = sk;
		}
		
		@Override
		public void run() {
			// 返回该SelectionKey对应的 Channel，其中有数据需要读取
			SocketChannel sc = (SocketChannel) sk.channel();
			ByteBuffer buff = ByteBuffer.allocate(1024);
			StringBuilder content = new StringBuilder();
			try {
				while (sc.read(buff) > 0) {
					buff.flip();
					content.append(charset.decode(buff));

				}
				System.out.println("Server is listening from client "
						+ sc.getRemoteAddress() + " data rev is: " + content.toString());
				// 将此对应的channel设置为准备下一次接受数据
				sk.interestOps(SelectionKey.OP_READ);
			} catch (IOException io) {
				sk.cancel();
				if (sk.channel() != null) {
					try {
						sk.channel().close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			if (content.length() > 0) {
				Map<String,Object> map = JsonUtil.toObject(content.toString(), Map.class);
				if (map.get("type").equals("register")){
					synchronized(onlineMap){
						String username = (String)map.get("username");
						if (onlineMap.containsKey(username)){
							if (onlineMap.get(username) != sc){
								onlineMap.put(username, sc);
							}
						}
						else{
							onlineMap.put(username, sc);
						}
					}
				}
				else if (map.get("type").equals("message")){
					try {
						BroadCast(sc, map);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}
}
