package com.zju.edu.gcs.model;

import lombok.Data;

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

    private Date createTime;

}
