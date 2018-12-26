package org.zechac.atlas.metadata.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.zechac.atlas.common.web.JsonResponse;
import org.zechac.atlas.common.web.RequestParamUtils;
import org.zechac.atlas.metadata.entity.DictKey;
import org.zechac.atlas.metadata.entity.DictValue;
import org.zechac.atlas.metadata.service.DictKeyService;
import org.zechac.atlas.metadata.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.zechac.atlas.metadata.service.DictValueService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by My on 2018-04-02.
 */
@RestController
@RequestMapping("api/metadata/dict")
public class DictController {

    @Autowired
    private DictService dictService;
    @Autowired
    private DictKeyService dictKeyService;
    @Autowired
    private DictValueService dictValueService;

    /**
     * 保存全部
     * @param dict
     * @return
     */
    @RequestMapping("save")
    public JsonResponse save(@RequestBody DictKey dict) {
        dictService.save(dict);
        return JsonResponse.success().message("保存成功");
    }


    /**
     * 保存值
     * @param dictValue
     * @return
     */
    @RequestMapping("value/save")
    public JsonResponse valueSave(DictValue dictValue) {
        dictValueService.save(dictValue);
        return JsonResponse.success().message("保存成功");
    }

    /**
     * key的page
     * @param pageable
     * @param param
     * @return
     */
    @RequestMapping("key/page")
    public JsonResponse list(Pageable pageable, @RequestParam Map param) {
        Map query = RequestParamUtils.mapRequestParam(param, "s_");
        Page<DictKey> pages = dictKeyService.queryByPage(query, pageable);
        return JsonResponse.success().data(pages);
    }


    @RequestMapping("key/{id}/value")
    public JsonResponse getDictById(@PathVariable String id) {
        DictValue dict = dictValueService.findTopByDictKeyId(id);
        if (dict != null) {
            return JsonResponse.success().data(dict);
        } else {
            return JsonResponse.fail().message("未找到该字典值");
        }
    }

    @RequestMapping("key/{id}/values/list")
    public JsonResponse getDictListById(@PathVariable String id) {
        Map queryMap=new HashMap();
        queryMap.put("dictKey__id",id);
        List<DictValue> dict = dictValueService.queryList(queryMap);
        if (dict != null) {
            return JsonResponse.success().data(dict);
        } else {
            return JsonResponse.fail().message("未找到该字典值");
        }
    }

    @RequestMapping("key/{id}/values/page")
    public JsonResponse getDictPageById(@PathVariable String id, Pageable pageRequest) {
        Map queryMap=new HashMap();
        queryMap.put("dictKey__id",id);
        Page<DictValue> dict = dictValueService.queryByPage(queryMap,pageRequest);
        if (dict != null) {
            return JsonResponse.success().data(dict);
        } else {
            return JsonResponse.fail().message("未找到该字典值");
        }
    }

}
