package com.example.demo;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
BCryptPasswordEncoder encoder = new  BCryptPasswordEncoder();
String rawPassword ="abc";
String encodedPassword = encoder.encode(rawPassword);
System.out.println(encodedPassword);
	}

}
