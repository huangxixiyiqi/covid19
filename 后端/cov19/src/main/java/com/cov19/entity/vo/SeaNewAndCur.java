package com.cov19.entity.vo;

import lombok.Data;

@Data
public class SeaNewAndCur implements Comparable<SeaNewAndCur>{
    private String updateTime;
    private String countryName;
    private Long newConfirm;
    private Long currentConfirm;

    @Override
    public int compareTo(SeaNewAndCur o) {
        return (int) (o.getNewConfirm()-this.newConfirm);
    }
}
