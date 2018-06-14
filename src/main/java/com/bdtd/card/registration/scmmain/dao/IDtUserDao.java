package com.bdtd.card.registration.scmmain.dao;

import com.bdtd.card.registration.scmmain.model.DtUser;

public interface IDtUserDao {

	DtUser findByUserCard(String userCard);
}
