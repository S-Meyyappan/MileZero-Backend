package com.springboot.milezero.controller;

import com.springboot.milezero.dto.request.AdminDto;
import com.springboot.milezero.dto.request.TokenDto;
import com.springboot.milezero.dto.response.UserRespDto;
import com.springboot.milezero.model.User;
import com.springboot.milezero.service.UserService;
import com.springboot.milezero.utility.JwtUtility;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:5173")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final UserService userService;
    private final JwtUtility jwtUtility;

    @PostMapping("/admin/add")
    public void add(@RequestBody AdminDto adminDto){
        userService.add(adminDto);
    }

    @PostMapping("/login")
    public TokenDto login(Principal principal) {

        String email = principal.getName();
        logger.info("Login request for user : {}",email);

        User user = userService.findByEmail(email);
        logger.info("User found : {}",user);

        String token = jwtUtility.generateToken(email);

        return new TokenDto(
                token,
                user.getName(),
                user.getRole(),
                jwtUtility.extractExpiration(token).toString()
        );
    }

    @GetMapping("/check-user")
    // We get the email from the principal passed in Bearer token decoded by jwtUtility
    // Then we get the user from the email

    public UserRespDto getUserDetails(Principal principal) {
        String email = principal.getName();
        User user = userService.findByEmail(email);
        return new UserRespDto(
                user.getName(),
                user.getRole());
    }

}
