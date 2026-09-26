package bj.agri.backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static bj.agri.backend.utils.Constants.*;

@Component
@RequiredArgsConstructor
public class JwtFilterComponent extends OncePerRequestFilter {

    private final UserDetailService userDetailService;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String reqHeader = request.getHeader(HEADER_STRING);

        if (reqHeader != null && reqHeader.startsWith(TOKEN_PREFIX)
                && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                String token = reqHeader.substring(TOKEN_PREFIX.length());
                AuthenticatedUser authUser = jwtService.extractAuthUser(token);
                final String username = authUser.getUsername();

                if (username != null) {
                    UserDetails userDetails = userDetailService.loadUserByUsername(username);

                    if (jwtService.validateToken(token, userDetails)) {
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);

                        request.setAttribute(USERNAME, userDetails.getUsername());
                        request.setAttribute(USER_ID, authUser.userId());
                    }
                }
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }
}
