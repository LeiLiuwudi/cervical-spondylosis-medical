package com.zju.edu.gcs.controller;

import com.zju.edu.gcs.common.result.Result;
import com.zju.edu.gcs.common.result.ResultEnum;
import com.zju.edu.gcs.dto.LoginDTO;
import com.zju.edu.gcs.dto.RegisterDTO;
import com.zju.edu.gcs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result<Boolean> login(@RequestBody LoginDTO loginDTO){
        Result<Boolean> result = new Result<>();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(userService.getUser(loginDTO));
        return result;
    }

    @PostMapping("/register")
    public Result<Void> register(@RequestBody RegisterDTO registerDTO){
        Result<Void> result = new Result<>();
        userService.addUser(registerDTO);
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        return result;
    }
}
