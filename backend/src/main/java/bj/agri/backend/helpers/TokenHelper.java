package bj.agri.backend.helpers;

import bj.agri.backend.dto.response.LoginSuccessResponse;
import bj.agri.backend.models.Users;
import bj.agri.backend.repositories.UsersRepository;
import bj.agri.backend.security.AuthenticatedUser;
import bj.agri.backend.security.JwtService;
import bj.agri.backend.security.UserDetailService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static bj.agri.backend.utils.Constants.HEADER_STRING;
import static bj.agri.backend.utils.Constants.TOKEN_PREFIX;

@Component
@RequiredArgsConstructor
public class TokenHelper {

    private final UserDetailService usersDetailService;
    private final JwtService jwtService;
    private final UsersRepository userRepository;


    public void setToken(LoginSuccessResponse success, HttpServletResponse response) throws IOException, JSONException {
        final UserDetails userDetails = usersDetailService.loadUserByUsername(success.getNpi());
        Optional<Users> optionalUser = userRepository.findByNpi(success.getNpi());

        Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();
                grantedAuthorities.add(new SimpleGrantedAuthority(optionalUser.get().getRole().name()));

        AuthenticatedUser authUser = new AuthenticatedUser(
                optionalUser.get().getId(),
                userDetails.getUsername(),
                null,
                grantedAuthorities);


        final String jwt = jwtService.generateToken(authUser);

        response.addHeader("Access-Control-Expose-Headers", HEADER_STRING);
        response.addHeader("X-User-Role", optionalUser.get().getRole().name());
        response.addHeader("Access-Control-Allow-Headers", "Authorization, X-PINGOTHER, origin," +
                " X-Requested-With, Content-type, Accept, Custom-Header");
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + jwt);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new JSONObject()
                .put("userId", optionalUser.get().getId())
                .put("role", optionalUser.get().getRole().name())
                .put("refreshToken", UUID.randomUUID().toString())
                .toString());
        response.getWriter().flush();
    }
}
