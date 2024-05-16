package ro.utcn.stack2147483648.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthenticationRequest {
    private String email;
    private String password;
}
