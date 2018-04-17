package com.hongdee.atlas.controller;

import com.hongdee.atlas.common.web.JsonResponse;
import com.hongdee.atlas.common.web.RequestParamUtils;
import com.hongdee.atlas.entity.dynamic.DynamicTable;
import com.hongdee.atlas.service.DynamicTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
public class DynamicTableController {

    @Autowired
    private DynamicTableService dynamicTableService;

    @RequestMapping("table/save")
    public JsonResponse save (DynamicTable dynamicTable){

        dynamicTableService.save(dynamicTable);
        return JsonResponse.success().data(dynamicTable);
    }

    @RequestMapping("table/get{id}")
    public JsonResponse get (@PathVariable String id){

        DynamicTable dynamicTable = dynamicTableService.findById(id);

        if(dynamicTable!=null){
            return JsonResponse.success().data(dynamicTable);
        }else{
            return JsonResponse.fail().data(dynamicTable);
        }
    }

    @RequestMapping("table/list")
    public JsonResponse list (Pageable pageable, @RequestParam Map param){
        Map query = RequestParamUtils.mapRequestParam(param,"s_");
        Page<DynamicTable>dynamicTables = dynamicTableService.queryByPage(query,pageable);
        return JsonResponse.success().data(dynamicTables);
    }

}
