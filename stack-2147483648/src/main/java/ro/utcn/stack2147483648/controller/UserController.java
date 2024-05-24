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
import org.springframework.web.bind.annotation.*;
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
        String role = getUserRole(userId);
        String status = getStatus(userId);
        if (status.equals("banned")) {
            return new ResponseEntity<>(new AuthenticationResponse(userId, jwt, role, status), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(new AuthenticationResponse(userId, jwt, role, status), HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUser(@PathVariable Long userId) {
        Optional<UserDTO> user = userService.getUserById(userId);
        if (user.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }

    @GetMapping("/user/score/{userId}")
    public ResponseEntity<?> getUserScore(@PathVariable Long userId) {
        Optional<Double> score = userService.getUserScore(userId);
        if (score.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(score.get(), HttpStatus.OK);
    }

    @GetMapping("/all-users/{pageNumber}")
    public ResponseEntity<?> getAllUsers(@PathVariable int pageNumber) {
        return new ResponseEntity<>(userService.getAllUsers(pageNumber), HttpStatus.OK);
    }

    @PutMapping("/ban-user/{userId}")
    public ResponseEntity<?> manageStatus(@PathVariable Long userId) {
        UserDTO user = userService.manageStatus(userId);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }

    private String getUserRole(Long userId) {
        Optional<String> role = userService.getUserRole(userId);
        if (role.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        return role.get();
   }

    private String getStatus(Long userId) {
          Optional<String> status = userService.getUserStatus(userId);
          if (status.isEmpty()) {
                throw new UserNotFoundException("User not found");
          }
          return status.get();
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