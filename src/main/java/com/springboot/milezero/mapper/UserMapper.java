package com.springboot.milezero.mapper;

import com.springboot.milezero.enums.Role;
import com.springboot.milezero.model.User;

public class UserMapper {

    private UserMapper() {
        /* This utility class should not be instantiated */
    }

    public static User convertDtoToEntity(String name, String email, String password, Role role){
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        return user;
    }
}
