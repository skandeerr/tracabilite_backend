package com.tracabilite.tracabilite.persistence.model;

import com.tracabilite.tracabilite.persistence.enumeration.BandeCommandeStatus;
import com.tracabilite.tracabilite.persistence.enumeration.BandeLivraisonStatus;
import io.swagger.annotations.ApiModel;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "t_bande_livraison")
@ApiModel(description = "BandeLivraison")
public class BandeLivraison extends TimestampEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bande_commande_id", referencedColumnName = "id")
    private BandeCommande bandeCommande;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", referencedColumnName = "id")
    private Article article;

    @Column(name = "quantite")
    private Float quantite;

    @Column(name = "bande_livraison_status")
    private BandeLivraisonStatus bandeLivraisonStatus;
}
