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
 * ChatMember entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "chat_member", catalog = "yuema")
public class ChatMember implements java.io.Serializable {

	// Fields

	private Integer id;
	private UserInfo userInfo;
	private ChatInfo chatInfo;
	private Short isSponsor;

	// Constructors

	/** default constructor */
	public ChatMember() {
	}

	/** full constructor */
	public ChatMember(UserInfo userInfo, ChatInfo chatInfo, Short isSponsor) {
		this.userInfo = userInfo;
		this.chatInfo = chatInfo;
		this.isSponsor = isSponsor;
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

	@Column(name = "is_sponsor", nullable = false)
	public Short getIsSponsor() {
		return this.isSponsor;
	}

	public void setIsSponsor(Short isSponsor) {
		this.isSponsor = isSponsor;
	}

}