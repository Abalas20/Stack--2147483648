package ro.utcn.stack2147483648.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    public final static String TOKEN_PREFIX = "Bearer ";
    public final static String HEADER_STRING = "Authorization";

    @PostMapping("/authentication")
    public void createAuthenticationToken(@RequestBody AuthenticationRequest authRequest, HttpServletResponse response) throws IOException, JSONException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        } catch (BadCredentialsException badCredentialsException) {
            throw new BadCredentialsException("Incorrect Email or Password");
        } catch (DisabledException disabledException) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User is not created");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());

        Optional<User> user = userService.getUserByEmail(userDetails.getUsername());

        final String jwt = jwtService.generateToken(userDetails);

        if (user.isPresent()) {
            response.getWriter().write(new JSONObject().put("userId", user.get().getId()).toString());
        }

        response.addHeader("Access-Control-Expose-Headers", "Authorization");
        response.setHeader("Access-Control-Allow-Headers", "Authorization X-PINGOTHER, X-Requested-With, Content-Type, Accept, X-Custom-header");
        response.setHeader(HEADER_STRING, TOKEN_PREFIX + jwt);
    }
}
