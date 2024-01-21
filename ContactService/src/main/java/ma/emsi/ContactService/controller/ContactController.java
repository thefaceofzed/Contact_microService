package ma.emsi.ContactService.controller;

import java.util.List;

import ma.emsi.ContactService.model.ContactDetails;
import ma.emsi.ContactService.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ma.emsi.ContactService.exception.ResourceNotFoundException;
import ma.emsi.ContactService.model.Contact;
import ma.emsi.ContactService.repository.ContactRepository;

@RestController
@RequestMapping("/api/Employes/")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8888"}, allowedHeaders = "*")
public class ContactController {
	@Autowired
	ContactRepository contactRepository;
	@Autowired
	private ContactService contactService;


	@PostMapping("/Ajout")
	public Contact CreateEmploye(@RequestBody Contact e) {
		return contactRepository.save(e);

	}

	@PutMapping("/Modifier/{id}")
	public ResponseEntity<Contact> modifierEmploye(@PathVariable Long id, @RequestBody Contact nouveauContact) {
		Contact contact = contactRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employe non trouvé avec l'ID : " + id));
		contact.setNom(nouveauContact.getNom());
		contact.setPrenom(nouveauContact.getPrenom());
		contact.setEmail(nouveauContact.getEmail());
		contact.setTele(nouveauContact.getTele());
		contact.setAdresse(nouveauContact.getAdresse());
		contact.setSalaire(nouveauContact.getSalaire());
		contact.setId_Departement(nouveauContact.getId_Departement());

		Contact updatedContact = contactRepository.save(contact);
		return ResponseEntity.ok(updatedContact);
	}
	@DeleteMapping("/{id}")
	public void deleteEmploye(@PathVariable Long id) {
		contactRepository.deleteById(id);
	}

	@GetMapping
	public List<ContactDetails> GetAllEmployes(){
		return contactService.findAll();
	}

	@GetMapping("/{id}")
	public ContactDetails getEmployeById(@PathVariable Long id) throws Exception{
		return contactService.findEmployeByID(id);
	}
	@GetMapping("/ParDepartement/{idDepartement}")
	public List<ContactDetails> getEmployesByDepartement(@PathVariable Long idDepartement) {
		return contactService.findEmployesByDepartement(idDepartement);
	}

	@GetMapping("/ParDepartement/Count/{idDepartement}")
	public ResponseEntity<Long> countEmployesByDepartement(@PathVariable Long idDepartement) {
		long count = contactService.countEmployesByDepartement(idDepartement);
		return ResponseEntity.ok(count);
	}

	@GetMapping("/Count/Total")
	public ResponseEntity<Long> countTotalEmployes() {
		long count = contactRepository.count();
		return ResponseEntity.ok(count);
	}
}
