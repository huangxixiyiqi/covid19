package com.cov19.entity.vo;

import lombok.Data;

@Data
public class TendencyVo implements Comparable<TendencyVo>{
    private String updateTime;
    private String provinceName;
    private Long newConfirm;
    private Long currentConfirm;
    private Long suspectedCount;
    private Long curedCount;
    private Long deadCount;

    @Override
    public int compareTo(TendencyVo o) {
        return this.updateTime.compareTo(o.getUpdateTime());
    }
}
