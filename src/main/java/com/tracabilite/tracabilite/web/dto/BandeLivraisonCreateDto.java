package com.tracabilite.tracabilite.web.dto;

import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BandeLivraisonCreateDto {
    private Long bandeCommandeId;
    private Long articleId;
    private Float quantite;
    private String bandeLivraisonStatus;
}
