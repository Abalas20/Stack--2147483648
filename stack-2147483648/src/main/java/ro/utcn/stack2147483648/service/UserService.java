package ro.utcn.stack2147483648.service;


import ro.utcn.stack2147483648.dto.SignupDTO;
import ro.utcn.stack2147483648.dto.UserDTO;
import ro.utcn.stack2147483648.entities.User;

import java.util.Optional;

public interface UserService {

    Optional<UserDTO> createUser(SignupDTO signupDTO);

    Optional<User> getUserByEmail(String email);

    boolean hasEmail(String email);
}
