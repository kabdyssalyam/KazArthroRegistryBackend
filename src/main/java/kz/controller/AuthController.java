package kz.controller;

import kz.dto.UserDto;
import kz.security.JwtUtil;
import kz.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public String login(@RequestParam String login, @RequestParam String password) {
        // Check user credentials (authentication) via UserService
        boolean isAuthenticated = userService.login(login, password);
        if (isAuthenticated) {
            // If authentication is successful, generate and return JWT token
            return JwtUtil.generateToken(login); // You may include more details such as roles in the token
        }
        return "Invalid credentials"; // If authentication fails
    }
}
