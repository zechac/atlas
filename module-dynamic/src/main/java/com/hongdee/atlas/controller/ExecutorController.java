package com.hongdee.atlas.controller;

import com.alibaba.fastjson.JSONObject;
import com.hongdee.atlas.common.web.JsonResponse;
import com.hongdee.atlas.service.ExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @param
 * @param
 * @param
 * @return
 * @throws
 */
@RestController
@RequestMapping("dynamic")
public class ExecutorController {

    @Autowired
    private ExecutorService executorService;

    @PostMapping("executor")
    public JsonResponse execPost(@RequestBody JSONObject data,String templateId){
        return executorService.exec(templateId,data);
    }

    @GetMapping("executor")
    public JsonResponse execGet(@RequestParam Map data,String templateId){
        return executorService.exec(templateId,data);
    }

}
