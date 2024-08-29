package com.LB.Ecommerce.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.LB.Ecommerce.entity.User;
import com.LB.Ecommerce.exception.NotFoundException;
import com.LB.Ecommerce.repository.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final  UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username)
                .orElseThrow(()-> new NotFoundException("User/ Email Not Found"));
        
        return AuthUser.builder()
                    .user(user)
                    .build();
    }

    
}
