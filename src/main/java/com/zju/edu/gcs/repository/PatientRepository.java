package com.zju.edu.gcs.repository;

import com.zju.edu.gcs.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface PatientRepository extends JpaRepository<Patient, Integer> {

}
