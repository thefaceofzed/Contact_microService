package ma.emsi.CalendrierService.repository;

import ma.emsi.CalendrierService.model.Calendrier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalendrierRepository extends JpaRepository<Calendrier, Long > {
    @Query("SELECT c FROM Calendrier c WHERE c.id_Employe = :employeId")
    List<Calendrier> findByEmployeId(@Param("employeId") Long employeId);
}
