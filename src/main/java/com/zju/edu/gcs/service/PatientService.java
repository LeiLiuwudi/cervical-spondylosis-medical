package com.zju.edu.gcs.service;

import com.zju.edu.gcs.common.exception.NirException;
import com.zju.edu.gcs.common.exception.NirExceptionEnum;
import com.zju.edu.gcs.common.util.TextComparison;
import com.zju.edu.gcs.dto.PatientDTO;
import com.zju.edu.gcs.dto.QueryPatientDTO;
import com.zju.edu.gcs.dto.TextComparisonDTO;
import com.zju.edu.gcs.model.Patient;
import com.zju.edu.gcs.repository.PatientRepository;
import com.zju.edu.gcs.vo.ComparisonVO;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public void addPatient(PatientDTO patientDTO){
        Patient patient = new Patient();
        BeanUtils.copyProperties(patientDTO, patient);
        patient.setCreateTime(new Date());
        patientRepository.saveAndFlush(patient);
    }

    public List<Patient> queryPatient(QueryPatientDTO queryPatientDTO) {
        String name = queryPatientDTO.getName();
        Integer doctorId = queryPatientDTO.getDoctorId();
        String initialDiagnosis = queryPatientDTO.getInitialDiagnosis();
        return patientRepository.findByQuery(name, doctorId, initialDiagnosis);
    }

    public void updatePatient(PatientDTO patientDTO) {
        if(patientDTO.getId() == null){
            throw new NirException(NirExceptionEnum.PARAMETER_ERROR);
        }else{
            Patient patient = new Patient();
            BeanUtils.copyProperties(patientDTO, patient);
            patient.setCreateTime(new Date());
            patientRepository.saveAndFlush(patient);
        }
    }

    public void deletePatient(Integer id) {
        if(id == null){
            throw new NirException(NirExceptionEnum.PARAMETER_ERROR);
        }else{
            patientRepository.deleteById(id);
        }
    }

    public List<Patient> textComparison(TextComparisonDTO textComparisonDTO) {
        Patient basePatient = patientRepository.findById(textComparisonDTO.getId()).get();
        Patient comparePatient = patientRepository.findById(textComparisonDTO.getSimilarId()).get();
//        主诉字段颜色渲染
        TextComparison textComparison = new TextComparison(basePatient.getChiefComplaint(), comparePatient.getChiefComplaint());
        textComparison.runAnalysis();
        textComparison.traceback();
        ComparisonVO comparisonVO = getColorString(textComparison.getString1(), textComparison.getString2());
        basePatient.setChiefComplaint(comparisonVO.getBase());
        comparePatient.setChiefComplaint(comparisonVO.getCompare());
//        现病史字段颜色渲染
        textComparison = new TextComparison(basePatient.getPresentHistory(), comparePatient.getPresentHistory());
        textComparison.runAnalysis();
        textComparison.traceback();
        comparisonVO = getColorString(textComparison.getString1(), textComparison.getString2());
        basePatient.setPresentHistory(comparisonVO.getBase());
        comparePatient.setPresentHistory(comparisonVO.getCompare());
//        既往史字段颜色渲染
        textComparison = new TextComparison(basePatient.getPastHistory(), comparePatient.getPastHistory());
        textComparison.runAnalysis();
        textComparison.traceback();
        comparisonVO = getColorString(textComparison.getString1(), textComparison.getString2());
        basePatient.setPastHistory(comparisonVO.getBase());
        comparePatient.setPastHistory(comparisonVO.getCompare());
        return Arrays.asList(basePatient,comparePatient);
    }

    public ComparisonVO getColorString(String str1, String str2){
        char[] s = str1.toCharArray();
        char[] p = str2.toCharArray();
        int length = str1.length();
        String base = "";
        String compare = "";
        for(int i=0;i<length;i++){
            if(s[i]=='-'){
                compare += p[i];
            }else if(p[i]=='-'){
                base += s[i];
            }else if(s[i] == p[i]){
                base += "<span style='color:red'>" + s[i] + "</span>";
                compare += "<span style='color:red'>" + p[i] + "</span>";

            }else if(s[i] != p[i]){
                base += s[i];
                compare += p[i];
            }
        }
        ComparisonVO comparisonVO = new ComparisonVO();
        comparisonVO.setBase(base);
        comparisonVO.setCompare(compare);
        return comparisonVO;
    }
}
