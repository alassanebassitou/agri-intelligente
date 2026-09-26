package bj.agri.backend.repositories;

import bj.agri.backend.models.Maladie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaladieRepository extends JpaRepository<Maladie,Long> {
    List<Maladie> findByCulturesId(Long id);
}
