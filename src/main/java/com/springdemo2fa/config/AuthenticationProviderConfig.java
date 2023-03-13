package com.springdemo2fa.config;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.amdelamar.jotp.OTP;
import com.amdelamar.jotp.type.Type;
import com.springdemo2fa.model.UserDto;
import com.springdemo2fa.repository.UserDtoRepository;

public class AuthenticationProviderConfig extends DaoAuthenticationProvider {

    @Autowired
    private UserDtoRepository userDtoRepository;

    public AuthenticationProviderConfig(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        super();
        this.setUserDetailsService(userDetailsService);
        this.setPasswordEncoder(passwordEncoder);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDto userDto = userDtoRepository.findByUsername(authentication.getName());

        if (userDto != null) {
            AuthenticationDetailsConfig providedLoginUserDetails = (AuthenticationDetailsConfig) authentication
                    .getDetails();

            try {
                String serverCode = OTP.create(userDto.getMfaSecret(), OTP.timeInHex(System.currentTimeMillis()), 6,
                        Type.TOTP);
                if (!serverCode.equals(providedLoginUserDetails.getUser2FaCode())) {
                    throw new BadCredentialsException("The User's 2FA code provided didn't match with server code");
                }

            } catch (InvalidKeyException | NoSuchAlgorithmException | IOException ex) {
                throw new AuthenticationServiceException(
                        "Error or generate 2FA code on server-side: " + ex.getMessage());
            }
        }

        return super.authenticate(authentication);
    }

}
