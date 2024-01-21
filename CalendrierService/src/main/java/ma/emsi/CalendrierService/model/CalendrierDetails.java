package ma.emsi.CalendrierService.model;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
public class CalendrierDetails {
    private Long id;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Evenement evenement;
    private Contact contact;
}
