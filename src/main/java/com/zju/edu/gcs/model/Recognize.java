package com.zju.edu.gcs.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity(name = "recognize")
@Data
public class Recognize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer patientId;
    private String patientName;
    private String infraredPath;
    private String recognizeResult;
    private Date createTime;
}
