package com.zju.edu.gcs.service;

import com.zju.edu.gcs.common.util.MD5Util;
import com.zju.edu.gcs.dto.LoginDTO;
import com.zju.edu.gcs.dto.RegisterDTO;
import com.zju.edu.gcs.model.User;
import com.zju.edu.gcs.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Boolean getUser(LoginDTO loginDTO){
        String username = loginDTO.getUsername();
        String password = MD5Util.md5Encode(loginDTO.getPassword());
        List<User> list = userRepository.findAllByUsernameAndAndPassword(username, password);
        if(CollectionUtils.isEmpty(list)){
            return false;
        }
        return true;
    }

    public void addUser(RegisterDTO registerDTO){
        User user = new User();
        registerDTO.setPassword(MD5Util.md5Encode(registerDTO.getPassword()));
        BeanUtils.copyProperties(registerDTO,user);
        userRepository.saveAndFlush(user);
    }
}
