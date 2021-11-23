package com.zju.edu.gcs.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created on 2021/11/23
 *
 * @author Patric Tian
 */
@Getter
@AllArgsConstructor
public enum AnalysisResultEnum {
    GOOD_RECOVERY(0, "结合患者主诉和临床表现，患者病情恢复良好，后期坚持康复训练，效果更佳"),
    FURTHER_OBSERVATION(1, "结合患者主诉和临床表现，考虑颈椎退行性病变可能性，建议住院观察，以期进一步的判断"),
    NOT_CONTROLLED(2, "结合患者主诉和临床表现，患者病情未得到明显控制，建议讨论决定新的治疗方案");

    private Integer trend;
    private String desc;

    public static AnalysisResultEnum getDescByTrend(Integer trend) {
        for (AnalysisResultEnum type : AnalysisResultEnum.values()) {
            if (type.getTrend().equals(trend)) {
                return type;
            }
        }
        return null;
    }
}
