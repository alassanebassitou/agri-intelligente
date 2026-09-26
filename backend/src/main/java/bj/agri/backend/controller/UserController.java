package bj.agri.backend.controller;

import bj.agri.backend.dto.request.UpdateUserRequest;
import bj.agri.backend.dto.response.UsersResponse;
import bj.agri.backend.security.AuthenticatedUser;
import bj.agri.backend.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;


    @GetMapping("/{userId}")
    public ResponseEntity getUser(@PathVariable("userId") Long userId) {
        UsersResponse response = userService.getById(userId);
        return ResponseEntity.status(200).body(response);
    }

    @PutMapping
    public ResponseEntity<UsersResponse> modifyUser(@RequestBody UpdateUserRequest request, @AuthenticationPrincipal AuthenticatedUser authUser) {
        UsersResponse userResponse = userService.modifyUser(request,authUser);
        return ResponseEntity.ok(userResponse);
    }
}
