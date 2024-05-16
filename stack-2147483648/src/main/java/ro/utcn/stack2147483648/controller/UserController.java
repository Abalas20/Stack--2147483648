package ro.utcn.stack2147483648.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ro.utcn.stack2147483648.dto.SignupDTO;
import ro.utcn.stack2147483648.dto.UserDTO;
import ro.utcn.stack2147483648.service.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> createUser(@RequestBody SignupDTO signupDTO) {

        if (userService.hasEmail(signupDTO.getEmail())) {
            return new ResponseEntity<>("Email is already used by another account", HttpStatus.CONFLICT);
        }

        UserDTO userDTO = userService.createUser(signupDTO);

        if (userDTO == null) {
            return new ResponseEntity<>("User not created, come again later", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
}
