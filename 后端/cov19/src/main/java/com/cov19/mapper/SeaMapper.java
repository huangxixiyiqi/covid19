package com.cov19.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cov19.entity.Sea;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeaMapper extends BaseMapper<Sea> {

    @Select("SELECT DISTINCT(country_name) country_name from sea;")
    List<String> getCountryName();


    @Select("SELECT * FROM sea where update_time like (SELECT  max(update_time) max_time from sea WHERE country_name = #{country}) and country_name = #{country};")
    List<Sea> getNowData(@Param("country") String country);

    @Select("SELECT * FROM sea where update_time like #{yest} and country_name = #{country}")
    List<Sea> getNowAndYestData(@Param("yest") String time, @Param("country") String country);

    @Select("SELECT * FROM sea where update_time like (SELECT  DATE_FORMAT(max(update_time),\"%Y-%m-%d%\") max_time from sea);")
    List<Sea> getNowAllSea();

    @Select("SELECT * FROM sea where update_time like #{yest}")
    List<Sea> getYestAllSea(@Param("yest") String time);

    @Select("SELECT * FROM sea WHERE update_time like \"2021%\" and country_name = #{country}")
    List<Sea> getSeaDataFromCountry(@Param("country") String country);


    @Select("SELECT * FROM sea WHERE update_time like \"2021%\" and country_english_name = #{country}")
    List<Sea> getSeaDataFromEnCountry(@Param("country") String country);

    @Select("SELECT * FROM sea where update_time like #{yest} and country_english_name = #{country}")
    List<Sea> getNowAndYestDataByEn(@Param("yest") String time, @Param("country") String country);
}
