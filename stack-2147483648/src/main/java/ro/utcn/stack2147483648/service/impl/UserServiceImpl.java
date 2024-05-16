package ro.utcn.stack2147483648.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO createUser(SignupDTO signupDTO) {
        User user = new User(
                signupDTO.getLastName(),
                signupDTO.getFirstName(),
                signupDTO.getPhone(),
                signupDTO.getEmail(),
                new BCryptPasswordEncoder().encode(signupDTO.getPassword()),
                signupDTO.getUsername()
        );

        User createdUser = userRepository.save(user);

        return new UserDTO(
                createdUser.getId(),
                createdUser.getLastName(),
                createdUser.getFirstName(),
                createdUser.getPhone(),
                createdUser.getEmail(),
                createdUser.getPassword(),
                createdUser.getUsername(),
                createdUser.getRole(),
                createdUser.getScore()
        );
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findFirstByEmail(email);
    }


    @Override
    public boolean hasEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }
}
