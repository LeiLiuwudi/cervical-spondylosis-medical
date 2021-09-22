package com.zju.edu.gcs.service;

import com.zju.edu.gcs.dto.PatientDTO;
import com.zju.edu.gcs.model.Patient;
import com.zju.edu.gcs.repository.PatientRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public void addPatient(PatientDTO patientDTO){
        Patient patient = new Patient();
        BeanUtils.copyProperties(patientDTO, patient);
        patientRepository.saveAndFlush(patient);
    }
}
