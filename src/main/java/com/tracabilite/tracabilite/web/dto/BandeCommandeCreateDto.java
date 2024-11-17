package com.tracabilite.tracabilite.web.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BandeCommandeCreateDto {
    private LocalDate startDate;

    private LocalDate endDate;
    private String designation;
    private Long personnelId;
    private Long client;
    private String bandeCommandeStatus;
}
