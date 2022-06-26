package com.cov19.controller;

import com.cov19.entity.China;
import com.cov19.entity.Sea;
import com.cov19.entity.vo.NewAndCur;
import com.cov19.entity.vo.SeaNewAndCur;
import com.cov19.entity.vo.SeaTendencyVo;
import com.cov19.entity.vo.TendencyVo;
import com.cov19.mapper.SeaMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/Sea")
public class SeaController {

    private SeaMapper mapper;

    SeaController(SeaMapper mapper) {
        this.mapper = mapper;
    }

    @CrossOrigin //解决跨域问题
    @GetMapping("/getSea")
    @ApiOperation("获取全部海外数据")
    List<Sea> getSea() {
        return mapper.selectList(null);
    }

    @CrossOrigin
    @GetMapping("/getCountryName")
    @ApiOperation("获取全球国家名字")
    List<String> getCountryName() {
        return mapper.getCountryName();
    }

    @CrossOrigin
    @ApiOperation("获取指定国家的当天数据")
    @GetMapping("/getNowData/{country}")
    List<Sea> getNowData(@PathVariable String country) {
        return mapper.getNowData(country);
    }

    @CrossOrigin
    @GetMapping("/getNowAndYestData/{country}")
    @ApiOperation("获取海外昨天和今天的数据")
    List<Sea> getNowAndYestData(@PathVariable String country) {
        List<Sea> list = mapper.getNowData(country);
        Sea sea = list.get(0);
        String time = sea.getUpdateTime();
        String[] strings = time.split(" ");
        String[] strings1 = strings[0].split("-");

        int day = Integer.valueOf(strings1[2]) - 1;
        System.out.println(day);
        String yest = strings1[0] + "-" + strings1[1] + "-" + day + "%";
        System.out.println(yest);
        List<Sea> yestList = mapper.getNowAndYestData(yest, country);
        list.add(yestList.get(yestList.size() - 1));
        return list;
    }


    @CrossOrigin
    @GetMapping("/getNowAllSea")
    @ApiOperation("获取海外今天所有数据")
    List<Sea> getNowAllSea() {

        List<Sea> list = mapper.getNowAllSea();
        if (list.get(0) == null)
            return null;
        List<Sea> voList = new ArrayList<Sea>();
        voList.add(list.get(0));
        for (int i = 1; i < list.size(); i++) {
            Sea sea = list.get(i);
            boolean flag = true;
            for (int j = 0; j < voList.size(); j++) {
                if (voList.get(j).getCountryName().equals(sea.getCountryName())) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                voList.add(sea);
            }
        }
        System.out.println(voList.size());
        return voList;
    }


    @CrossOrigin
    @ApiOperation("获取所有海外国家昨日的数据")
    @GetMapping("/getYestAllSea")
    List<Sea> getYestAllSea() {
//        得到当前的日期
        List<Sea> list = mapper.getNowAllSea();
        Sea sea = list.get(0);
        String time = sea.getUpdateTime();
        String[] strings = time.split(" ");
        String[] strings1 = strings[0].split("-");

        int day = Integer.valueOf(strings1[2]) - 1;
        System.out.println(day);
        String yest = strings1[0] + "-" + strings1[1] + "-" + day + "%";
        System.out.println(yest);

//      得到去重后的数据
        List<Sea> yestList = mapper.getYestAllSea(yest);
        if (list.get(0) == null)
            return null;
        List<Sea> voList = new ArrayList<Sea>();
        voList.add(yestList.get(0));
        for (int i = 1; i < yestList.size(); i++) {
            sea = yestList.get(i);
            boolean flag = true;
            for (int j = 0; j < voList.size(); j++) {
                if (voList.get(j).getCountryName().equals(sea.getCountryName())) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                voList.add(sea);
            }
        }
        System.out.println(voList.size());
        return voList;
    }


    //    日期，地区，新增确诊，现有确诊
    @CrossOrigin
    @ApiOperation("获得所有地区新增和现有")
    @GetMapping("/getNewAndCurData")
    List<SeaNewAndCur> getNewAndCurData() {
        List<Sea> yestList = getYestAllSea();
        List<Sea> todayList = getNowAllSea();
        List<SeaNewAndCur> list = new ArrayList<>();

        for (int i = 0; i < yestList.size(); i++) {
            Sea yest = yestList.get(i);
            for (int j = 0; j < todayList.size(); j++) {
                Sea today = todayList.get(j);

                boolean flag = false;
                boolean flag2 = true;
                if (yest.getCountryName().equals(today.getCountryName())) {
                    for (int k = 0; k < list.size(); k++) {
                        SeaNewAndCur newAndCur = list.get(k);
                        if (newAndCur.getCountryName().equals(today.getCountryName())) {
                            flag2 = false;
                            break;
                        }
                    }
                    if (flag2) {
                        flag = true;
                    }
                }

                if (flag) {
                    SeaNewAndCur newAndCur = new SeaNewAndCur();
                    newAndCur.setUpdateTime(today.getUpdateTime());
                    newAndCur.setCountryName(today.getCountryName());
                    newAndCur.setNewConfirm(today.getConfirmedCount() - yest.getConfirmedCount());
                    newAndCur.setCurrentConfirm(today.getConfirmedCount() - today.getCuredCount() - today.getDeadCount());
                    list.add(newAndCur);
                }
            }
        }
        Collections.sort(list);
        System.out.println(list.size());
        return list;
    }


