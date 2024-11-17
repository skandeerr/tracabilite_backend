package com.tracabilite.tracabilite.persistence.model;

import com.tracabilite.tracabilite.persistence.enumeration.BandeCommandeStatus;
import io.swagger.annotations.ApiModel;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "t_bande_commande")
@ApiModel(description = "BandeCommande")
public class BandeCommande extends TimestampEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "startDate")
    private LocalDate startDate;
    @Column(name = "designation")
    private String designation;
    @Column(name = "endDate")
    private LocalDate endDate;
    @Column(name = "personnel_id")
    private Long personnelId;
    @Column(name = "bande_commande_status")
    private BandeCommandeStatus bandeCommandeStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

}
