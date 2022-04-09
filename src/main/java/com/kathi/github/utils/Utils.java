package com.kathi.github.utils;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Utils {

    private static final String ACCEPT_HEADER = "accept";
    private static final String ACCEPTS_APPLICATION_JSON = "application/json";

    public static String getAcceptsFromHeader(Map<String, String> headers){

        String accepts = ACCEPTS_APPLICATION_JSON;
        for(Map.Entry<String, String> e: headers.entrySet()){
            if(e.getKey() == ACCEPT_HEADER){
                accepts = e.getValue();
                return accepts;
            }
        }
        return accepts;
    }
}
