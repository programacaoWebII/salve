package com.salve.biblioteca.functions;

import org.springframework.security.oauth2.jwt.Jwt;

public class Functions {
    static public String dbDataConvert(String data){
        data = data.substring(8,10)+"/"+data.substring(5,7)+"/"+data.substring(0,4)+" as"+data.substring(10, data.length());
        return data;
    }
    static public String getId(Jwt jwt){
        return jwt.getClaim("sub").toString();
    }
}
