package com.zju.edu.gcs.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatisticType {
    PATIENT_NUMBER(1, "患者数量统计"),
    ILLNESS_NUMBER(2, "患病类别数量统计"),
    USAGE_COUNT(3, "AI智能分析模块使用次数统计");

    private Integer code;
    private String description;

    public static StatisticType getOperationTypeByCode(Integer code){
        for(StatisticType type : StatisticType.values()){
            if(type.getCode().equals(code)){
                return type;
            }
        }
        return null;
    }
}
