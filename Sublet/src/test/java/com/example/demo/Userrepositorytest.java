package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class Userrepositorytest {
@Autowired
private UserRepository repo;

@Autowired
private TestEntityManager entitymanager;

@Test
public void testcreateuser()
{
	User user = new User();
	user.setEmail("abc@gmail.com");
	user.setPassword("abc");
	user.setName("abc");
	
	User saveduser = repo.save(user);
	
	User existuser = entitymanager.find(User.class,saveduser.getId());

   assertThat(existuser.getEmail()).isEqualTo(user.getEmail());
}

@Test
public  void testFindUserByEmail() {
	String email="abc@gmail.com";
	User user = repo.findByEmail(email);
	
	assertThat(user).isNotNull();
	
}
	
}
