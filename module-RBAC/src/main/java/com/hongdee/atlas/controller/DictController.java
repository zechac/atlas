package com.hongdee.atlas.controller;

import com.hongdee.atlas.common.web.JsonResponse;
import com.hongdee.atlas.common.web.RequestParamUtils;
import com.hongdee.atlas.entity.rbac.BankAccount;
import com.hongdee.atlas.entity.rbac.Dict;
import com.hongdee.atlas.entity.rbac.Organ;
import com.hongdee.atlas.rbac.DictStatus;
import com.hongdee.atlas.rbac.OrganStatus;
import com.hongdee.atlas.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by My on 2018-04-02.
 */
/**
*@author ：王恒腾
*类名：
*功能：字典接口类
*时间：2018-04-02/9:37
*
*/
@RestController
@RequestMapping("dict")
public class DictController {
    @Autowired
    private DictService dictService;
    /**
    *@author作者：王恒腾
    *类名：
    *功能：字典保存
    *时间：2018-04-02/0:09
    *
    */
    @RequestMapping("save")
    public JsonResponse save(@RequestBody Dict dict){
        dictService.save(dict);
        return JsonResponse.success().message("保存成功");
    }
    /**
    *@author作者：王恒腾
    *类名：
    *功能：获取字典树，先查询父节点为-1的节点
    *时间：2018-04-02/0:26
    *
    */
    @RequestMapping("tree")
    public JsonResponse dictTree(){
        List<Dict> dicts=dictService.buildDictChild("-1");
        return JsonResponse.success().data(dicts);
    }
    /**
    *@author作者：王恒腾
    *类名：
    *功能：获取分页
    *时间：2018-04-02/0:35
    *
    */
    @RequestMapping("list")
    public JsonResponse list(Pageable pageable, @RequestParam Map param){
        Map query= RequestParamUtils.mapRequestParam(param,"s_");
        Page<Dict> pages=dictService.queryByPage(query,pageable);
        return JsonResponse.success().data(pages);
    }
    /**
    *@author作者：王恒腾
    *类名：
    *功能：根据id获取字典详情
    *时间：2018-04-02/0:26
    *
    */
    @RequestMapping("info/{id}")
    public JsonResponse getDictById(@PathVariable String id){
        Dict dict=dictService.findById(id);
        if(dict!=null) {
            return JsonResponse.success().data(dict);
        }else {
            return JsonResponse.fail().message("未找到该字典");
        }
    }
    /**
    *@author作者：王恒腾
    *类名：
    *功能：字典启用/停用
    *时间：2018-04-02/0:29
    *
    */
    @RequestMapping("{id}/status/{status}")
    public JsonResponse changeStatus(@PathVariable String id,@PathVariable DictStatus status){
        Dict dict=new Dict();
        dict.setId(id);
        dict.setStatus(status);
        dictService.update(dict,"status");
        return JsonResponse.success().data(dict).message("修改成功");
    }
}
