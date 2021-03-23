package com.itptit.controller.admin;

import javax.jws.soap.SOAPBinding.Use;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.itptit.entities.Product;
import com.itptit.entities.User;
import com.itptit.respositories.UserRepo;
import com.itptit.services.UserService;

import java.util.List;

@RestController
public class AdminUserController {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/admin/user", method = RequestMethod.GET)
	public ResponseEntity<List<User>> findAllUser() throws Exception{
		return new ResponseEntity<>(userRepo.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/admin/user-add", method = RequestMethod.POST)
	public ResponseEntity<User> addUser(@RequestBody User user) throws Exception{
		userService.saveUser(user);
		return new ResponseEntity<>(user,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/admin/user-edit/{id}", method = RequestMethod.GET)
	public ResponseEntity<User> getUserById( @PathVariable("id") Integer id )throws Exception{
		User user = userRepo.getOne(id);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/admin/user-edit/{id}", method = RequestMethod.PUT)
	public ResponseEntity<User> updateUser (@PathVariable("id") Integer id, @RequestBody User user)throws Exception{
		userService.saveUser(user);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}



}
