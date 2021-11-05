package com.zju.edu.gcs.controller;

import com.zju.edu.gcs.common.enums.StatisticType;
import com.zju.edu.gcs.common.exception.NirExceptionEnum;
import com.zju.edu.gcs.common.result.Result;
import com.zju.edu.gcs.common.result.ResultEnum;
import com.zju.edu.gcs.dto.StatisticDTO;
import com.zju.edu.gcs.service.StatisticFactory;
import com.zju.edu.gcs.vo.StatisticResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/statistic")
public class StatisticController {

    @Autowired
    private StatisticFactory factory;

    @PostMapping("/statisticCount" )
    public Result<StatisticResultVO> statisticCount(@RequestBody StatisticDTO statisticDTO){
        Result<StatisticResultVO> result = new Result<>();
        StatisticResultVO statisticResultVO;
        try {
            statisticResultVO = factory.execute(StatisticType.getOperationTypeByCode(statisticDTO.getTypeCode()), statisticDTO);
        } catch (Exception e) {
            result.setCode(NirExceptionEnum.GENERAL_EXCEPTION.getCode());
            result.setMsg(e.getMessage());
            return result;
        }
        result.setData(statisticResultVO);
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        return result;
    }
}
