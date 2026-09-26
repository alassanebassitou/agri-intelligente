package bj.agri.backend.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginSuccessResponse {
    private String npi;
    private String password;
}
