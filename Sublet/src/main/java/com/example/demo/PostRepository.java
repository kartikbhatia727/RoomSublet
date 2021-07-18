package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Integer> {
	List<Post> findByUser(User user);
//	
//	@Query("SELECT * from Post u WHERE u.location=?1")
	public List<Post> findByLocation(String loc);
//	
	public List<Post> findByUserId(String userid);
}
