package com.zju.edu.gcs.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity(name = "patient")
@Data
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private Date createTime;
}
