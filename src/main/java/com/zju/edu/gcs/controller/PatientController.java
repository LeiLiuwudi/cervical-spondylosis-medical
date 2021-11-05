package com.zju.edu.gcs.controller;

import com.zju.edu.gcs.common.exception.NirExceptionEnum;
import com.zju.edu.gcs.common.result.Result;
import com.zju.edu.gcs.common.result.ResultEnum;
import com.zju.edu.gcs.dto.PatientDTO;
import com.zju.edu.gcs.dto.QueryPatientDTO;
import com.zju.edu.gcs.dto.TextComparisonDTO;
import com.zju.edu.gcs.model.Patient;
import com.zju.edu.gcs.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping("/addPatient")
    public Result<Void> addPatient(@RequestBody PatientDTO patientDTO){
        Result<Void> result = new Result<>();
        try{
            patientService.addPatient(patientDTO);
        }catch (Exception e){
            result.setCode(NirExceptionEnum.GENERAL_EXCEPTION.getCode());
            result.setMsg(e.getMessage());
            return result;
        }
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        return result;
    }

    @PostMapping("/queryPatient" )
    public Result<List<Patient>> queryPatient(@RequestBody QueryPatientDTO queryPatientDTO){
        Result<List<Patient>> result = new Result<>();
        List<Patient> list;
        try{
            list = patientService.queryPatient(queryPatientDTO);
        }catch (Exception e){
            result.setCode(NirExceptionEnum.GENERAL_EXCEPTION.getCode());
            result.setMsg(e.getMessage());
            return result;
        }
        result.setData(list);
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        return result;
    }

    @PostMapping("/updatePatient")
    public Result<Void> updatePatient(@RequestBody PatientDTO patientDTO){
        Result<Void> result = new Result<>();
        try{
            patientService.updatePatient(patientDTO);
        }catch (Exception e){
            result.setCode(NirExceptionEnum.GENERAL_EXCEPTION.getCode());
            result.setMsg(e.getMessage());
            return result;
        }
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        return result;
    }

    @GetMapping("/deletePatient")
    public Result<Void> deletePatient(@RequestParam Integer id){
        Result<Void> result = new Result<>();
        try{
            patientService.deletePatient(id);
        }catch (Exception e){
            result.setCode(NirExceptionEnum.GENERAL_EXCEPTION.getCode());
            result.setMsg(e.getMessage());
            return result;
        }
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        return result;
    }

    @PostMapping("/textComparison")
    public Result<List<Patient>> textComparison(@RequestBody TextComparisonDTO textComparisonDTO){
        Result<List<Patient>> result = new Result<>();
        List<Patient> list;
        try{
            list = patientService.textComparison(textComparisonDTO);
        }catch (Exception e){
            result.setCode(NirExceptionEnum.GENERAL_EXCEPTION.getCode());
            result.setMsg(e.getMessage());
            return result;
        }
        result.setData(list);
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        return result;
    }



}
