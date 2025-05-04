package kz.controller;

import kz.dto.UserDto;
import kz.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String register(@RequestBody UserDto dto) {
        return userService.register(dto);
    }

    @PostMapping("/login")
    public String login(@RequestParam String login, @RequestParam String password) {
        return userService.login(login, password);
    }
}
