package ma.emsi.CalendrierService.controller;

import ma.emsi.CalendrierService.exception.ResourceNotFoundException;
import ma.emsi.CalendrierService.model.Calendrier;
import ma.emsi.CalendrierService.model.CalendrierDetails;
import ma.emsi.CalendrierService.repository.CalendrierRepository;
import ma.emsi.CalendrierService.service.CongeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/Conges/")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8888"}, allowedHeaders = "*")
public class CalendrierController {
    @Autowired
    CalendrierRepository calendrierRepository;
    @Autowired
    private CongeService congeService;


    @PostMapping("/Ajout")
    public Calendrier CreateConge(@RequestBody Calendrier e) {
        return calendrierRepository.save(e);
    }

    @PutMapping("/Modifier/{id}")
    public ResponseEntity<Calendrier> modifierConge(@PathVariable Long id, @RequestBody Calendrier nouveauCalendrier) {
        Calendrier calendrier = calendrierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Conge non trouvé avec l'ID : " + id));

        calendrier.setDateDebut(nouveauCalendrier.getDateDebut());
        calendrier.setDateFin(nouveauCalendrier.getDateFin());
        calendrier.setEvenement(nouveauCalendrier.getEvenement());
        calendrier.setId_Employe(nouveauCalendrier.getId_Employe());

        Calendrier updatedCalendrier = calendrierRepository.save(calendrier);
        return ResponseEntity.ok(updatedCalendrier);
    }

    @DeleteMapping("/{id}")
    public void deleteConge(@PathVariable Long id) {
        calendrierRepository.deleteById(id);
    }

    @GetMapping
    public List<CalendrierDetails> GetAllConges(){
        return congeService.findAll();
    }

    @GetMapping("/{id}")
    public CalendrierDetails getCongeById(@PathVariable Long id) throws Exception{
        return congeService.findCongeByID(id);
    }
    @GetMapping("/employe/{employeId}")
    public List<CalendrierDetails> getCongesByEmployeId(@PathVariable Long employeId) {
        return congeService.findCongesByEmployeId(employeId);
    }
}
