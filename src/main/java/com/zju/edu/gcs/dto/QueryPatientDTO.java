package com.zju.edu.gcs.dto;

import lombok.Data;

@Data
public class QueryPatientDTO {
    private String name;
    private Integer doctorId;
    private String initialDiagnosis;
}
