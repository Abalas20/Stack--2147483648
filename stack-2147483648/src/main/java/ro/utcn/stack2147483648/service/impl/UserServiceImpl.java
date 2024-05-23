package ro.utcn.stack2147483648.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import ro.utcn.stack2147483648.dto.SignupDTO;
import ro.utcn.stack2147483648.dto.UserDTO;
import ro.utcn.stack2147483648.entities.User;
import ro.utcn.stack2147483648.repository.UserRepository;
import ro.utcn.stack2147483648.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<UserDTO> createUser(SignupDTO signupDTO) {
        User user = convertToUser(signupDTO);
        User createdUser = userRepository.save(user);
        return Optional.of(convertToUserDTO(createdUser));
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findFirstByEmail(email);
    }

    @Override
    public boolean hasEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }

    @Override
    public Optional<UserDTO> getUserById(Long userId) {
        return userRepository.findById(userId).map(this::convertToUserDTO);
    }

    @Override
    public Optional<Double> getUserScore(Long userId) {
        return userRepository.findById(userId).map(User::getScore);
    }

    @Override
    public Optional<String> getUserRole(Long userId) {
        return userRepository.findById(userId).map(User::getRole);
    }

    @Override
    public Optional<String> getUserStatus(Long userId) {
        return userRepository.findById(userId).map(User::getStatus);
    }

    private User convertToUser(SignupDTO signupDTO) {
        return new User(
                signupDTO.getLastName(),
                signupDTO.getFirstName(),
                signupDTO.getPhone(),
                signupDTO.getEmail(),
                passwordEncoder.encode(signupDTO.getPassword()),
                signupDTO.getUsername()
        );
    }

    private UserDTO convertToUserDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getLastName(),
                user.getFirstName(),
                user.getPhone(),
                user.getEmail(),
                user.getPassword(),
                user.getUsername(),
                user.getRole(),
                user.getScore()
        );
    }
}