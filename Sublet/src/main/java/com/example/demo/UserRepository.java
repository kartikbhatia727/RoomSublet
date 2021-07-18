package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;
//public interface UserRepository extends CrudRepository<User, Integer> {
//
//}
public interface UserRepository extends JpaRepository<User, Integer> {

//	@Query("SELECT u from User u WHERE u.email=?1")
//	User findByEmail(String email);
//	Optional<User> findByEmail(String email);
	
	User findById(String Id);
	User findByEmail(String email);

	Boolean existsByEmail(String email);
	
//	@Query("Select p from User u Join c.posts p")
//	public List<Post> getPosts(); 
	
	
//	@Query("SELECT u FROM User u WHERE u.username = :username")
//    public User getUserByUsername(@Param("username") String username);
}