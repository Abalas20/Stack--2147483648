package ro.utcn.stack2147483648.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
    private String role;
    private double score;
    private String status;

    public UserDTO(Long id, String lastName, String firstName, String phone, String email, String password, String username, String role, double score, String status) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.username = username;
        this.role = role;
        this.score = score;
        this.status = status;
    }
}
