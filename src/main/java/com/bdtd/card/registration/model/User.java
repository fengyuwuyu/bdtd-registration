package com.bdtd.card.registration.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_user1")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String account;
	private String username;
	private Integer roleId;
	private String cardNumber;
	private Date createDate;
	private Date updateDate;

	public User() {
	}

	public User(String account, String username, Integer roleId, String cardNumber,	Date createDate, Date updateDate) {
		this.account = account;
		this.username = username;
		this.roleId = roleId;
		this.cardNumber = cardNumber;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", account=" + account + ", username=" + username + ", roleId=" + roleId
				+ ", cardNumber=" + cardNumber + ", createDate=" + createDate
				+ ", updateDate=" + updateDate + "]";
	}

}
