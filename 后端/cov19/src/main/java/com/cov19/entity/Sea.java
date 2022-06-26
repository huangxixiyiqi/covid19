package com.cov19.entity;


import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("海外数据")
public class Sea {
    private Long id;
    private String continentName;
    private String continentEnglishName;
    private String countryName;
    private String countryEnglishName;
    private String zipCode;
    private Long confirmedCount;
    private Long suspectedCount;
    private Long curedCount;
    private Long deadCount;
    private String updateTime;
}
