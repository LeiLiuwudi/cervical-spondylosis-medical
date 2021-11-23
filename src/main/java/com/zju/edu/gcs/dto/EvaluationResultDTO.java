package com.zju.edu.gcs.dto;

import lombok.Data;

import java.util.List;

/**
 * Created on 2021/11/23
 *
 * @author Patric Tian
 */
@Data
public class EvaluationResultDTO {
    private List<Double> result;

    private Integer count;

    private Integer trend;
}
