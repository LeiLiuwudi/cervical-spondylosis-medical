package com.zju.edu.gcs.dto;

import com.zju.edu.gcs.model.User;
import lombok.Data;

@Data
public class RegisterDTO {
    private String username;
    private String password;
    private String trueName;

    public static RegisterDTO fromEntity(User user) {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername(user.getUsername());
        registerDTO.setPassword(user.getPassword());
        registerDTO.setTrueName(user.getTrueName());
        return registerDTO;
    }
}
