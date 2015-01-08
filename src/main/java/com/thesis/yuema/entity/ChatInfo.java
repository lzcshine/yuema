package com.thesis.yuema.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * ChatInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "chat_info", catalog = "yuema")
public class ChatInfo implements java.io.Serializable {

	// Fields

	private Integer chatId;
	private UserInfo userInfo;
	private String title;
	private Integer createTime;
	private Integer limitTime;
	private Short isResponse;
	private Set<ChatHistory> chatHistories = new HashSet<ChatHistory>(0);
	private Set<ChatMember> chatMembers = new HashSet<ChatMember>(0);

	// Constructors

	/** default constructor */
	public ChatInfo() {
	}

	/** minimal constructor */
	public ChatInfo(UserInfo userInfo, String title, Integer createTime,
			Integer limitTime, Short isResponse) {
		this.userInfo = userInfo;
		this.title = title;
		this.createTime = createTime;
		this.limitTime = limitTime;
		this.isResponse = isResponse;
	}

	/** full constructor */
	public ChatInfo(UserInfo userInfo, String title, Integer createTime,
			Integer limitTime, Short isResponse,
			Set<ChatHistory> chatHistories, Set<ChatMember> chatMembers) {
		this.userInfo = userInfo;
		this.title = title;
		this.createTime = createTime;
		this.limitTime = limitTime;
		this.isResponse = isResponse;
		this.chatHistories = chatHistories;
		this.chatMembers = chatMembers;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "increment")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "chat_id", unique = true, nullable = false)
	public Integer getChatId() {
		return this.chatId;
	}

	public void setChatId(Integer chatId) {
		this.chatId = chatId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sponsor_id", nullable = false)
	public UserInfo getUserInfo() {
		return this.userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	@Column(name = "title", nullable = false, length = 300)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "create_time", nullable = false)
	public Integer getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	@Column(name = "limit_time", nullable = false)
	public Integer getLimitTime() {
		return this.limitTime;
	}

	public void setLimitTime(Integer limitTime) {
		this.limitTime = limitTime;
	}

	@Column(name = "is_response", nullable = false)
	public Short getIsResponse() {
		return this.isResponse;
	}

	public void setIsResponse(Short isResponse) {
		this.isResponse = isResponse;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "chatInfo")
	public Set<ChatHistory> getChatHistories() {
		return this.chatHistories;
	}

	public void setChatHistories(Set<ChatHistory> chatHistories) {
		this.chatHistories = chatHistories;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "chatInfo")
	public Set<ChatMember> getChatMembers() {
		return this.chatMembers;
	}

	public void setChatMembers(Set<ChatMember> chatMembers) {
		this.chatMembers = chatMembers;
	}

}