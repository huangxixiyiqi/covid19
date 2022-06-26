package com.cov19.controller;

import com.cov19.entity.China;
import com.cov19.entity.vo.NewAndCur;
import com.cov19.entity.vo.TendencyVo;
import com.cov19.mapper.ChinaMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/China")
public class ChinaController {

    private ChinaMapper mapper;

    ChinaController(ChinaMapper mapper) {
        this.mapper = mapper;
    }

    @CrossOrigin //解决跨域问题
    @GetMapping("/getChina")
    @ApiOperation("获取中国数据")
    List<China> getAllData() {
        return mapper.selectList(null);
    }

    @CrossOrigin //解决跨域问题
    @GetMapping("/getProvinceName")
    @ApiOperation("获取所有国内的省份")
    List<String> getProvinceName() {
        return mapper.getProvinceName();
    }

    @CrossOrigin
    @GetMapping("/getNowData/{country}")
    @ApiOperation("获取当前数据")
    List<China> getNowData(@PathVariable String country) {
        return mapper.getNowData(country);
    }


    @CrossOrigin
    @GetMapping("/getNowAndYestData/{country}")
    @ApiOperation("获取昨天和今天的数据")
    List<China> getNowAndYestData(@PathVariable String country) {
        List<China> list = mapper.getNowData(country);
        China china = list.get(0);
        String time = china.getUpdateTime();
        String[] strings = time.split(" ");
        String[] strings1 = strings[0].split("-");

        int day = Integer.valueOf(strings1[2]) - 1;
        System.out.println(day);
        String yest = strings1[0] + "-" + strings1[1] + "-" + day + "%";
        System.out.println(yest);
        List<China> yestList = mapper.getNowAndYestData(yest, country);
        list.add(yestList.get(yestList.size() - 1));
        return list;
    }

    @CrossOrigin
    @GetMapping("/getNowAllChina")
    @ApiOperation("获取中国今天所有数据")
    List<China> getNowAllChina() {

        List<China> list = mapper.getNowAllChina();
        if (list.get(0) == null)
            return null;
        List<China> voList = new ArrayList<China>();
        voList.add(list.get(0));
        for (int i = 1; i < list.size(); i++) {
            China china = list.get(i);
            boolean flag = true;
            for (int j = 0; j < voList.size(); j++) {
                if (voList.get(j).getProvinceName().equals(china.getProvinceName())) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                voList.add(china);
            }
        }
        System.out.println(voList.size());
        return voList;
    }

    @CrossOrigin
    @ApiOperation("获取所有地区昨日的数据")
    @GetMapping("/getYestAllChina")
    List<China> getYestAllChina() {
//        得到当前的日期
        List<China> list = mapper.getNowData("中国");
        China china = list.get(0);
        String time = china.getUpdateTime();
        String[] strings = time.split(" ");
        String[] strings1 = strings[0].split("-");

        int day = Integer.valueOf(strings1[2]) - 1;
        System.out.println(day);
        String yest = strings1[0] + "-" + strings1[1] + "-" + day + "%";
        System.out.println(yest);

//      得到去重后的数据
        List<China> yestList = mapper.getYestAllChina(yest);
        if (list.get(0) == null)
            return null;
        List<China> voList = new ArrayList<China>();
        voList.add(yestList.get(0));
        for (int i = 1; i < yestList.size(); i++) {
            china = yestList.get(i);
            boolean flag = true;
            for (int j = 0; j < voList.size(); j++) {
                if (voList.get(j).getProvinceName().equals(china.getProvinceName())) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                voList.add(china);
            }
        }
        System.out.println(voList.size());
        return voList;
    }


