package com.zju.edu.gcs.service;

import com.zju.edu.gcs.common.enums.StatisticType;
import com.zju.edu.gcs.dto.BaseDTO;
import com.zju.edu.gcs.handler.AbstractStatisticHandler;
import com.zju.edu.gcs.vo.StatisticResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class StatisticFactory {
    private static final Map<StatisticType, AbstractStatisticHandler> handlerMap = new HashMap<>();

    @Autowired
    public StatisticFactory(List<AbstractStatisticHandler> list){
        for(AbstractStatisticHandler handler : list){
            handlerMap.put(handler.getStatisticType(), handler);
        }
    }

    public StatisticResultVO execute(StatisticType type, BaseDTO baseDTO) throws Exception {
        AbstractStatisticHandler handler = handlerMap.get(type);
        if(handler == null){
            log.warn("找不到对应的handler, type code = {}", type.getCode());
            throw new Exception("操作类型错误");
        }
        StatisticResultVO result = handler.execute(baseDTO);
        return result;
    }
}
