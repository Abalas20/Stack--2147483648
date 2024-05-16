package ro.utcn.stack2147483648.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
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
import ro.utcn.stack2147483648.entities.User;
import ro.utcn.stack2147483648.service.UserService;
import ro.utcn.stack2147483648.service.jwt.JwtService;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@RestController
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final JwtService jwtService;

    public AuthenticationController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, UserService userService, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/authentication")
    public ResponseEntity<String> createAuthenticationToken(@RequestBody AuthenticationRequest authRequest, HttpServletResponse response) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        } catch (BadCredentialsException badCredentialsException) {
            return new ResponseEntity<>("Incorrect Email or Password", HttpStatus.UNAUTHORIZED);
        } catch (DisabledException disabledException) {
            return new ResponseEntity<>("User is not created", HttpStatus.NOT_FOUND);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());

        Optional<User> user = userService.getUserByEmail(userDetails.getUsername());

        final String jwt = jwtService.generateToken(userDetails);

        user.ifPresent(u -> {
            try {
                response.getWriter().write(new JSONObject().put("userId", u.getId()).toString());
            } catch (IOException | JSONException e) {
                log.error("Error occurred while processing user authentication", e);
            }
        });

        response.addHeader("Access-Control-Expose-Headers", "Authorization");
        response.setHeader("Access-Control-Allow-Headers", "Authorization X-PINGOTHER, X-Requested-With, Content-Type, Accept, X-Custom-header");
        response.setHeader("Authorization", "Bearer " + jwt);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}