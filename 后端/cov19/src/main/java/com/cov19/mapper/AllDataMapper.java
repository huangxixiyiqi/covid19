package com.cov19.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cov19.entity.AllData;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AllDataMapper extends BaseMapper<AllData> {

    @Select("SELECT * FROM all_data WHERE country like \"China%\";")
    List<AllData> getChinaData();
}
