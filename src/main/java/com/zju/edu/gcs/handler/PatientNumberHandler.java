package com.zju.edu.gcs.handler;

import com.zju.edu.gcs.common.enums.StatisticType;
import com.zju.edu.gcs.dto.StatisticDTO;
import com.zju.edu.gcs.repository.PatientRepository;
import com.zju.edu.gcs.vo.CountNumVO;
import com.zju.edu.gcs.vo.StatisticResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PatientNumberHandler extends AbstractStatisticHandler<StatisticDTO>{

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public StatisticResultVO calculate(StatisticDTO statisticDTO) {
        StatisticResultVO statisticResultVO = new StatisticResultVO();
        if(statisticDTO == null || statisticDTO.getTime() == null || statisticDTO.getTime().equals("all") ){
            List<Object[]> list= patientRepository.findCountAll();
            List<Integer> yAxisData = list.stream().map(objects -> Integer.valueOf(objects[0].toString())).collect(Collectors.toList());
            List<String> xAxisData = list.stream().map(objects -> (String)objects[1]).collect(Collectors.toList());
            statisticResultVO.setXAxisData(xAxisData);
            statisticResultVO.setYAxisData(yAxisData);
        } else if(statisticDTO.getTime().equals("weekly")){
            List<String> xAxisData = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            xAxisData.add(sdf.format(calendar.getTime()));
            for(int i=0;i<6;i++){
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                xAxisData.add(sdf.format(calendar.getTime()));
            }
            Collections.reverse(xAxisData);
            statisticResultVO.setXAxisData(xAxisData);
            List<Object[]> list= patientRepository.findCountWeekly();
            Map<String, Integer> dateMap = new HashMap<>();
            for(Object[] each:list){
                dateMap.put(sdf.format(each[1]), Integer.valueOf(each[0].toString()));
            }
            List<Integer> yAxisData = xAxisData.stream().map(s -> {
                if(dateMap.containsKey(s)) return dateMap.get(s);
                return 0;
            }).collect(Collectors.toList());
            statisticResultVO.setYAxisData(yAxisData);
        }else if(statisticDTO.getTime().equals("monthly")){
            List<String> xAxisData = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            xAxisData.add(sdf.format(calendar.getTime()));
            for(int i=0;i<29;i++){
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                xAxisData.add(sdf.format(calendar.getTime()));
            }
            Collections.reverse(xAxisData);
            statisticResultVO.setXAxisData(xAxisData);
            List<Object[]> list= patientRepository.findCountMonthly();
            Map<String, Integer> dateMap = new HashMap<>();
            for(Object[] each:list){
                dateMap.put(sdf.format(each[1]), Integer.valueOf(each[0].toString()));
            }
            List<Integer> yAxisData = xAxisData.stream().map(s -> {
                if(dateMap.containsKey(s)) return dateMap.get(s);
                return 0;
            }).collect(Collectors.toList());
            statisticResultVO.setYAxisData(yAxisData);
        }else if(statisticDTO.getTime().equals("yearly")){
            List<String> xAxisData = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            Calendar calendar = Calendar.getInstance();
            xAxisData.add(sdf.format(calendar.getTime()));
            for(int i=0;i<11;i++){
                calendar.add(Calendar.MONTH, -1);
                xAxisData.add(sdf.format(calendar.getTime()));
            }
            Collections.reverse(xAxisData);
            statisticResultVO.setXAxisData(xAxisData);
            List<Object[]> list= patientRepository.findCountYearly();
            Map<String, Integer> dateMap = new HashMap<>();
            for(Object[] each:list){
                dateMap.put((String)each[1], Integer.valueOf(each[0].toString()));
            }
            List<Integer> yAxisData = xAxisData.stream().map(s -> {
                if(dateMap.containsKey(s)) return dateMap.get(s);
                return 0;
            }).collect(Collectors.toList());
            statisticResultVO.setYAxisData(yAxisData);
        }

        return statisticResultVO;
    }

    @Override
    public StatisticType getStatisticType() {
        return StatisticType.PATIENT_NUMBER;
    }
}
