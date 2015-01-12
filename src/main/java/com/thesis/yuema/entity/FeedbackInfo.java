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
 * FeedbackInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "feedback_info", catalog = "yuema")
public class FeedbackInfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private UserInfo userInfo;
	private String feedback;
	private String imeiValue;
	private String replyFeedback;
	private String feedbackTime;
	private String replyTime;
	private Short isReply;

	// Constructors

	/** default constructor */
	public FeedbackInfo() {
	}

	/** minimal constructor */
	public FeedbackInfo(UserInfo userInfo, String feedback, String feedbackTime) {
		this.userInfo = userInfo;
		this.feedback = feedback;
		this.feedbackTime = feedbackTime;
	}

	/** full constructor */
	public FeedbackInfo(UserInfo userInfo, String feedback, String imeiValue,
			String replyFeedback, String feedbackTime, String replyTime,
			Short isReply) {
		this.userInfo = userInfo;
		this.feedback = feedback;
		this.imeiValue = imeiValue;
		this.replyFeedback = replyFeedback;
		this.feedbackTime = feedbackTime;
		this.replyTime = replyTime;
		this.isReply = isReply;
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

	@Column(name = "feedback", nullable = false, length = 300)
	public String getFeedback() {
		return this.feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	@Column(name = "imei_value", length = 200)
	public String getImeiValue() {
		return this.imeiValue;
	}

	public void setImeiValue(String imeiValue) {
		this.imeiValue = imeiValue;
	}

	@Column(name = "reply_feedback", length = 300)
	public String getReplyFeedback() {
		return this.replyFeedback;
	}

	public void setReplyFeedback(String replyFeedback) {
		this.replyFeedback = replyFeedback;
	}

	@Column(name = "feedback_time", nullable = false, length = 12)
	public String getFeedbackTime() {
		return this.feedbackTime;
	}

	public void setFeedbackTime(String feedbackTime) {
		this.feedbackTime = feedbackTime;
	}

	@Column(name = "reply_time", length = 12)
	public String getReplyTime() {
		return this.replyTime;
	}

	public void setReplyTime(String replyTime) {
		this.replyTime = replyTime;
	}

	@Column(name = "is_reply")
	public Short getIsReply() {
		return this.isReply;
	}

	public void setIsReply(Short isReply) {
		this.isReply = isReply;
	}

}