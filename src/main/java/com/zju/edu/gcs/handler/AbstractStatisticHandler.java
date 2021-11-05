package com.zju.edu.gcs.handler;

import com.zju.edu.gcs.common.enums.StatisticType;
import com.zju.edu.gcs.dto.BaseDTO;
import com.zju.edu.gcs.vo.StatisticResultVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract  class AbstractStatisticHandler<T extends BaseDTO>{
    protected static final ThreadLocal<BaseDTO> THREAD_LOCAL_INPUT = new ThreadLocal<>();

    public StatisticResultVO execute(T t){
        init(t);
        StatisticResultVO result = calculate(t);
        printLog(t,result);
        return result;
    }

    private void init(T t){
        THREAD_LOCAL_INPUT.set(t);
    }

    private void printLog(T t, StatisticResultVO result){
        log.info("查询类型为{}，得到的结果是{}", StatisticType.getOperationTypeByCode(t.getTypeCode()).getDescription(),result);
    }

    public abstract StatisticResultVO calculate(T t);

    public abstract StatisticType getStatisticType();

}
