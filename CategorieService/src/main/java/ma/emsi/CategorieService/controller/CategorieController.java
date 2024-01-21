package ma.emsi.CategorieService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ma.emsi.CategorieService.model.Categorie;
import ma.emsi.CategorieService.repository.CategorieRepository;

@RestController
@RequestMapping("/api/Departement/")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8888"}, allowedHeaders = "*")
public class CategorieController {

	@Autowired
	private CategorieRepository categorieRepository;


	@PostMapping("/Ajout")
	public Categorie CreateDepartement(@RequestBody Categorie d) {
		return categorieRepository.save(d);

	}
	@GetMapping
	public List<Categorie> GetAllDepartements(){
		return categorieRepository.findAll();
	}
	
    @DeleteMapping("/{id}")
    public Categorie deleteDepartement(@PathVariable Long id) {
        // Supprime le département par son ID
        Categorie categorieToDelete = categorieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Departement non trouvé avec l'ID : " + id));

        categorieRepository.delete(categorieToDelete);

        // Retourne le département supprimé si nécessaire
        return categorieToDelete;
    }

	@GetMapping("/{id}")
	public Categorie getDepartementById(@PathVariable Long id) throws Exception{

		return categorieRepository.findById(id).orElseThrow(() -> new Exception("Departement Invalid"));
	}

	@PutMapping("/Modifier/{id}")
	public Categorie UpdateDepartement(@PathVariable Long id , @RequestBody Categorie categorie) throws Exception {
		Categorie findDepartement= categorieRepository.findById(id).orElseThrow(() -> new Exception("Departement Invalid"));
		if (findDepartement != null){
			findDepartement.setNom(categorie.getNom());
			findDepartement.setDescription(categorie.getDescription());
		}
		categorieRepository.save(findDepartement);
		return  findDepartement;
	}
	@GetMapping("/Nom/{id}")
	public String getNomDepartementById(@PathVariable Long id) throws Exception {
		Categorie categorie = categorieRepository.findById(id)
				.orElseThrow(() -> new Exception("Departement Invalid"));

		return categorie.getNom();
	}
}
