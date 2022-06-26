package com.cov19.entity.vo;

import lombok.Data;

@Data
public class VaccineVo {

    private Long id;
    private String  location;
    private String date;
    private String vaccine;
    private Long totalVaccinations;
    private Long peopleVaccinated;
    private Long fullyVaccinated;
    private Long newConfirm;
}
