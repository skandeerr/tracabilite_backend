package com.tracabilite.tracabilite.web.dto;

import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatiereCreateDto {
    @Size(max=50)
    private String ref;
    private Float tonnage;
}
