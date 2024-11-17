package com.tracabilite.tracabilite.web.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BandeLivraisonDto {
    private Long id;

    private BandeCommandeDto bandeCommande;
    private ArticleDto article;
    private Float quantite;
    private String bandeLivraisonStatus;
}
