package com.cov19.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("中国数据")
public class ChinaData {
    @ApiModelProperty("ID")
    private Long id;
    @ApiModelProperty("日期")
    private Date date;
    @ApiModelProperty("省份")
    private String province;
    @ApiModelProperty("新增疑似")
    private Long newSuspected;
    @ApiModelProperty("累计疑似")
    private Long sumSuspected;
    @ApiModelProperty("新增确诊")
    private Long newDiagnosis;
    @ApiModelProperty("累计确诊")
    private Long sumDiagnosis;
    @ApiModelProperty("新增死亡")
    private Long newDeath;
    @ApiModelProperty("累计死亡")
    private Long sumDeath;
}
