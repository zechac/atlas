package com.hongdee.atlas.controller;

import com.hongdee.atlas.common.web.JsonResponse;
import com.hongdee.atlas.common.web.RequestParamUtils;
import com.hongdee.atlas.entity.Metadata;
import com.hongdee.atlas.entity.MetadataGroup;
import com.hongdee.atlas.service.MetadataGroupService;
import com.hongdee.atlas.service.MetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
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
     * /metadata/group/list?page=0&size=10&s_name=2
     * @return
     */
    @RequestMapping("group/list")
    public JsonResponse groupList(Pageable pageable,@RequestParam Map param,String[] select){
       Map query= RequestParamUtils.mapRequestParam(param,"s_");
       Page<MetadataGroup> metadataGroups= metadataGroupService.queryByPage(query,pageable,select);
       return JsonResponse.success().Data(metadataGroups);
    }

    /**
     * 获取元数据
     * @param pageable
     * @return
     */
    @RequestMapping("list")
    public JsonResponse list(Pageable pageable,Map param){
        Map query= RequestParamUtils.mapRequestParam(param,"s_");
        Page<Metadata> metadataGroups= metadataService.queryByPage(query,pageable);
        return JsonResponse.success().Data(metadataGroups);
    }

    /**
     * 修改元数据
     * @return
     */
    @RequestMapping("mdy")
    public JsonResponse mdy(Metadata metadata){
        metadataService.save(metadata);
        return JsonResponse.success().Data(metadata);
    }

    /**
     * 修改元数据组
     * /metadata/group/mdy?name=2
     * @return
     */
    @RequestMapping("group/mdy")
    public JsonResponse groupMdy(MetadataGroup metadataGroup){
        metadataGroupService.save(metadataGroup);
        return JsonResponse.success().Data(metadataGroup);
    }

    /**
     * 删除元数据组
     * /metadata/group/delete/1
     * @return
     */
    @RequestMapping("group/delete/{id}")
    public JsonResponse groupDelete(@PathVariable String id){
        metadataGroupService.deleteById(id);
        return JsonResponse.success();
    }

    /**
     * 删除元数据
     * @return
     */
    @RequestMapping("delete/{id}")
    public JsonResponse delete(@PathVariable String id){
        metadataService.deleteById(id);
        return JsonResponse.success();
    }
}
