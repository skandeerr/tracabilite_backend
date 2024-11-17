package com.tracabilite.tracabilite.web.dto;

import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MachineDto {

    private Long id;
    @Size(max=50)
    private String name;
}
