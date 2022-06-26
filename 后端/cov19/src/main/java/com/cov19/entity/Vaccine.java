package com.cov19.entity;

import lombok.Data;

@Data
public class Vaccine {
    private Long id;
    private String  location;
    private String date;
    private String vaccine;
    private Long totalVaccinations;
    private Long peopleVaccinated;
    private Long fullyVaccinated;
}
