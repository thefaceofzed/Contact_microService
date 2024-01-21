package ma.emsi.CalendrierService.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
public class Contact {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String tele;
    private String adresse;
    private double salaire;
    private Long id_Departement;
}
