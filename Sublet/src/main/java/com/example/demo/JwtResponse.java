package com.example.demo;

public class JwtResponse {

	private String token;
	private String type = "Bearer";
	private String email;
	private Integer id;

	public JwtResponse(String accessToken,Integer id, String email) {
		this.token = accessToken;
		this.id = id;
		this.email = email;
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
