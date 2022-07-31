package com.project.questionapp.dataAccess;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.questionapp.entities.User;

public interface UserDao extends JpaRepository<User, UUID> {

	
	User getById(int id);
	User getByUserName(String userName);
}
