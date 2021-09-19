package com.zju.edu.gcs.controller;

import com.zju.edu.gcs.common.entity.TokenStatus;
import com.zju.edu.gcs.common.exception.NirException;
import com.zju.edu.gcs.common.exception.NirExceptionEnum;
import com.zju.edu.gcs.common.result.Result;
import com.zju.edu.gcs.common.result.ResultEnum;
import com.zju.edu.gcs.dto.LoginDTO;
import com.zju.edu.gcs.dto.RegisterDTO;
import com.zju.edu.gcs.dto.TokenDTO;
import com.zju.edu.gcs.model.User;
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
    public Result<TokenStatus> login(@RequestBody LoginDTO loginDTO){
        Result<TokenStatus> result = new Result<>();
        TokenStatus tokenStatus;
        try{
            tokenStatus = userService.getToken(loginDTO);
        }catch (NirException e){
            result.setCode(e.getCode());
            result.setMsg(e.getMessage());
            return result;
        }catch (Exception e){
            result.setCode(NirExceptionEnum.GENERAL_EXCEPTION.getCode());
            result.setMsg(e.getMessage());
            return result;

        }
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(tokenStatus);
        return result;
    }

    @PostMapping("/register")
    public Result<Void> register(@RequestBody RegisterDTO registerDTO){
        Result<Void> result = new Result<>();
        try{
            userService.addUser(registerDTO);
        }catch (NirException e){
            result.setCode(e.getCode());
            result.setMsg(e.getMessage());
            return result;
        }catch (Exception e){
            result.setCode(NirExceptionEnum.GENERAL_EXCEPTION.getCode());
            result.setMsg(e.getMessage());
            return result;
        }

        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        return result;
    }

    @PostMapping("/getUser")
    public Result<User> getUser(@RequestBody TokenDTO tokenDTO){
        Result<User> result = new Result<>();
        User user = new User();
        try{
            user = userService.getUser(tokenDTO);
        }catch (Exception e){
            result.setCode(NirExceptionEnum.GENERAL_EXCEPTION.getCode());
            result.setMsg(e.getMessage());
            return result;
        }
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(user);
        return result;
    }
}
