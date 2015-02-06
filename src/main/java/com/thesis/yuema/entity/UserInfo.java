package com.thesis.yuema.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * UserInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_info", catalog = "yuema")
public class UserInfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private String username;
	private String password;
	private String nickname;
	private String realName;
	private Short sex;
	private String birthday;
	private String state;
	private String introduce;
	private String photo;
	private Short isFrost;
	private String createTime;
	private Set<FocusRelation> focusRelationsForMyId = new HashSet<FocusRelation>(
			0);
	private Set<FeedbackInfo> feedbackInfos = new HashSet<FeedbackInfo>(0);
	private Set<ImeiInfo> imeiInfos = new HashSet<ImeiInfo>(0);
	private Set<ChatInfo> chatInfos = new HashSet<ChatInfo>(0);
	private Set<FocusRelation> focusRelationsForFocusId = new HashSet<FocusRelation>(
			0);
	private Set<ChatHistory> chatHistoriesForUserId = new HashSet<ChatHistory>(
			0);
	private Set<ChatMember> chatMembers = new HashSet<ChatMember>(0);
	private Set<ChatHistory> chatHistoriesForChatUserId = new HashSet<ChatHistory>(
			0);

	// Constructors

	/** default constructor */
	public UserInfo() {
	}

	/** minimal constructor */
	public UserInfo(String username, String password, String nickname,
			Short isFrost, String createTime) {
		this.username = username;
		this.password = password;
		this.nickname = nickname;
		this.isFrost = isFrost;
		this.createTime = createTime;
	}

	/** full constructor */
	public UserInfo(String username, String password, String nickname,
			String realName, Short sex, String birthday, String state,
			String introduce, String photo, Short isFrost, String createTime,
			Set<FocusRelation> focusRelationsForMyId,
			Set<FeedbackInfo> feedbackInfos, Set<ImeiInfo> imeiInfos,
			Set<ChatInfo> chatInfos,
			Set<FocusRelation> focusRelationsForFocusId,
			Set<ChatHistory> chatHistoriesForUserId,
			Set<ChatMember> chatMembers,
			Set<ChatHistory> chatHistoriesForChatUserId) {
		this.username = username;
		this.password = password;
		this.nickname = nickname;
		this.realName = realName;
		this.sex = sex;
		this.birthday = birthday;
		this.state = state;
		this.introduce = introduce;
		this.photo = photo;
		this.isFrost = isFrost;
		this.createTime = createTime;
		this.focusRelationsForMyId = focusRelationsForMyId;
		this.feedbackInfos = feedbackInfos;
		this.imeiInfos = imeiInfos;
		this.chatInfos = chatInfos;
		this.focusRelationsForFocusId = focusRelationsForFocusId;
		this.chatHistoriesForUserId = chatHistoriesForUserId;
		this.chatMembers = chatMembers;
		this.chatHistoriesForChatUserId = chatHistoriesForChatUserId;
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

	@Column(name = "username", nullable = false, length = 20)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password", nullable = false, length = 32)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "nickname", nullable = false, length = 100)
	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Column(name = "real_name", length = 20)
	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	@Column(name = "sex")
	public Short getSex() {
		return this.sex;
	}

	public void setSex(Short sex) {
		this.sex = sex;
	}

	@Column(name = "birthday", length = 10)
	public String getBirthday() {
		return this.birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	@Column(name = "state", length = 10)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "introduce", length = 100)
	public String getIntroduce() {
		return this.introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	@Column(name = "photo", length = 200)
	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@Column(name = "is_frost", nullable = false)
	public Short getIsFrost() {
		return this.isFrost;
	}

	public void setIsFrost(Short isFrost) {
		this.isFrost = isFrost;
	}

	@Column(name = "create_time", nullable = false, length = 12)
	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userInfoByMyId")
	public Set<FocusRelation> getFocusRelationsForMyId() {
		return this.focusRelationsForMyId;
	}

	public void setFocusRelationsForMyId(
			Set<FocusRelation> focusRelationsForMyId) {
		this.focusRelationsForMyId = focusRelationsForMyId;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userInfo")
	public Set<FeedbackInfo> getFeedbackInfos() {
		return this.feedbackInfos;
	}

	public void setFeedbackInfos(Set<FeedbackInfo> feedbackInfos) {
		this.feedbackInfos = feedbackInfos;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userInfo")
	public Set<ImeiInfo> getImeiInfos() {
		return this.imeiInfos;
	}

	public void setImeiInfos(Set<ImeiInfo> imeiInfos) {
		this.imeiInfos = imeiInfos;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userInfo")
	public Set<ChatInfo> getChatInfos() {
		return this.chatInfos;
	}

	public void setChatInfos(Set<ChatInfo> chatInfos) {
		this.chatInfos = chatInfos;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userInfoByFocusId")
	public Set<FocusRelation> getFocusRelationsForFocusId() {
		return this.focusRelationsForFocusId;
	}

	public void setFocusRelationsForFocusId(
			Set<FocusRelation> focusRelationsForFocusId) {
		this.focusRelationsForFocusId = focusRelationsForFocusId;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userInfoByUserId")
	public Set<ChatHistory> getChatHistoriesForUserId() {
		return this.chatHistoriesForUserId;
	}

	public void setChatHistoriesForUserId(
			Set<ChatHistory> chatHistoriesForUserId) {
		this.chatHistoriesForUserId = chatHistoriesForUserId;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userInfo")
	public Set<ChatMember> getChatMembers() {
		return this.chatMembers;
	}

	public void setChatMembers(Set<ChatMember> chatMembers) {
		this.chatMembers = chatMembers;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userInfoByChatUserId")
	public Set<ChatHistory> getChatHistoriesForChatUserId() {
		return this.chatHistoriesForChatUserId;
	}

	public void setChatHistoriesForChatUserId(
			Set<ChatHistory> chatHistoriesForChatUserId) {
		this.chatHistoriesForChatUserId = chatHistoriesForChatUserId;
	}

}