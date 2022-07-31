package com.project.questionapp.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.questionapp.entities.RefreshToken;

public interface RefreshTokenDao extends JpaRepository<RefreshToken, Integer>{

	@Query("FROM RefreshToken where user.id=:userId")
	RefreshToken getByUser_Id(int userId);
}
