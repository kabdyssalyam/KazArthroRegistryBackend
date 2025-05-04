package kz.service;

import kz.dto.UserDto;
import kz.repository.UserRepository;
import kz.types.UserType;
import kz.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String register(UserDto dto) {
        if (userRepository.findByLogin(dto.login).isPresent()) {
            return "User already exists";
        }

        User user = new User();
        user.setLogin(dto.login);
        user.setPassword(passwordEncoder.encode(dto.password));
        user.setFirstname(dto.firstname);
        user.setLastname(dto.lastname);
        user.setType(UserType.valueOf(dto.type.toUpperCase()));

        userRepository.save(user);
        return "User registered successfully";
    }

    public boolean login(String login, String password) {
        Optional<User> userOpt = userRepository.findByLogin(login);
        return userOpt.isPresent() && passwordEncoder.matches(password, userOpt.get().getPassword());
    }
}
