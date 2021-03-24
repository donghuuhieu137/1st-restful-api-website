package com.itptit.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.itptit.model.request.LoginRequest;
import com.itptit.model.respone.JwtResponse;
import com.itptit.securitity.JWT.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.itptit.entities.User;
import com.itptit.services.UserService;

@RestController
public class LoginController extends BaseController{
	
	@Autowired
	UserService userService;

	@Autowired
    JwtTokenProvider jwtTokenProvider;

	@Autowired
    AuthenticationManager authenticationManager;

	@RequestMapping(value = "/regist", method = RequestMethod.POST)
    public ResponseEntity<User> createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findByUserName(user.getUserName());
        if (userExists != null) {
            bindingResult
                    .rejectValue("userName", "error.user",
                            "There is already a user registered with the user name provided");
        }
        if (bindingResult.hasErrors())
           return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<JwtResponse> signIn(@Valid @RequestBody LoginRequest loginRequest){
        try {
            // Tạo chuỗi authentication từ username và password
            // (object LoginRequest - file này chỉ là 1 class bình thường, chứa 2 trường username và password)
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            //Set chuỗi authentication đó cho UserPrincipal
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtTokenProvider.generateToken(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = userService.findByUserName(userDetails.getUsername());
            return ResponseEntity.ok(new JwtResponse(jwt, user.getEmail(), user.getUsername(), user.getRoles().toString()));
        }   catch (AuthenticationException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
	}
}
