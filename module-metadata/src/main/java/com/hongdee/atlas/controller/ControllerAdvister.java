package com.hongdee.atlas.controller;

import com.hongdee.atlas.common.web.JsonResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvister {

    @ExceptionHandler
    public JsonResponse ex(){
        return JsonResponse.fail().Message("出错了");
    }
}
