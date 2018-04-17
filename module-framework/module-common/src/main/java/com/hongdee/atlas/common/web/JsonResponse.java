package com.hongdee.atlas.common.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * web json 返回值封装
 */
public class JsonResponse extends ResponseEntity<Map<String,Object>> {

    public JsonResponse(){
        super(new HashMap<>(),HttpStatus.OK);
    }

    public JsonResponse(HttpStatus httpStatus){
        super(new HashMap<>(),httpStatus);
    }

    public static JsonResponse success(){
        JsonResponse jsonResponse=new JsonResponse();
        jsonResponse.getBody().put("success",true);
        return jsonResponse;
    }

    public static JsonResponse fail(){
        JsonResponse jsonResponse=new JsonResponse();
        jsonResponse.getBody().put("success",false);
        return jsonResponse;
    }

    /**
     * 带有http状态的失败返回
     * @param httpStatus
     * @return
     */
    public static JsonResponse fail(HttpStatus httpStatus){
        JsonResponse jsonResponse=new JsonResponse(httpStatus);
        jsonResponse.getBody().put("success",false);
        return jsonResponse;
    }

    public JsonResponse message(String message){
        this.getBody().put("message",message);
        return this;
    }

    public JsonResponse data(Object o){
        this.getBody().put("data",o);
        return this;
    }

    public JsonResponse put(String key,Object val){
        this.getBody().put(key,val);
        return this;
    }
}
