package ma.emsi.ContactService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ma.emsi.ContactService.model.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long >{
    @Query("SELECT e FROM Contact e WHERE e.id_Departement = :departementId")
    java.util.List<Contact> findById_Departement(@Param("departementId") Long departementId);
}
