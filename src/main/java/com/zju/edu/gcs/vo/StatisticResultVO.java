package com.zju.edu.gcs.vo;

import lombok.Data;

import java.util.List;

@Data
public class StatisticResultVO {
    private List<String> xAxisData;
    private List<Integer> yAxisData;
}
