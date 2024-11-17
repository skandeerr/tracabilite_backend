package com.tracabilite.tracabilite.persistence.model;

import io.swagger.annotations.ApiModel;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "t_matiere")
@ApiModel(description = "Matiere")
public class Matiere extends TimestampEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ref")
    private String ref;
    @Column(name = "tonnage")
    private Float tonnage;
    @OneToMany(mappedBy = "matiere", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Article> articles;


}
