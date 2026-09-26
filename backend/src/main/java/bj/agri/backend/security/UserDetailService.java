package bj.agri.backend.security;

import bj.agri.backend.models.Users;
import bj.agri.backend.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String npi) throws UsernameNotFoundException {

        if (npi == null || npi.isEmpty()) {
            throw new UsernameNotFoundException("Username is empty");
        }

        Optional<Users> user = usersRepository.findByNpi(npi);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(user.get().getRole().name()));

        return new AuthenticatedUser(
                user.get().getId(),
                user.get().getNpi(),
                user.get().getPassword(),
                authorities
        );
    }
}
