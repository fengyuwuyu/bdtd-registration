package com.bdtd.card.registration.scmmain.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bdtd.card.registration.scmmain.dao.IDtUserDao;
import com.bdtd.card.registration.scmmain.model.DtUser;
import com.bdtd.card.registration.scmmain.repository.UserReposity;

@Repository("dtUserDaoImpl")
public class DtUserDaoImpl implements IDtUserDao {
	
	@Autowired
	UserReposity userReposity;

	public DtUser findByUserCard(String userCard) {
		return userReposity.findByUserCard(userCard);
	}

}
