package com.zju.edu.gcs.repository;

import com.zju.edu.gcs.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

}
