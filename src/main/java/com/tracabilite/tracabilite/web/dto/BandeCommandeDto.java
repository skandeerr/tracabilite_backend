package com.tracabilite.tracabilite.web.dto;


import lombok.*;

import java.time.LocalDate;
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BandeCommandeDto {
    private Long id;

    private LocalDate startDate;

    private LocalDate endDate;
    private String designation;
    private Long personnelId;
    private ClientDto client;
    private String bandeCommandeStatus;
}
