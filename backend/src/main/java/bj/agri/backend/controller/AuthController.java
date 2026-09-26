package bj.agri.backend.controller;

import bj.agri.backend.dto.request.CreateUserRequest;
import bj.agri.backend.dto.request.LoginRequest;
import bj.agri.backend.dto.response.LoginSuccessResponse;
import bj.agri.backend.dto.response.UsersResponse;
import bj.agri.backend.helpers.TokenHelper;
import bj.agri.backend.services.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final TokenHelper tokenHelper;

    @PostMapping("/register")
    public ResponseEntity<UsersResponse> registerUser(@Valid @RequestBody CreateUserRequest request) {
        UsersResponse response = authService.registerUser(request);
        return ResponseEntity.status(201).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest request, HttpServletResponse response) throws JSONException, IOException {
        LoginSuccessResponse loginSuccessResponse = authService.login(request);
        tokenHelper.setToken(loginSuccessResponse, response);
        return ResponseEntity.noContent().build();
    }

    //@GetMapping("/me")
    //public ResponseEntity<UsersResponse> getMyself() {}
}
