package com.itptit.securitity.JWT;

import com.itptit.entities.User;
import com.itptit.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Value("${app.jwtPrefix}")
    String jwtPrefix;

    @Value("${app.jwtHeader}")
    String jwtHeader;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String jwt = getToken(httpServletRequest);
        if(jwt != null && jwtTokenProvider.validateToken(jwt)){
            try {
                User user = userService.findUserById(jwtTokenProvider.getUserIdFromJWT(jwt));
                List<GrantedAuthority> listAuthorities = new ArrayList<GrantedAuthority>();
                listAuthorities.addAll(user.getAuthorities());
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user.getUserName(), null, listAuthorities);
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception e){
                logger.error("Set authentication from JWT fail");
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String getToken(HttpServletRequest req){
        String authHeader = req.getHeader(jwtHeader);
        if (authHeader != null && authHeader.startsWith(jwtPrefix)){
            return authHeader;
        }
        return null;
    }
}

