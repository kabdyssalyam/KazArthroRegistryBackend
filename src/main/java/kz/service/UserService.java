package kz.service;

import kz.dto.UserDto;
import kz.repository.UserRepository;
import kz.types.UserType;
import kz.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String register(UserDto dto) {
        throw new RuntimeException("Registration is disabled");
        /*
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
        return "User registered successfully";*/
    }

    public User createUser(UserDto userDto) {
        User user = new User();
        user.setLogin(userDto.login);
        user.setPassword(passwordEncoder.encode(userDto.password));
        user.setFirstname(userDto.firstname);
        user.setLastname(userDto.lastname);
        user.setType(UserType.DOCTOR);
        user.setActive(true);

        return userRepository.save(user);
    }

    public User updateUser(Long userId, UserDto userDto) {
        Optional<User> existingUser = userRepository.findById(userId);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setLogin(userDto.login);
            user.setPassword(passwordEncoder.encode(userDto.password));
            user.setFirstname(userDto.firstname);
            user.setLastname(userDto.lastname);
            user.setActive(userDto.isActive);
            return userRepository.save(user);
        }
        return null;
    }

    // Method to get a list of all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Method to get user by ID
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public boolean login(String login, String password) {
        Optional<User> userOpt = userRepository.findByLogin(login);
        return userOpt.isPresent() && userOpt.get().isActive() && passwordEncoder.matches(password, userOpt.get().getPassword());
    }
}
