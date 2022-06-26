package com.cov19.entity.vo;

import lombok.Data;

@Data
public class SeaTendencyVo implements Comparable<SeaTendencyVo>{

    private String updateTime;
    private String countryName;
    private Long newConfirm;
    private Long currentConfirm;
    private Long suspectedCount;
    private Long curedCount;
    private Long deadCount;

    @Override
    public int compareTo(SeaTendencyVo o) {
        return this.updateTime.compareTo(o.getUpdateTime());
    }
}
