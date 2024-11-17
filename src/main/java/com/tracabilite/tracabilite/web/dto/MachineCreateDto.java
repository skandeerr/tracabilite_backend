package com.tracabilite.tracabilite.web.dto;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MachineCreateDto {
    @Size(max=50)
    private String name;
}
