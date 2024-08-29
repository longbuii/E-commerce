package com.LB.Ecommerce.service.interf;

import com.LB.Ecommerce.dto.LoginRequest;
import com.LB.Ecommerce.dto.Response;
import com.LB.Ecommerce.dto.UserDto;
import com.LB.Ecommerce.entity.User;




public interface UserService {

    Response registerUser(UserDto registrationRequest);
    Response loginUser(LoginRequest loginRequest);
    Response getAllUsers();
    User getLoginUser();
    Response getUserInfoAndOrderHistory();
}