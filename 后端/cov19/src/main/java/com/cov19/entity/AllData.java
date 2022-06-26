package com.cov19.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("全部数据")
public class AllData {

    @ApiModelProperty("ID")
    private Long id;
    @ApiModelProperty("日期")
    private String date;
    @ApiModelProperty("累计确诊")
    private Long sumConfirmed;
    @ApiModelProperty("新增确诊")
    private Long newConfirmed;
    @ApiModelProperty("累计死亡")
    private Long sumDeath;
    @ApiModelProperty("新增死亡")
    private Long newDeath;
    @ApiModelProperty("累计治愈")
    private Long sumRecovered;
    @ApiModelProperty("新增治愈")
    private Long newRecovered;
    @ApiModelProperty("纬度")
    private Double latitude;
    @ApiModelProperty("经度")
    private Double longitude;
    @ApiModelProperty("ISO2")
    private String ISO2;
    @ApiModelProperty("ISO3")
    private String ISO3;
    @ApiModelProperty("国家")
    private String country;
    @ApiModelProperty("地区")
    private String adminRegion1;
    @ApiModelProperty("地区")
    private String adminRegion2;

}
