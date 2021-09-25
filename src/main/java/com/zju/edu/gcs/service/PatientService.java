package com.zju.edu.gcs.service;

import com.zju.edu.gcs.common.exception.NirException;
import com.zju.edu.gcs.common.exception.NirExceptionEnum;
import com.zju.edu.gcs.dto.PatientDTO;
import com.zju.edu.gcs.dto.QueryPatientDTO;
import com.zju.edu.gcs.model.Patient;
import com.zju.edu.gcs.repository.PatientRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
