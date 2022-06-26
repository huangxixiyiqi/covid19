package com.cov19.controller;

import com.cov19.entity.Vaccine;
import com.cov19.entity.vo.SeaTendencyVo;
import com.cov19.entity.vo.TendencyVo;
import com.cov19.entity.vo.VaccineVo;
import com.cov19.mapper.ChinaMapper;
import com.cov19.mapper.SeaMapper;
import com.cov19.mapper.VaccineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class VaccineController {

    @Autowired
    private VaccineMapper mapper;

    @Autowired
    private ChinaMapper chinaMapper;


    @Autowired
    private SeaMapper seaMapper;

    @GetMapping("/VaccineAll")
    List<Vaccine> getAll() {
        return mapper.getAll();
    }


    @CrossOrigin
    @GetMapping("/getAllCountry")
    List<String> getAllCountry() {
        return mapper.getAllCountry();
    }

    @CrossOrigin
    @GetMapping("/getAllByLocation/{location}")
    List<Vaccine> getAllByLocation(@PathVariable String location) {
        return mapper.getAllByLocation(location);
    }


    @CrossOrigin
    @GetMapping("/getVaccineVo/{location}")
    List<VaccineVo> getVaccineVo(@PathVariable String location) {
        List<Vaccine> allVaccineList = mapper.getAllByLocation(location);

        List<VaccineVo> list = new ArrayList<>();

        if ("China".equals(location)) {
            ChinaController chinaController = new ChinaController(chinaMapper);
            List<TendencyVo> tendencyVoList = chinaController.getTendencyVo("中国");


            for (int i = 0; i < allVaccineList.size(); i++) {
                Vaccine vaccine = allVaccineList.get(i);
                VaccineVo vaccineVo = new VaccineVo();

                String date = vaccine.getDate();

                for (int j = 0; j < tendencyVoList.size(); j++) {
                    TendencyVo tendencyVo = tendencyVoList.get(j);
                    if (tendencyVo.getUpdateTime().equals(date)) {
                        vaccineVo.setNewConfirm(tendencyVo.getNewConfirm());
                        list.add(vaccineVo);
                        break;
                    }
                }
                vaccineVo.setDate(date);
                vaccineVo.setId(vaccine.getId());
                vaccineVo.setLocation(vaccine.getLocation());
                vaccineVo.setVaccine(vaccine.getVaccine());
                vaccineVo.setTotalVaccinations(vaccine.getTotalVaccinations());
                vaccineVo.setPeopleVaccinated(vaccine.getPeopleVaccinated());
                vaccineVo.setFullyVaccinated(vaccine.getFullyVaccinated());
            }

        } else {
            SeaController seaController = new SeaController(seaMapper);
            List<SeaTendencyVo> seaTendencyVoList = seaController.getSeaTendencyVoByEn(location);

            for (int i = 0; i < allVaccineList.size(); i++) {
                Vaccine vaccine = allVaccineList.get(i);
                VaccineVo vaccineVo = new VaccineVo();

                String date = vaccine.getDate();

                for (int j = 0; j < seaTendencyVoList.size(); j++) {
                    SeaTendencyVo seaTendencyVo = seaTendencyVoList.get(j);
                    if (seaTendencyVo.getUpdateTime().equals(date)) {
                        vaccineVo.setNewConfirm(seaTendencyVo.getNewConfirm());
                        list.add(vaccineVo);
                        break;
                    }
                }
                vaccineVo.setDate(date);
                vaccineVo.setId(vaccine.getId());
                vaccineVo.setLocation(vaccine.getLocation());
                vaccineVo.setVaccine(vaccine.getVaccine());
                vaccineVo.setTotalVaccinations(vaccine.getTotalVaccinations());
                vaccineVo.setPeopleVaccinated(vaccine.getPeopleVaccinated());
                vaccineVo.setFullyVaccinated(vaccine.getFullyVaccinated());
            }
        }
        return list;
    }


}
