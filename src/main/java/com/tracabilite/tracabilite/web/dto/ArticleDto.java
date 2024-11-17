package com.tracabilite.tracabilite.web.dto;

import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleDto {
    private Long id;
    @Size(max=50)
    private String designation;
    @Size(max=50)
    private String code;

    private MatiereDto matiere;
    private Float largeur;
    private Float longeur;
    private Float epaisseur;
    private Float tonnage;
}
