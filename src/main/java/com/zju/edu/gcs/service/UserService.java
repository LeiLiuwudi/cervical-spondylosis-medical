package com.zju.edu.gcs.service;

import com.zju.edu.gcs.common.entity.TokenStatus;
import com.zju.edu.gcs.common.exception.NirException;
import com.zju.edu.gcs.common.exception.NirExceptionEnum;
import com.zju.edu.gcs.common.util.JwtUtil;
import com.zju.edu.gcs.common.util.MD5Util;
import com.zju.edu.gcs.dto.LoginDTO;
import com.zju.edu.gcs.dto.RegisterDTO;
import com.zju.edu.gcs.dto.TokenDTO;
import com.zju.edu.gcs.model.User;
import com.zju.edu.gcs.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public TokenStatus getToken(LoginDTO loginDTO){
        String username = loginDTO.getUsername();
        String password = MD5Util.md5Encode(loginDTO.getPassword());
        User user = userRepository.findByUsernameAndAndPassword(username, password);
        if(user == null){
            throw new NirException(NirExceptionEnum.NO_USER_FOUND);
        }
        TokenStatus tokenStatus = JwtUtil.createJwt(user.getId().toString(), user.getUsername());
        return tokenStatus;
    }

    public void addUser(RegisterDTO registerDTO){
        User user = new User();
        List<User> list = userRepository.findAllByUsername(registerDTO.getUsername());
        if(CollectionUtils.isEmpty(list)){
            registerDTO.setPassword(MD5Util.md5Encode(registerDTO.getPassword()));
            BeanUtils.copyProperties(registerDTO,user);
            user.setCreateTime(new Date());
            userRepository.saveAndFlush(user);
        }else{
            throw new NirException(NirExceptionEnum.USER_EXIST);
        }

    }

    public User getUser(TokenDTO tokenDTO) {
        String username = JwtUtil.parseJwt(tokenDTO.getToken()).getSubject();
        return userRepository.findByUsername(username);
    }
}
