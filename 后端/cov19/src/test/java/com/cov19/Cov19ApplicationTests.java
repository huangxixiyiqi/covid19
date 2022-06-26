package com.cov19;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cov19.entity.ChinaData;
import com.cov19.mapper.ChinaDataMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class Cov19ApplicationTests {

    @Autowired
    private ChinaDataMapper mapper;

    @Test
    void contextLoads() {
        List<ChinaData> datas = mapper.selectList(null);
        datas.forEach(System.out::println);
    }

}
