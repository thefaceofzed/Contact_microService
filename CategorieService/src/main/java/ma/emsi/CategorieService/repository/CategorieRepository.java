package ma.emsi.CategorieService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ma.emsi.CategorieService.model.Categorie;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Long> {

	
}
