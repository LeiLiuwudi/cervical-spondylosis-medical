package com.zju.edu.gcs.common.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.Date;

@Data
public class TokenStatus {

    private String token;
    private Date expireTime;
}
