package com.thp.spring.simplecontext.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thp.spring.simplecontext.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByMail(String mail);

}
