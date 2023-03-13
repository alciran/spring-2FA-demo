package com.springdemo2fa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private AuthenticationDetailsSourceConfig authenticationDetailsSourceConfig;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .antMatchers("/h2-console/**").permitAll() // h2 database console
                .antMatchers("/user/register").permitAll() // permit new register
                .antMatchers("/user/registered").permitAll() // permit show registered info
                .antMatchers("/login*").permitAll() // permit login page
                .antMatchers("/").permitAll()
                .anyRequest().authenticated() // the others page different of configuration must be logged in to access.
                .and()
                .headers().frameOptions().sameOrigin() // h2-console
                .and()
                .formLogin() // use JSESSIONID cookie
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .authenticationDetailsSource(authenticationDetailsSourceConfig)
                .and()
                .logout() // logout process, clean JSESSIONID cookie
                .logoutUrl("/logout")
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/"); // redirect
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(@Autowired UserDetailsService userDetailsService,
            @Autowired PasswordEncoder passwordEncoder) {
        return new AuthenticationProviderConfig(userDetailsService, passwordEncoder);
    }

}
