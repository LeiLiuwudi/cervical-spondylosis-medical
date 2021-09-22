package com.zju.edu.gcs.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PatientDTO {
    private String name;
    private Integer doctorId;
    private Integer gender;
    private Date birthday;
    private String profession;
    private String chiefComplaint;
    private String presentHistory;
    private String PastHistory;
    private String initialDiagnosis;
    private String diagnoseBasis;
}
