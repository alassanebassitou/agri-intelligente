package bj.agri.backend.services;

import bj.agri.backend.dto.request.UpdateUserRequest;
import bj.agri.backend.dto.response.UsersResponse;
import bj.agri.backend.exceptions.ResourceNotFoundException;
import bj.agri.backend.models.Users;
import bj.agri.backend.repositories.UsersRepository;
import bj.agri.backend.security.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersRepository usersRepository;

    public UsersResponse getById(Long userId) {
        return UsersResponse.from(this.getUser(userId));
    }

    @Transactional
    public UsersResponse modifyUser(UpdateUserRequest request, AuthenticatedUser authUser) {

        Users users =this.getUser(authUser.userId());
        if (!request.getLastname().isEmpty()) {
            users.setLastname(request.getLastname());
        }

        if (!request.getFirstname().isEmpty()) {
            users.setFirstname(request.getFirstname());
        }

        if(request.getPhone().isEmpty()) {
            users.setPhone(request.getPhone());
        }

        usersRepository.save(users);

        return UsersResponse.from(users);
    }

    //public Page<UsersResponse> getUserByType()

    private Users getUser(Long userId) {
        return usersRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
