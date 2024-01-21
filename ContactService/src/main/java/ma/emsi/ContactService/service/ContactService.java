package ma.emsi.ContactService.service;

import ma.emsi.ContactService.model.Categorie;
import ma.emsi.ContactService.model.Contact;
import ma.emsi.ContactService.model.ContactDetails;
import ma.emsi.ContactService.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.Arrays;
import java.util.List;

@Service
public class ContactService {

    @Autowired
    ContactRepository contactRepository;
    @Autowired
    private RestTemplate restTemplate;

    private final String URLDepartements="http://localhost:8888/SERVICE-DEPARTEMENT";

    public List<ContactDetails> findAll(){

        List<Contact> contacts = contactRepository.findAll();
        ResponseEntity<Categorie[]> response=restTemplate.getForEntity(this.URLDepartements+"/api/Departement/", Categorie[].class);
        Categorie[] categorie =response.getBody();

        return contacts.stream().map((Contact e)->
                        mapToEmployeResponse(e, categorie))
                .toList();


    }
    public ContactDetails findEmployeByID(Long id) throws Exception{

        Contact contact = contactRepository.findById(id).orElseThrow(()->new Exception("Employe Invalid"));
        Categorie categorie =restTemplate.getForObject(this.URLDepartements+"/api/Departement/"+ contact.getId_Departement(), Categorie.class);


        return ContactDetails.builder()
                .id(contact.getId())
                .nom(contact.getNom())
                .prenom(contact.getPrenom())
                .email(contact.getEmail())
                .tele(contact.getTele())
                .adresse(contact.getAdresse())
                .salaire(contact.getSalaire())
                .categorie(categorie)
                .build();

    }
    private ContactDetails mapToEmployeResponse(Contact contact, Categorie[] categories){


        Categorie foundCategorie = Arrays.stream(categories)
                .filter(department->department.getId().equals(contact.getId_Departement())).findFirst()
                .orElse(null);

        return ContactDetails.builder()
                .id(contact.getId())
                .nom(contact.getNom())
                .prenom(contact.getPrenom())
                .email(contact.getEmail())
                .tele(contact.getTele())
                .adresse(contact.getAdresse())
                .salaire(contact.getSalaire())
                .categorie(foundCategorie)
                .build();

    }



    public List<ContactDetails> findEmployesByDepartement(Long idDepartement) {
        List<Contact> contacts = contactRepository.findById_Departement(idDepartement);
        return contacts.stream().map((Contact e) -> mapToEmployeResponse(e, getDepartements()))
                .toList();
    }
    public long countEmployesByDepartement(Long idDepartement) {
        List<ContactDetails> employes = findEmployesByDepartement(idDepartement);
        return employes.size();
        // Ou utilisez employes.stream().count() pour Java 8 et versions ultérieures
    }

    // Méthode pour récupérer tous les départements
    private Categorie[] getDepartements() {
        ResponseEntity<Categorie[]> response = restTemplate.getForEntity(this.URLDepartements + "/api/Departement/",
                Categorie[].class);
        return response.getBody();
    }
}
