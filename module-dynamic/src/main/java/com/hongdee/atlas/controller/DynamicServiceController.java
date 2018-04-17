package com.hongdee.atlas.controller;

import com.hongdee.atlas.common.web.JsonResponse;
import com.hongdee.atlas.common.web.RequestParamUtils;
import com.hongdee.atlas.entity.dynamic.DynamicServiceTemplate;
import com.hongdee.atlas.service.DynamicService;
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
public class DynamicServiceController {
    @Autowired
    private DynamicService dynamicService;

    @RequestMapping("service/{id}")
    public JsonResponse get(@PathVariable String id){
        DynamicServiceTemplate dynamic =dynamicService.findById(id);
        if(dynamic!=null) {
            return JsonResponse.success().data(dynamic);
        }else {
            return JsonResponse.fail().message("没有找到相关信息");
        }
    }

    @RequestMapping("service/save")
    public JsonResponse save(@RequestBody DynamicServiceTemplate dynamic){
        dynamicService.save(dynamic);
        return JsonResponse.success().data(dynamic);
    }

    @RequestMapping("service/list")
    public JsonResponse list(Pageable pageable, @RequestParam Map param, String[] select){

        Map query = RequestParamUtils.mapRequestParam(param,"s_");
        Page<DynamicServiceTemplate> dynamic = dynamicService.queryByPage(query,pageable,select);
        return JsonResponse.success().data(dynamic);
    }

}
