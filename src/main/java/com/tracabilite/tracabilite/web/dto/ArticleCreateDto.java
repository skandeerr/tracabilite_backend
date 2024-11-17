package com.tracabilite.tracabilite.web.dto;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleCreateDto {
    @Size(max=50)
    private String designation;
    @Size(max=50)
    private String code;

    private Long matiere;
    private Float largeur;
    private Float longeur;
    private Float epaisseur;
    private Float tonnage;
}
