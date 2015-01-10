package com.thesis.yuema.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * ChatHistory entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "chat_history", catalog = "yuema")
public class ChatHistory implements java.io.Serializable {

	// Fields

	private Integer id;
	private UserInfo userInfo;
	private ChatInfo chatInfo;
	private String content;
	private String chatTime;

	// Constructors

	/** default constructor */
	public ChatHistory() {
	}

	/** full constructor */
	public ChatHistory(UserInfo userInfo, ChatInfo chatInfo, String content,
			String chatTime) {
		this.userInfo = userInfo;
		this.chatInfo = chatInfo;
		this.content = content;
		this.chatTime = chatTime;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	public UserInfo getUserInfo() {
		return this.userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "chat_id", nullable = false)
	public ChatInfo getChatInfo() {
		return this.chatInfo;
	}

	public void setChatInfo(ChatInfo chatInfo) {
		this.chatInfo = chatInfo;
	}

	@Column(name = "content", nullable = false, length = 65535)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "chat_time", nullable = false, length = 12)
	public String getChatTime() {
		return this.chatTime;
	}

	public void setChatTime(String chatTime) {
		this.chatTime = chatTime;
	}

}