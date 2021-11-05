package com.zju.edu.gcs.repository;

import com.zju.edu.gcs.model.Patient;
import com.zju.edu.gcs.vo.CountNumVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PatientRepository extends JpaRepository<Patient, Integer> {

    @Query(value = "select * from patient where if(?1 !='', name=?1,1=1) and if(?2 !='', doctor_id=?2,1=1) and if(?3 != '', initial_diagnosis=?3, 1=1)",nativeQuery = true)
    List<Patient> findByQuery(String name, Integer doctorId, String initialDiagnosis);

    @Query(value = "SELECT count(id) as countNum,DATE(create_time) as dateTime FROM patient WHERE create_time > DATE_SUB(CURDATE(), INTERVAL 7 DAY) group by DATE(create_time) ORDER BY DATE(create_time) ", nativeQuery = true)
    List<Object[]> findCountWeekly();

    @Query(value = "SELECT count(id) as countNum,DATE(create_time) as dateTime FROM patient WHERE create_time > DATE_SUB(CURDATE(), INTERVAL 30 DAY) group by DATE(create_time) ORDER BY DATE(create_time) ", nativeQuery = true)
    List<Object[]> findCountMonthly();

    @Query(value = "SELECT count(id) as countNum,DATE_FORMAT(create_time, '%Y-%m') as dateTime FROM patient WHERE create_time > DATE_SUB(CURDATE(), INTERVAL 1 YEAR) group by DATE_FORMAT(create_time, '%Y-%m') ORDER BY DATE_FORMAT(create_time, '%Y-%m') ", nativeQuery = true)
    List<Object[]> findCountYearly();

    @Query(value = "SELECT count(id) as countNum,DATE_FORMAT(create_time, '%Y-%m') as dateTime FROM patient group by DATE_FORMAT(create_time, '%Y-%m') ORDER BY DATE_FORMAT(create_time, '%Y-%m')  ", nativeQuery = true)
    List<Object[]> findCountAll();

    @Query(value = "SELECT count(id) FROM patient where initial_diagnosis=?1", nativeQuery = true)
    Integer findCategoryCount(String diagnosis);
}
