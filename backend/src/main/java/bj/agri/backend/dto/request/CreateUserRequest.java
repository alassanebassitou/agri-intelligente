package bj.agri.backend.dto.request;

import bj.agri.backend.enums.TypeUser;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserRequest {

    @NotNull
    private String npi;

    @NotNull
    private String lastname;
    private String firstname;

    @NotNull
    private String password;

    @NotNull
    private TypeUser typeActeur;

    private String phone;
    private String langue;
}
