package com.zju.edu.gcs.service;

import com.zju.edu.gcs.model.Doctor;
import com.zju.edu.gcs.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public List<Doctor> getDoctorList() {
        return doctorRepository.findAll();
    }
}
