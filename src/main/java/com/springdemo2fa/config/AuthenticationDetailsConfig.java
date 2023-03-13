package com.springdemo2fa.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import lombok.Getter;

@Getter
public class AuthenticationDetailsConfig extends WebAuthenticationDetails {

    private String user2FaCode;

    public AuthenticationDetailsConfig(HttpServletRequest request) {
        super(request);
        this.user2FaCode = request.getParameter("2FaCode");
    }
}
