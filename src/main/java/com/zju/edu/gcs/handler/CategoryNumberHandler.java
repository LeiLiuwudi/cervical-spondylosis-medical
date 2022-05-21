package com.zju.edu.gcs.handler;

import com.zju.edu.gcs.common.enums.StatisticType;
import com.zju.edu.gcs.dto.StatisticDTO;
import com.zju.edu.gcs.repository.PatientRepository;
import com.zju.edu.gcs.vo.StatisticResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CategoryNumberHandler extends AbstractStatisticHandler<StatisticDTO>{
    private List<String> xAxisData = Arrays.asList("正常", "颈椎疲劳", "颈椎劳损", "颈椎间盘突出", "颈椎退行性病变");

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public StatisticResultVO calculate(StatisticDTO statisticDTO) {
        StatisticResultVO statisticResultVO = new StatisticResultVO();
        List<Integer> yAxisData = new ArrayList<>();
        for(String each:xAxisData){
            yAxisData.add(patientRepository.findCategoryCount(each));
        }
        statisticResultVO.setXAxisData(xAxisData);
        statisticResultVO.setYAxisData(yAxisData);
        return statisticResultVO;
    }

    @Override
    public StatisticType getStatisticType() {
        return StatisticType.ILLNESS_NUMBER;
    }
}
