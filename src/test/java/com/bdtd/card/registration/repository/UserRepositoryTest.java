package com.bdtd.card.registration.repository;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bdtd.card.registration.base.BaseJunit;
import com.bdtd.card.registration.model.User;

public class UserRepositoryTest extends BaseJunit {

	@Autowired
	UserReposity userReposity;
	
	@Test
	public void testSave() {
		String account = "000001";
		String username = "张三";
		Integer roleId = 1;
		String cardNumber = "000001";
		Date createDate = new Date();
		Date updateDate = createDate;
		User entity = new User(account, username, roleId, cardNumber, createDate, updateDate);
		userReposity.save(entity);
	}
}
