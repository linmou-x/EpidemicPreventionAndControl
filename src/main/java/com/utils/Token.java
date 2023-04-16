package com.utils;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Charles
 */
@Service
public class Token {
    String key="ASDFGHJKL";

    public String getToken(String phone,String password,Long id,String role,String name,String address) {
        String token=null;
        DateTime now =DateTime.now();
        DateTime newTime= now.offsetNew(DateField.MINUTE,100);
        Map<String,Object> payload = new HashMap<String,Object>();
        /**
         * 签发时间
         */
        payload.put(JWTPayload.ISSUED_AT, now);
        /**
         * 过期时间
         */
        payload.put(JWTPayload.EXPIRES_AT, newTime);
        /**
         * 生效时间
         */
        payload.put(JWTPayload.NOT_BEFORE, now);
        payload.put("phone",phone);
        payload.put("password",password);
        payload.put("id",id);
        payload.put("address",address);
        payload.put("role",role);
        payload.put("name",name);
        String key="ASDFGHJKL";
        token= JWTUtil.createToken(payload,key.getBytes());
        return token;
    }

    public boolean verifyToken(String token){

        JWT jwt = JWTUtil.parseToken(token);
        boolean verifyKey = jwt.setKey(key.getBytes()).verify();
        boolean verifyTime = jwt.validate(0);
        if (verifyKey||verifyTime){
            return true;
        }else{
            return false;
        }
    }

    public Long getId(String token){
        JWT jwt = JWTUtil.parseToken(token);
        JSONObject payloads = jwt.getPayloads();
        return  Long.valueOf(payloads.get("id").toString());
    }

    public String getRole(String token){
        JWT jwt = JWTUtil.parseToken(token);
        JSONObject payloads = jwt.getPayloads();
        return  payloads.get("role").toString();
    }

    public String getName(String token){
        JWT jwt = JWTUtil.parseToken(token);
        JSONObject payloads = jwt.getPayloads();
        return  payloads.get("name").toString();
    }


    public String getAddress(String token){
        JWT jwt = JWTUtil.parseToken(token);
        JSONObject payloads = jwt.getPayloads();
        return  payloads.get("address").toString();
    }

    public String getPhone(String token){
        JWT jwt = JWTUtil.parseToken(token);
        JSONObject payloads = jwt.getPayloads();
        return  payloads.get("phone").toString();
    }
}
