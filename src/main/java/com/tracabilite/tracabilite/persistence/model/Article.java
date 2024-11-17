package com.tracabilite.tracabilite.persistence.model;

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
@Table(name = "t_article")
@ApiModel(description = "Article")
public class Article extends TimestampEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "designation")
    private String designation;
    @Column(name = "code")
    private String code;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matiere_id", referencedColumnName = "id")
    private Matiere matiere;

    @Column(name = "largeur")
    private Float largeur;
    @Column(name = "longeur")
    private Float longeur;
    @Column(name = "epaisseur")
    private Float epaisseur;
    @Column(name = "tonnage")
    private Float tonnage;




}
