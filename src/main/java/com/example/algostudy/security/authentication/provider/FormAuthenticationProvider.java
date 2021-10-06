package com.example.algostudy.security.authentication.provider;

import com.example.algostudy.security.authentication.domain.UserDetail;
import com.example.algostudy.security.authentication.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FormAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private  PasswordEncoder  passwordEncoder;
    @Autowired
    UserDetailsService userDetailsService;




    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        String email = auth.getName();
        String password = (String) auth.getCredentials();

        UserDetails userDetails = null;

        try {
            userDetails = userDetailsService.loadUserByUsername(email);
            if (userDetails != null && !passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("Invalid Password");
            }
            if (!userDetails.isEnabled()) {
                throw new BadCredentialsException("Not User Confirm");
            }

        } catch (UsernameNotFoundException e) {
            log.info(e.toString());
            throw new UsernameNotFoundException(e.getMessage());
        } catch (BadCredentialsException e) {
            log.info(e.toString());
            throw new BadCredentialsException(e.getMessage());
        } catch (Exception e) {
            log.info(e.toString());
            throw new RuntimeException(e.getMessage());
        }
        return new UsernamePasswordAuthenticationToken(((UserDetail) userDetails).getMember(), null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
