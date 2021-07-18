package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity 
@Table(name="user")
public class User {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Integer id;
  @Column(nullable=false,length=20)
  private String name;
//@Email
//@NotBlank
 @Column(nullable=false,unique=true,length=45)
  private String email;
 @Column(nullable=false,length=64)
 private String password;

// private String role;
// private boolean enabled;
 
 
 @OneToMany(mappedBy = "user") //,cascade = CascadeType.ALL)//,fetch = FetchType.LAZY) 	
// joinColumns = @JoinColumn(name = "user_id"));
 
 
// @OneToMany(targetEntity = Post.class) //cascade = CascadeType.ALL)
// @JoinColumn(name = "user_post_fk", referencedColumnName = "id")
 private List<Post> posts ;//= new ArrayList<>();
 
 	
  public User() {

}

public User(String email, String password) {
	this.email = email;
	this.password = password;
}

public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
  public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

public List<Post> getPosts() {
	return posts;
}

public void setPosts(List<Post> posts) {
	this.posts = posts;
}
}