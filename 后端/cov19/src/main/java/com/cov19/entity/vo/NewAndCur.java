package com.cov19.entity.vo;

import lombok.Data;

@Data
public class NewAndCur implements Comparable<NewAndCur>  {
    private String updateTime;
    private String provinceName;
    private Long newConfirm;
    private Long currentConfirm;

    @Override
    public int compareTo(NewAndCur o) {
        return (int) (o.getNewConfirm()-this.newConfirm);
    }
}
