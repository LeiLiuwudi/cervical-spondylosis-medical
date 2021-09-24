package com.zju.edu.gcs.repository;

import com.zju.edu.gcs.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PatientRepository extends JpaRepository<Patient, Integer> {

    @Query(value = "select * from patient where if(?1 !='', name=?1,1=1) and if(?2 !='', doctor_id=?2,1=1) and if(?3 != '', initial_diagnosis=?3, 1=1)",nativeQuery = true)
    List<Patient> findByQuery(String name, Integer doctorId, String initialDiagnosis);
}
