package ma.emsi.CalendrierService.service;

import ma.emsi.CalendrierService.model.Calendrier;
import ma.emsi.CalendrierService.model.CalendrierDetails;
import ma.emsi.CalendrierService.model.Contact;
import ma.emsi.CalendrierService.repository.CalendrierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class CongeService {
    @Autowired
    CalendrierRepository calendrierRepository;
    @Autowired
    private RestTemplate restTemplate;

    private final String URLEmployes="http://localhost:8888/SERVICE-EMPLOYE";

    public List<CalendrierDetails> findAll(){

        List<Calendrier> calendriers = calendrierRepository.findAll();
        ResponseEntity<Contact[]> response=restTemplate.getForEntity(this.URLEmployes+"/api/Employes/", Contact[].class);
        Contact[] contact =response.getBody();

        return calendriers.stream().map((Calendrier e)->
                        mapToCongeResponse(e, contact))
                .toList();


    }
    public CalendrierDetails findCongeByID(Long id) throws Exception{

        Calendrier calendrier = calendrierRepository.findById(id).orElseThrow(()->new Exception("Conge Invalid"));
        Contact contact =restTemplate.getForObject(this.URLEmployes+"/api/Employes/"+ calendrier.getId_Employe(), Contact.class);


        return CalendrierDetails.builder()
                .id(calendrier.getId())
                .dateDebut(calendrier.getDateDebut())
                .dateFin(calendrier.getDateFin())
                .evenement(calendrier.getEvenement())
                .contact(contact)
                .build();

    }
    private CalendrierDetails mapToCongeResponse(Calendrier calendrier, Contact[] contacts){

        Contact foundContact = Arrays.stream(contacts)
                .filter(contact -> contact.getId().equals(calendrier.getId_Employe())).findFirst()
                .orElse(null);

        return CalendrierDetails.builder()
                .id(calendrier.getId())
                .dateDebut(calendrier.getDateDebut())
                .dateFin(calendrier.getDateFin())
                .evenement(calendrier.getEvenement())
                .contact(foundContact)
                .build();

    }
    public List<CalendrierDetails> findCongesByEmployeId(Long employeId) {
        List<Calendrier> calendriers = calendrierRepository.findByEmployeId(employeId); // Ajouter cette méthode dans votre interface CongeRepository
        Contact contact = restTemplate.getForObject(URLEmployes + "/api/Employes/" + employeId, Contact.class);

        return calendriers.stream()
                .map(calendrier -> CalendrierDetails.builder()
                        .id(calendrier.getId())
                        .dateDebut(calendrier.getDateDebut())
                        .dateFin(calendrier.getDateFin())
                        .evenement(calendrier.getEvenement())
                        .contact(contact)
                        .build())
                .toList();
    }
}
