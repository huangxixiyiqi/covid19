package com.cov19.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cov19.entity.ChinaData;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface ChinaDataMapper extends BaseMapper<ChinaData> {

//    选出所有日期
    @Select("SELECT DISTINCT date FROM china_data")
    List<Date> selectAllDate();


    List<Date> selectAllDateByXml();
}
