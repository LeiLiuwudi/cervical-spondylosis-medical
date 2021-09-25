package com.zju.edu.gcs.repository;

import com.zju.edu.gcs.model.Recognize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RecognizeRepository extends JpaRepository<Recognize, Integer> {

    List<Recognize> findAllByPatientIdOrderByCreateTime(Integer patientId);
}
