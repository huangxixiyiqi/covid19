package com.cov19.controller;

import com.cov19.entity.ChinaData;
import com.cov19.mapper.ChinaDataMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;

@RestController
public class ChinaDataController {

    private ChinaDataMapper mapper;

//    去除@AutoWired警告
    ChinaDataController(ChinaDataMapper mapper)
    {
        this.mapper = mapper;
    }

    @ApiOperation("查询所有数据")
    @GetMapping("/getAll")
    public List<ChinaData> getAll()
    {
        return mapper.selectList(null);
    }

    @ApiOperation("查询所有日期")
    @GetMapping("/getAllDate")
    public List<Date> getAllDate()
    {
        return mapper.selectAllDate();
    }

    @ApiOperation("通过XML查询所有日期")
    @GetMapping("/getAllDateXml")
    public List<Date> getAllDateXml()
    {
        return mapper.selectAllDateByXml();
    }
}
