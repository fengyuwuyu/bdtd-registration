package com.bdtd.card.registration.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.bdtd.card.registration.model.User;

public interface UserReposity extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

}
