package ro.utcn.stack2147483648.dto;

import lombok.Data;

@Data
public class SignupDTO {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
