package com.zju.edu.gcs.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PatientDTO {
    private Integer id;
    private String name;
    private Integer doctorId;
    private String doctorName;
    private Integer gender;
    private Date birthday;
    private String profession;
    private String chiefComplaint;
    private String presentHistory;
    private String pastHistory;
    private String initialDiagnosis;
    private String diagnoseBasis;
}
