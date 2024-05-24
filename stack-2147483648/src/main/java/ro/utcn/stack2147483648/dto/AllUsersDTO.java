package ro.utcn.stack2147483648.dto;

import lombok.Data;

import java.util.List;

@Data
public class AllUsersDTO {
    private List<UserDTO> users;
    private Integer totalPages;
    private Integer pageNumber;
}
