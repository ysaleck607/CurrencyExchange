package com.example.devise.Offre;

import com.example.devise.Demande.StatutDemand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OffreRepository extends JpaRepository<Offre, Long> {
    List<Optional<Offre>> findByIdOffreurAndAndStatutOffreIn(Long idOffreur, List<StatutOffre> statutOffres);
}
