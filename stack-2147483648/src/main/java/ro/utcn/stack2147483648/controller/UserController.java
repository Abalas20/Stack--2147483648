package ro.utcn.stack2147483648.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ro.utcn.stack2147483648.dto.AuthenticationRequest;
import ro.utcn.stack2147483648.dto.AuthenticationResponse;
import ro.utcn.stack2147483648.dto.SignupDTO;
import ro.utcn.stack2147483648.dto.UserDTO;
import ro.utcn.stack2147483648.entities.User;
import ro.utcn.stack2147483648.exception.UserNotCreatedException;
import ro.utcn.stack2147483648.exception.UserNotFoundException;
import ro.utcn.stack2147483648.service.UserService;
import ro.utcn.stack2147483648.service.jwt.JwtService;

import java.util.Optional;

@Slf4j
@RestController
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final JwtService jwtService;

    public UserController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, UserService userService, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.jwtService = jwtService;
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

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody AuthenticationRequest authRequest) {
        authenticateUser(authRequest);
        UserDetails userDetails = loadUserDetails(authRequest);
        String jwt = generateToken(userDetails);
        Long userId = getUserId(userDetails);
        return new ResponseEntity<>(new AuthenticationResponse(userId, jwt), HttpStatus.CREATED);
    }

    private void authenticateUser(AuthenticationRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect Email or Password", e);
        } catch (DisabledException e) {
            throw new UserNotCreatedException("User is not created", e);
        }
    }

    private UserDetails loadUserDetails(AuthenticationRequest authRequest) {
        return userDetailsService.loadUserByUsername(authRequest.getEmail());
    }

    private String generateToken(UserDetails userDetails) {
        return jwtService.generateToken(userDetails);
    }

    private Long getUserId(UserDetails userDetails) {
        Optional<User> user = userService.getUserByEmail(userDetails.getUsername());
        return user.map(User::getId).orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}