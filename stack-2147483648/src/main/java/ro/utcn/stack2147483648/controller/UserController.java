package ro.utcn.stack2147483648.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ro.utcn.stack2147483648.dto.SignupDTO;
import ro.utcn.stack2147483648.dto.UserDTO;
import ro.utcn.stack2147483648.service.UserService;

import java.util.Optional;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> createUser(@RequestBody SignupDTO signupDTO) {

        if (userService.hasEmail(signupDTO.getEmail())) {
            return new ResponseEntity<>("Email is already used by another account", HttpStatus.CONFLICT);
        }

        Optional<UserDTO> userDTO = userService.createUser(signupDTO);

        if (userDTO.isEmpty()) {
            return new ResponseEntity<>("User not created, come again later", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(userDTO.get(), HttpStatus.OK);
    }
}