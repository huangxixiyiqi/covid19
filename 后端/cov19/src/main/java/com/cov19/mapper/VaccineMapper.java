package com.cov19.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cov19.entity.Vaccine;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VaccineMapper extends BaseMapper<Vaccine> {

    @Select("select * from vaccine;")
    List<Vaccine> getAll();

    @Select("SELECT DISTINCT(location) location FROM vaccine;")
    List<String> getAllCountry();

    @Select("SELECT * from vaccine WHERE location=#{location}")
    List<Vaccine> getAllByLocation(@Param("location") String location);
}
