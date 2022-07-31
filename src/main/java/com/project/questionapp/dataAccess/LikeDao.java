package com.project.questionapp.dataAccess;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.questionapp.entities.Like;

public interface LikeDao extends JpaRepository<Like, Integer> {

	int countByPost_Id(int postId);

	List<Like> getByPost_Id(int postId);

	Like getByUser_IdAndPost_Id(int userId, int postId);

	//@Query(value="SELECT * from likes l WHERE l.post_id IN :postIds LIMIT 5", nativeQuery = true)
	@Query(value = "SELECT 'liked on', l.post_id, u.avatar, u.user_name FROM likes l LEFT JOIN users u on u.id = l.user_id WHERE l.post_id IN :postIds LIMIT 5", nativeQuery = true)
	List<Object> getUserLikesByPostId(@Param("postIds") List<Integer> postIds);

}
