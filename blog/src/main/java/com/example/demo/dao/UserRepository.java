package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.vo.User;

public interface UserRepository extends JpaRepository<User,Long>{
	
	User findByUsernameAndPassword(String username,String password);
	
	 
}
