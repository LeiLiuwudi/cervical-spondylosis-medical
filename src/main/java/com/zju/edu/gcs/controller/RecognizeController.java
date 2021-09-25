package com.zju.edu.gcs.controller;

import com.zju.edu.gcs.common.exception.NirExceptionEnum;
import com.zju.edu.gcs.common.result.Result;
import com.zju.edu.gcs.common.result.ResultEnum;
import com.zju.edu.gcs.dto.RecognizeDTO;
import com.zju.edu.gcs.model.Recognize;
import com.zju.edu.gcs.service.RecognizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/recognize")
public class RecognizeController {

    @Autowired
    private RecognizeService recognizeService;

    @GetMapping("/getRecognizeList")
    private Result<List<Recognize>> getRecognizeList(@RequestParam Integer patientId){
        Result<List<Recognize>> result = new Result<>();
        List<Recognize> list;
        try{
            list = recognizeService.getRecognizeList(patientId);
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

    @PostMapping("/upload")
    public Result<Void> uploadImage(@RequestBody RecognizeDTO recognizeDTO){
        Result<Void> result = new Result<>();
        try{
            recognizeService.uploadImage(recognizeDTO);
        }catch (Exception e){
            result.setCode(NirExceptionEnum.GENERAL_EXCEPTION.getCode());
            result.setMsg(e.getMessage());
            return result;
        }
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        return result;
    }
}
