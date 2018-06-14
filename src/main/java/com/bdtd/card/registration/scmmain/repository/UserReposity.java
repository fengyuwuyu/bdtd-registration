package com.bdtd.card.registration.scmmain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.bdtd.card.registration.scmmain.model.DtUser;

public interface UserReposity extends JpaRepository<DtUser, Integer>, JpaSpecificationExecutor<DtUser> {

	DtUser findByUserCard(String userCard);
}
