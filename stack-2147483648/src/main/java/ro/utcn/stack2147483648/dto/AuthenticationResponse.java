package ro.utcn.stack2147483648.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class AuthenticationResponse {
    private Long userId;
    private String jwtToken;
    private String role;
    private String status;
}
