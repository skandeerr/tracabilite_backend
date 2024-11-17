package com.tracabilite.tracabilite.web.dto;

import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientDto {

    private Long id;

    @Size(max=50)
    private String nom;
    @Size(max=50)
    private String prenom;
    @Size(max=50)
    private String adresse;

    @Size(max=50)
    private String numero;

    @Size(max=50)
    private String email;
}
