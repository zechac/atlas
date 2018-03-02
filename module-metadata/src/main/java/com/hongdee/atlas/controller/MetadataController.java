package com.hongdee.atlas.controller;

import com.hongdee.atlas.common.web.JsonResponse;
import com.hongdee.atlas.entity.MetadataGroup;
import com.hongdee.atlas.service.MetadataGroupService;
import com.hongdee.atlas.service.MetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("metadata")
public class MetadataController {

    @Autowired
    private MetadataService metadataService;

    @Autowired
    private MetadataGroupService metadataGroupService;

    /**
     * 获取元数据组
     * @param pageable
     * @return
     */
    @RequestMapping("groups")
    public JsonResponse index(Pageable pageable,Map<String,String> query){
       Page<MetadataGroup> metadataGroups= metadataGroupService.queryByPage(query,pageable);
       return JsonResponse.success().Data(metadataGroups);
    }
}
