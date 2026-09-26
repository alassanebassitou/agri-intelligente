package bj.agri.backend.services;

import bj.agri.backend.dto.request.CreateUserRequest;
import bj.agri.backend.dto.request.LoginRequest;
import bj.agri.backend.dto.response.LoginSuccessResponse;
import bj.agri.backend.dto.response.UsersResponse;
import bj.agri.backend.enums.Authorities;
import bj.agri.backend.exceptions.NPIAlreadyUseException;
import bj.agri.backend.exceptions.ResourceNotFoundException;
import bj.agri.backend.models.Users;
import bj.agri.backend.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager manager;
    private final UsersRepository usersRepository;
    private final PasswordEncoder encoder;

    @Transactional
    public UsersResponse registerUser(CreateUserRequest request) {

        Users users = new Users();
        if (usersRepository.existsByNpi(request.getNpi())) {
            throw new NPIAlreadyUseException(request.getNpi());
        }
        users.setLastname(request.getLastname());
        users.setFirstname(request.getFirstname());
        users.setNpi(request.getNpi());
        users.setPassword(encoder.encode(request.getPassword()));
        users.setPhone(request.getPhone());
        users.setLangue(request.getLangue());
        users.setTypeUser(request.getTypeActeur());
        users.setDateCreation(LocalDateTime.now());

        switch (request.getTypeActeur()) {
            case ACHETEUR -> users.setRole(Authorities.ROLE_ACHETEUR);
            case AGENT_ETAT -> users.setRole(Authorities.ROLE_AGENT_ETAT);
            case AGRICULTEUR -> users.setRole(Authorities.ROLE_AGRICULTEUR);
        }

        return UsersResponse.from(usersRepository.save(users));
    }

    public LoginSuccessResponse login(LoginRequest request) {
        try{
            manager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getNpi(),
                            request.getPassword()
                    ));
            return LoginSuccessResponse.builder()
                    .npi(request.getNpi())
                    .password(request.getPassword())
                    .build();

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Password or Username incorrect");
        }
    }

    //public UsersResponse getMyself() {}
}