    @CrossOrigin
    @ApiOperation("获取新增，疑似，治愈，死亡，现有，日期(年月日)，省份")
    @GetMapping("/getSeaTendencyVo/{country}")
    List<SeaTendencyVo> getSeaTendencyVo(@PathVariable String country) {

        int[] d = new int[]{0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int m, day;

        List<Sea> list = mapper.getSeaDataFromCountry(country);

        List<SeaTendencyVo> tendencyVoList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            SeaTendencyVo tendencyVo = new SeaTendencyVo();
            Sea sea = list.get(i);
            tendencyVo.setCuredCount(sea.getCuredCount());
            tendencyVo.setDeadCount(sea.getDeadCount());
            tendencyVo.setCurrentConfirm(sea.getConfirmedCount() - sea.getDeadCount() - sea.getCuredCount());
            tendencyVo.setSuspectedCount(sea.getSuspectedCount());
            tendencyVo.setUpdateTime(sea.getUpdateTime().split(" ")[0]);
            //获取昨天的数据
            String time = sea.getUpdateTime();
            String[] strings = time.split(" ");
            String[] strings1 = strings[0].split("-");

            day = Integer.parseInt(strings1[2]) - 1;
            m = Integer.parseInt(strings1[1]);
            if (day == 0) {
                m -= 1;
                strings1[1] = String.valueOf(m);
                day = d[m];
            }
            if (strings1[1].length() < 2) {
                strings1[1] = "0" + strings1[1];
            }
            String yest = "";
            if (day < 10) {
                yest = strings1[0] + "-" + strings1[1] + "-0" + day + "%";
            } else {
                yest = strings1[0] + "-" + strings1[1] + "-" + day + "%";
            }
            System.out.println(yest);
            boolean flag = true;
            for (int j = 0; j < tendencyVoList.size(); j++) {
                SeaTendencyVo tendencyVo1 = tendencyVoList.get(j);
                if (tendencyVo.getUpdateTime().equals(tendencyVo1.getUpdateTime())) {
                    flag = false;
                    break;
                }
            }
            if (flag) {

                List<Sea> yestList = mapper.getNowAndYestData(yest, country);
                if (yestList.size() > 0) {
                    Sea yestSea = yestList.get(0);
                    System.out.println(sea.getConfirmedCount());
                    System.out.println(yestSea.getConfirmedCount());
                    if (m == 0 && day == 0) {
                        tendencyVo.setNewConfirm(0L);
                    }
                    tendencyVo.setNewConfirm(sea.getConfirmedCount() - yestSea.getConfirmedCount());
                } else {
                    tendencyVo.setNewConfirm(0L);
                }

                tendencyVo.setCountryName(sea.getCountryName());
                if (flag) {
                    tendencyVoList.add(tendencyVo);
                }
            }
        }

        Collections.sort(tendencyVoList);
        System.out.println(tendencyVoList.size());
        return tendencyVoList;
    }

    @CrossOrigin
    @GetMapping("/getSeaTendencyVoByEn/{country}")
    List<SeaTendencyVo> getSeaTendencyVoByEn(@PathVariable String country) {
        int[] d = new int[]{0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int m, day;

        List<Sea> list = mapper.getSeaDataFromEnCountry(country);

        List<SeaTendencyVo> tendencyVoList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            SeaTendencyVo tendencyVo = new SeaTendencyVo();
            Sea sea = list.get(i);
            tendencyVo.setCuredCount(sea.getCuredCount());
            tendencyVo.setDeadCount(sea.getDeadCount());
            tendencyVo.setCurrentConfirm(sea.getConfirmedCount() - sea.getDeadCount() - sea.getCuredCount());
            tendencyVo.setSuspectedCount(sea.getSuspectedCount());
            tendencyVo.setUpdateTime(sea.getUpdateTime().split(" ")[0]);
            //获取昨天的数据
            String time = sea.getUpdateTime();
            String[] strings = time.split(" ");
            String[] strings1 = strings[0].split("-");

            day = Integer.parseInt(strings1[2]) - 1;
            m = Integer.parseInt(strings1[1]);
            if (day == 0) {
                m -= 1;
                strings1[1] = String.valueOf(m);
                day = d[m];
            }
            if (strings1[1].length() < 2) {
                strings1[1] = "0" + strings1[1];
            }
            String yest = "";
            if (day < 10) {
                yest = strings1[0] + "-" + strings1[1] + "-0" + day + "%";
            } else {
                yest = strings1[0] + "-" + strings1[1] + "-" + day + "%";
            }
            System.out.println(yest);
            boolean flag = true;
            for (int j = 0; j < tendencyVoList.size(); j++) {
                SeaTendencyVo tendencyVo1 = tendencyVoList.get(j);
                if (tendencyVo.getUpdateTime().equals(tendencyVo1.getUpdateTime())) {
                    flag = false;
                    break;
                }
            }
            if (flag) {

                List<Sea> yestList = mapper.getNowAndYestDataByEn(yest, country);
                if (yestList.size() > 0) {
                    Sea yestSea = yestList.get(0);
                    System.out.println(sea.getConfirmedCount());
                    System.out.println(yestSea.getConfirmedCount());
                    if (m == 0 && day == 0) {
                        tendencyVo.setNewConfirm(0L);
                    }
                    tendencyVo.setNewConfirm(sea.getConfirmedCount() - yestSea.getConfirmedCount());
                } else {
                    tendencyVo.setNewConfirm(0L);
                }

                tendencyVo.setCountryName(sea.getCountryName());
                if (flag) {
                    tendencyVoList.add(tendencyVo);
                }
            }
        }

        Collections.sort(tendencyVoList);
        System.out.println(tendencyVoList.size());
        return tendencyVoList;
    }
}
