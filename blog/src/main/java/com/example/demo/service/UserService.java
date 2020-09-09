package com.example.demo.service;

import java.util.Optional;


import com.example.demo.vo.User;

public interface UserService {

	User CheckUser(String usernmae,String password);
	
	Optional<User> getUserById(Long id);
}
