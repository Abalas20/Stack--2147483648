package ro.utcn.stack2147483648.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthenticationResponse {

    private String jwtToken;

    public AuthenticationResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
