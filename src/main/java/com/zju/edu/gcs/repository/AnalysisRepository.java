package com.zju.edu.gcs.repository;

import com.zju.edu.gcs.model.Analysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

@Component
public interface AnalysisRepository extends JpaRepository<Analysis, Integer> {
    @Query(value = "SELECT count(id) FROM analysis where function=?1", nativeQuery = true)
    Integer findAnalysisCount(String function);

    Analysis findAnalysisById(Integer id);
}
