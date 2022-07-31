package com.project.questionapp.dataAccess;

import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.questionapp.entities.Comment;

public interface CommentDao extends JpaRepository<Comment, Integer>{
	
	@Query("FROM Comment where id=:id")
	Comment getById(int id);

	List<Comment> getByPost_Id(int postId);
	
	List<Comment> getByUser_Id(int userId);
	
	
	//@Query(value= "SELECT * FROM comments c WHERE c.post_id IN :postIds LIMIT 5",nativeQuery = true)
	@Query(value = "SELECT 'commented on', c.post_id, u.avatar, u.user_name FROM comments c LEFT JOIN users u ON u.id = c.user_id WHERE c.post_id IN :postIds LIMIT 5", nativeQuery = true)
	List<Object> getUserCommentsByPostId(@Param("postIds") List<Integer> postIds);
}
