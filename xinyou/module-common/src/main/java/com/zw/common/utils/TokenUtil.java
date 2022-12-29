package com.zw.common.utils;

import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtil {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expire}")
    private Long expire;

    @Value("${jwt.header}")
    private String header;

    //根据openid 和 session_key 生成Token
    public String getToken(String openid, String session_key) {
        Date nowDate = new Date();
        //过期时间
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);
        Map<String, Object> maps = new HashMap<>();
        maps.put("openid", openid);
        maps.put("session_key", session_key);
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(JSONObject.toJSONString(maps))
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /*获取Token中注册的信息*/
    public Claims getTokenClaim(String token) {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
        return null;
        }
    }
}
