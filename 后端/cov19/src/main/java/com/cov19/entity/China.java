package com.cov19.entity;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("中国数据")
public class China {

    private Long id;
    private String continentName;
    private String continentEnglishName;
    private String countryName;
    private String countryEnglishName;
    private String provinceName;
    private String englishName;
    private String zipCode;
    private Long confirmedCount;
    private Long suspectedCount;
    private Long curedCount;
    private Long deadCount;
    private String updateTime;

}
