package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="post")
public class Post {
	  @Id
	  @GeneratedValue(strategy=GenerationType.AUTO)
	  private Integer postid;
//	  private String type;

//	   private Instant createdDate;
//	   private  boolean fulfilled;
       @JsonIgnore
	   @ManyToOne(fetch = FetchType.LAZY)
       @JoinColumn(name = "user_id")//, referencedColumnName = "id")
	   private User user;
	   
	
		private String title;
		private String description;
		private String location;
		private String preferences;
		private Integer rent;
		private String utilities;
//		private String leaseType; not for sublets
		
		public Post() {
		}
		
		public Post(String title, String description, String location) {
			this.title = title;
			this.description = description;
			this.location = location;
		}

		
		public Integer getPostid() {
			return postid;
		}

		
		public void setPostid(Integer postid) {
			this.postid = postid;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}
		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getPreferences() {
			return preferences;
		}

		public void setPreferences(String preferences) {
			this.preferences = preferences;
		}

		public Integer getRent() {
			return rent;
		}

		public void setRent(Integer rent) {
			this.rent = rent;
		}

		public String getUtilities() {
			return utilities;
		}

		public void setUtilities(String utilities) {
			this.utilities = utilities;
		}

		
		@Override
		public String toString() {
			return "Post [postid=" + postid + ", title=" + title + ", description=" + description + ", preferences="
					+ preferences + ", rent=" + rent + ", utilities=" + utilities + "]";
		}

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}
	   
}
