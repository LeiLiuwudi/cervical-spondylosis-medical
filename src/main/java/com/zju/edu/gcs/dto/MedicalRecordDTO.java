package com.zju.edu.gcs.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created on 2021/11/23
 *
 * @author Patric Tian
 */
@Data
public class MedicalRecordDTO {
    /*
    基本信息
     */
    //单据id
    private Integer id;
    //创建时间
    private Date createdAt;

    /*
    病人信息
     */
    //病人姓名
    private String patientName;
    //性别
    private Integer gender;
    //生日
    private Date birthday;
    //职业
    private String profession;
    //初步诊断
    private String initialDiagnosis;
    //诊断基础
    private String diagnoseBasis;
    //主诉
    private String chiefComplaint;
    //既往史
    private String pastHistory;
    //现病史
    private String presentHistory;

    /*
    医生信息
     */
    private String doctorName;

    /*
    智能分析结果
     */
    private EvaluationResultDTO evaluationResultDTO;

    //评估描述
    private String evaluationDesc;

}
