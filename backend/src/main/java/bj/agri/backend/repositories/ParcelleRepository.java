package bj.agri.backend.repositories;

import bj.agri.backend.models.Parcelle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParcelleRepository extends JpaRepository<Parcelle, Long> {
    Page<Parcelle> findAll(Pageable pageable);

    @EntityGraph()
    Page<Parcelle> findAllByOwnerId(Pageable pageable, Long userId);
    Optional<Parcelle> findByIdAndOwnerId(Long parId, Long userId);
}
