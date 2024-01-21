package ma.emsi.ContactService.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
public class Categorie {
    private Long id;
    private String nom;
    private String description;

}