    //    日期，地区，新增确诊，现有确诊
    @CrossOrigin
    @ApiOperation("获得所有地区新增和现有")
    @GetMapping("/getNewAndCurData")
    List<NewAndCur> getNewAndCurData() {
        List<China> yestList = getYestAllChina();
        List<China> todayList = getNowAllChina();
        List<NewAndCur> list = new ArrayList<>();

        for (int i = 0; i < yestList.size(); i++) {
            China yest = yestList.get(i);
            for (int j = 0; j < todayList.size(); j++) {
                China today = todayList.get(j);

                boolean flag = false;
                boolean flag2 = true;
                if (yest.getProvinceName().equals(today.getProvinceName())) {
                    for (int k = 0; k < list.size(); k++) {
                        NewAndCur newAndCur = list.get(k);
                        if (newAndCur.getProvinceName().equals(today.getProvinceName())) {
                            flag2 = false;
                            break;
                        }
                    }
                    if (flag2) {
                        flag = true;
                    }
                }

                if (flag) {
                    NewAndCur newAndCur = new NewAndCur();
                    newAndCur.setUpdateTime(today.getUpdateTime());
                    newAndCur.setProvinceName(today.getProvinceName());
                    newAndCur.setNewConfirm(today.getConfirmedCount() - yest.getConfirmedCount());
                    newAndCur.setCurrentConfirm(today.getConfirmedCount() - today.getCuredCount() - today.getDeadCount());
                    list.add(newAndCur);
                }
            }
        }
        System.out.println(list.size());
        Collections.sort(list);

        return list;
    }


//    新增，疑似，治愈，死亡，现有，日期(年月日)，省份

    @CrossOrigin
    @ApiOperation("获取指定地区2021年的数据")
    @GetMapping("/getChinaDataFromCountry/{country")
    List<China> getChinaDataFromCountry(@PathVariable String country){
        return mapper.getChinaDataFromCountry(country);
    }
    
    @CrossOrigin
    @ApiOperation("获取新增，疑似，治愈，死亡，现有，日期(年月日)，省份")
    @GetMapping("/getTendencyVo/{country}")
    List<TendencyVo> getTendencyVo(@PathVariable String country){

        int[] d = new int[]{0,31,28,31,30,31,30,31,31,30,31,30,31};
        int m, day;

        List<China> list = mapper.getChinaDataFromCountry(country);
        
        List<TendencyVo> tendencyVoList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            TendencyVo tendencyVo = new TendencyVo();
            China china = list.get(i);
            tendencyVo.setCuredCount(china.getCuredCount());
            tendencyVo.setDeadCount(china.getDeadCount());
            tendencyVo.setCurrentConfirm(china.getConfirmedCount()-china.getDeadCount()-china.getCuredCount());
            tendencyVo.setSuspectedCount(china.getSuspectedCount());
            tendencyVo.setUpdateTime(china.getUpdateTime().split(" ")[0]);
            //获取昨天的数据
            String time = china.getUpdateTime();
            String[] strings = time.split(" ");
            String[] strings1 = strings[0].split("-");

            day = Integer.parseInt(strings1[2]) - 1;
            m = Integer.parseInt(strings1[1]);
            if(day==0){
                m-=1;
                strings1[1] = String.valueOf(m);
                day = d[m];
            }
            if (strings1[1].length() < 2){
                strings1[1] = "0"+strings1[1];
            }
            String yest = "";
            if(day < 10){
                yest = strings1[0] + "-" + strings1[1] + "-0" + day + "%";
            }else {
                 yest = strings1[0] + "-" + strings1[1] + "-" + day + "%";
            }
            System.out.println(yest);
            boolean flag = true;
            for (int j = 0; j < tendencyVoList.size();j++){
                TendencyVo tendencyVo1 = tendencyVoList.get(j);
                if(tendencyVo.getUpdateTime().equals(tendencyVo1.getUpdateTime())){
                    flag = false;break;
                }
            }
            if (flag){

                List<China> yestList = mapper.getNowAndYestData(yest, country);
                if (yestList.size() > 0){
                    China yestChina = yestList.get(0);
                    System.out.println(china.getConfirmedCount());
                    System.out.println(yestChina.getConfirmedCount());
                    if(m ==0 && day==0){
                        tendencyVo.setNewConfirm(0L);
                    }
                    tendencyVo.setNewConfirm(china.getConfirmedCount()-yestChina.getConfirmedCount());
                }else {
                    tendencyVo.setNewConfirm(0L);
                }

                tendencyVo.setProvinceName(china.getProvinceName());
                if (flag){
                    tendencyVoList.add(tendencyVo);
                }
            }
        }

        Collections.sort(tendencyVoList);
        System.out.println(tendencyVoList.size());
        return tendencyVoList;
    }

}
