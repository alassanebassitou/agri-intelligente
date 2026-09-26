package bj.agri.backend.repositories;

import bj.agri.backend.models.ObservationClimat;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ObservationClimatRepository extends JpaRepository<ObservationClimat,Long> {
    Optional<ObservationClimat> findTop30ByParcelleIdOrderByJourDesc(Long parcelleId);

    List<ObservationClimat> findByParcelleIdOrderByJourDesc(Long id, PageRequest of);
}
