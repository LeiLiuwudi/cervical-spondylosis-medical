package com.zju.edu.gcs.common.util;

import com.zju.edu.gcs.common.entity.TokenStatus;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {
    public static final String KEY = "userLogin";
    public static final Long FAILURE_TIME = 3600000L;

    public static TokenStatus createJwt(String id, String username){
        TokenStatus tokenStatus = new TokenStatus();
        Long now = System.currentTimeMillis();
        Long expire = now + FAILURE_TIME;
        Date date = new Date(expire);
        JwtBuilder jwtBuilder = Jwts.builder().setId(id).setSubject(username).setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256,KEY);
        jwtBuilder.setExpiration(date);
        String token = jwtBuilder.compact();
        tokenStatus.setToken(token);
        tokenStatus.setExpireTime(date);
        return tokenStatus;
    }

    public static Claims parseJwt(String token){
        Claims claims = Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody();
        return claims;
    }
}
