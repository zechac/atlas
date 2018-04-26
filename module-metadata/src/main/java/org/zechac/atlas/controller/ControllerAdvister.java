package org.zechac.atlas.controller;

import org.zechac.atlas.common.web.JsonResponse;
import org.zechac.atlas.common.web.JsonResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvister {

    @ExceptionHandler
    public JsonResponse ex(Exception ex) {
        return JsonResponse.fail().message("出错了");
    }
}
