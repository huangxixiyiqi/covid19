package com.cov19.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cov19.entity.China;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ChinaMapper extends BaseMapper<China> {

    @Select("SELECT DISTINCT(province_name) province_name from china;")
    List<String> getProvinceName();

    @Select("SELECT * FROM china where update_time like (SELECT  max(update_time) max_time from china WHERE province_name = #{country}) and province_name = #{country};")
    List<China> getNowData(@Param("country") String country);

    @Select("SELECT * FROM china where update_time like #{yest} and province_name = #{country}")
    List<China> getNowAndYestData(@Param("yest") String time,@Param("country") String country);



    @Select("SELECT * FROM china where update_time like (SELECT  DATE_FORMAT(max(update_time),\"%Y-%m-%d%\") max_time from china WHERE province_name = \"中国\");")
    List<China> getNowAllChina();


    @Select("SELECT * FROM china where update_time like #{yest}")
    List<China> getYestAllChina(@Param("yest") String time);


    @Select("SELECT * FROM china WHERE update_time like \"2021%\" and province_name = #{country}")
    List<China> getChinaDataFromCountry(@Param("country") String country);


}
