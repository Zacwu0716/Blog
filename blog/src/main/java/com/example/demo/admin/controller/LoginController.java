package com.example.demo.admin.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.service.UserService;
import com.example.demo.util.MD5Utils;
import com.example.demo.vo.User;

@Controller
@RequestMapping("/admin")
public class LoginController {

	@Autowired
	private UserService uservice;

	@GetMapping("/")
	public String loginPage() {
		return "admin/login";

	}

	@PostMapping("/login")
	public String login(@RequestParam String username, @RequestParam String password, HttpSession session,
			RedirectAttributes attributes) {
		//System.out.println(password);
		//String fnpassword = MD5Utils.code(password);
		//Optional<User> user1 = uservice.getUserById((long) 1);
		
		User user = uservice.CheckUser(username, password);
		System.out.println(user);
		if (user != null) { //user 密碼正確
			user.setPassword(null);
			session.setAttribute("user", user);
			return "admin/index";
		} else {
			attributes.addFlashAttribute("message", "帳號或密碼輸入錯誤");
			return "redirect:/admin";
		}
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		
		session.removeAttribute("user");
		return "redirect:/admin";
		
	}
	
	
}
