package com.springdemo2fa.service;

import javax.persistence.EntityExistsException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.amdelamar.jotp.OTP;
import com.springdemo2fa.model.User;
import com.springdemo2fa.model.UserDto;
import com.springdemo2fa.repository.UserDtoRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserDtoRepository userDtoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Create user
    @Transactional
    public void creatUser(UserDto userDto) {
        if (userDtoRepository.findByUsername(userDto.getUsername()) != null)
            throw new EntityExistsException(
                    "Already exists an user with this username [ " + userDto.getUsername() + " ]");

        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userDto.setMfaSecret(OTP.randomBase32(20));

        userDtoRepository.save(userDto);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto userDto = userDtoRepository.findByUsername(username);

        if (userDto == null) {
            throw new UsernameNotFoundException("user " + username + " not found");
        }

        return new User(userDto);
    }

}
