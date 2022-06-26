package com.cov19.controller;

import com.cov19.entity.AllData;
import com.cov19.mapper.AllDataMapper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AllDataController {

    private AllDataMapper mapper;

    AllDataController(AllDataMapper mapper) {
        this.mapper = mapper;
    }

    @GetMapping("/getChinaData")
    @ApiOperation("获取中国数据")
    public List<AllData> getChinaData(){
        return mapper.getChinaData();
    }
}
