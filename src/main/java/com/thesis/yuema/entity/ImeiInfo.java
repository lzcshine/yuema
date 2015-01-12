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
 * ImeiInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "imei_info", catalog = "yuema")
public class ImeiInfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private UserInfo userInfo;
	private String imei;

	// Constructors

	/** default constructor */
	public ImeiInfo() {
	}

	/** minimal constructor */
	public ImeiInfo(String imei) {
		this.imei = imei;
	}

	/** full constructor */
	public ImeiInfo(UserInfo userInfo, String imei) {
		this.userInfo = userInfo;
		this.imei = imei;
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
	@JoinColumn(name = "user_id")
	public UserInfo getUserInfo() {
		return this.userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	@Column(name = "imei", nullable = false, length = 200)
	public String getImei() {
		return this.imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

}