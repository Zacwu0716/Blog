package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UserRepository;
import com.example.demo.util.MD5Utils;
import com.example.demo.vo.User;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userrepository;
	
	@Override
	public User CheckUser(String username, String password) {
		// TODO Auto-generated method stub
		//MD5加密
		
		User user =  userrepository.findByUsernameAndPassword(username, MD5Utils.code(password));
		return user;
	}

	@Override
	public Optional<User> getUserById(Long id) {
		// TODO Auto-generated method stub
		
		Optional<User> user = userrepository.findById(id);
		return user;
	}

}
