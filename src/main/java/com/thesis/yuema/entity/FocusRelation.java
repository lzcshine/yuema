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
 * FocusRelation entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "focus_relation", catalog = "yuema")
public class FocusRelation implements java.io.Serializable {

	// Fields

	private Integer id;
	private UserInfo userInfoByFocusId;
	private UserInfo userInfoByMyId;

	// Constructors

	/** default constructor */
	public FocusRelation() {
	}

	/** full constructor */
	public FocusRelation(UserInfo userInfoByFocusId, UserInfo userInfoByMyId) {
		this.userInfoByFocusId = userInfoByFocusId;
		this.userInfoByMyId = userInfoByMyId;
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
	@JoinColumn(name = "focus_id", nullable = false)
	public UserInfo getUserInfoByFocusId() {
		return this.userInfoByFocusId;
	}

	public void setUserInfoByFocusId(UserInfo userInfoByFocusId) {
		this.userInfoByFocusId = userInfoByFocusId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "my_id", nullable = false)
	public UserInfo getUserInfoByMyId() {
		return this.userInfoByMyId;
	}

	public void setUserInfoByMyId(UserInfo userInfoByMyId) {
		this.userInfoByMyId = userInfoByMyId;
	}

}