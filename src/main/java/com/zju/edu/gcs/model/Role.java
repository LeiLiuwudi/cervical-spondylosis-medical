package com.zju.edu.gcs.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created on 2021/9/24
 *
 * @author Patric Tian
 */
@Entity(name = "role")
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String chineseName;

    private String patientCreateAuthority;

    private String recordQueryAuthority;

    private String recordComparisonAuthority;

    private String identifyAuthorityAuthority;

    private String effectEvaluationAuthority;

    private String statAnalysisAuthority;

    private String authorityManagement;

    private String containUserId;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

}
