package com.zju.edu.gcs.handler;

import com.zju.edu.gcs.common.enums.StatisticType;
import com.zju.edu.gcs.dto.StatisticDTO;
import com.zju.edu.gcs.repository.AnalysisRepository;
import com.zju.edu.gcs.vo.StatisticResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AnalysisNumberHandler  extends AbstractStatisticHandler<StatisticDTO>{
    private List<String> xAxisData = Arrays.asList("电子病历对比分析", "病种识别结果分析", "治疗效果评估");

    @Autowired
    private AnalysisRepository analysisRepository;

    @Override
    public StatisticResultVO calculate(StatisticDTO statisticDTO) {
        StatisticResultVO statisticResultVO = new StatisticResultVO();
        List<Integer> yAxisData = new ArrayList<>();
        for(String each:xAxisData){
            yAxisData.add(analysisRepository.findAnalysisCount(each));
        }
        statisticResultVO.setXAxisData(xAxisData);
        statisticResultVO.setYAxisData(yAxisData);
        return statisticResultVO;
    }

    @Override
    public StatisticType getStatisticType() {
        return StatisticType.USAGE_COUNT;
    }
}
