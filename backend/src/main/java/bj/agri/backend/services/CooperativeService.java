package bj.agri.backend.services;

import bj.agri.backend.dto.request.CreateCooperativeRequest;
import bj.agri.backend.dto.response.CooperativeResponse;
import bj.agri.backend.enums.RoleCooperative;
import bj.agri.backend.exceptions.ResourceNotFoundException;
import bj.agri.backend.models.Cooperative;
import bj.agri.backend.models.Users;
import bj.agri.backend.repositories.CooperativeRepository;
import bj.agri.backend.repositories.UsersRepository;
import bj.agri.backend.security.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CooperativeService {

    private final CooperativeRepository cooperativeRepository;
    private final UsersRepository usersRepository;

    public CooperativeResponse creer(CreateCooperativeRequest req, AuthenticatedUser authUser) {
        Users responsable = usersRepository.findById(authUser.userId())
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur introuvable"));

        Cooperative coop = new Cooperative();
        coop.setName(req.name());
        coop.setCommune(req.commune());
        coop.setCodeInvitation(genererCode());
        coop.setResponsable(responsable);
        coop.setDateCreation(LocalDateTime.now());
        coop = cooperativeRepository.save(coop);

        responsable.setCooperative(coop);
        responsable.setRoleCooperative(RoleCooperative.RESPONSABLE);
        usersRepository.save(responsable);

        return CooperativeResponse.from(coop);
    }

    public void rejoindre(String codeInvitation, AuthenticatedUser authUser) {
        Cooperative coop = cooperativeRepository.findByCodeInvitation(codeInvitation)
                .orElseThrow(() -> new ResourceNotFoundException("Code invalide"));

        Users membre = usersRepository.findById(authUser.userId())
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur introuvable"));

        membre.setCooperative(coop);
        membre.setRoleCooperative(RoleCooperative.MEMBRE);
        usersRepository.save(membre);
    }

    private String genererCode() {
        return "COOP-" + (1000 + new Random().nextInt(9000));
    }
}
