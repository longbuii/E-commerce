package com.LB.Ecommerce.service.Impl;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.LB.Ecommerce.dto.LoginRequest;
import com.LB.Ecommerce.dto.Response;
import com.LB.Ecommerce.dto.UserDto;
import com.LB.Ecommerce.entity.User;
import com.LB.Ecommerce.enums.UserRole;
import com.LB.Ecommerce.exception.InvalidCredentialsException;
import com.LB.Ecommerce.exception.NotFoundException;
import com.LB.Ecommerce.mapper.EntityDtoMapper;
import com.LB.Ecommerce.repository.UserRepo;
import com.LB.Ecommerce.security.JwtUtils;
import com.LB.Ecommerce.service.interf.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final EntityDtoMapper entityDtoMapper;

    @Override
    public Response registerUser(UserDto registrationRequest) {
        UserRole role = UserRole.USER;

        if(registrationRequest.getRole() != null && registrationRequest.getRole().equalsIgnoreCase("admin")){
            role = UserRole.ADMIN;
        }
        User user = User.builder()
                    .name(registrationRequest.getName())
                    .email(registrationRequest.getEmail())
                    .password(passwordEncoder.encode(registrationRequest.getPassword()))
                    .password(registrationRequest.getPassword())
                    .role(role)
                    .build();
        User savedUser = userRepo.save(user);

        UserDto userDto = entityDtoMapper.mapUserToDtoBasic(savedUser);
        return Response.builder()
                .status(200)
                .message("User Successfully Added")
                .user(userDto)
                .build();
    }

    @Override
    public Response loginUser(LoginRequest loginRequest) {

        User user = userRepo.findByEmail(loginRequest.getEmail()).orElseThrow(()-> new NotFoundException("Email not found"));
        
        if(!passwordEncoder.matches( loginRequest.getPassword(), user.getPassword())){
            throw new InvalidCredentialsException("Password does not match");
        }

        String token = jwtUtils.generateToken(user);

        return Response.builder()
                .status(200)
                .message("User Successfully Logged In")
                .token(token)
                .expirationTime("6 Month")
                .role(user.getRole().name())
                .build();
    }

    @Override
    public Response getAllUsers() {

        List<User> users = userRepo.findAll();
        List<UserDto> userDto = users.stream()
                        .map(entityDtoMapper::mapUserToDtoBasic)
                        .toList();
        return Response.builder()
                    .status(200)
                    .message("Successful")
                    .userList(userDto)
                    .build();
    }

    @Override
    public User getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        log.info("User Email is: " + email);
        return userRepo.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User Not Found"));
    }

    @Override
    public Response getUserInfoAndOrderHistory() {

        User user =getLoginUser();
        UserDto userDto = entityDtoMapper.mapUserToDtoPlusAddressAndOrderHistory(user);

        return Response.builder()
                .status(200)
                .user(userDto)
                .build();
    }

}
