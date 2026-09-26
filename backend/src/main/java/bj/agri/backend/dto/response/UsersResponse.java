package bj.agri.backend.dto.response;

import bj.agri.backend.models.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsersResponse {
    private Long id;
    private String lastname;
    private String firstname;
    private String phone;
    private String typeUser;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static UsersResponse from(Users users) {
        UsersResponse response = new UsersResponse();
        response.setId(users.getId());
        response.setLastname(users.getLastname());
        response.setFirstname(users.getFirstname());
        response.setPhone(users.getPhone());
        response.setTypeUser(users.getTypeUser().name());
        response.setCreatedAt(users.getDateCreation());
        response.setUpdatedAt(users.getDateModification());
        return response;
    }
}
