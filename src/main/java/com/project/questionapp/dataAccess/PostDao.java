package com.project.questionapp.dataAccess;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.project.questionapp.entities.Post;

public interface PostDao extends JpaRepository<Post, Integer> {

	@Query("FROM Post where id=:id")
	Post getById(int id);
	
	Post getByTitle(String title);
	
	@Transactional
	List<Post> getPostsByUserId(int userId);
	
	@Query(value="SELECT id FROM posts p WHERE p.user_id = :userId ORDER BY created_at DESC LIMIT 5", nativeQuery = true)
	List<Integer> getTopByUserId(@Param("userId") int userId);
	
}
