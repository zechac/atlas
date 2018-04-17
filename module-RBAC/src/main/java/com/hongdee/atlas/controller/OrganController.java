package com.hongdee.atlas.controller;

import com.hongdee.atlas.common.web.JsonResponse;
import com.hongdee.atlas.common.web.RequestParamUtils;
import com.hongdee.atlas.entity.rbac.BankAccount;
import com.hongdee.atlas.entity.rbac.Menu;
import com.hongdee.atlas.entity.rbac.Organ;
import com.hongdee.atlas.entity.rbac.User;
import com.hongdee.atlas.rbac.OrganStatus;
import com.hongdee.atlas.service.OrganService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
*@author作者：王恒腾
*类名：
*功能：部门接口控制
*时间：2018-03-21/16:03
*
*/
@RestController
@RequestMapping("organ")
public class OrganController {
    @Autowired
    private   OrganService organService;
    /**
    *@author作者：王恒腾
    *类名：
    *功能：分页查询
    *时间：2018-03-27/18:10
    *
    */
    @RequestMapping("list")
    public JsonResponse list(Pageable pageable, @RequestParam Map param){
        Map query= RequestParamUtils.mapRequestParam(param,"s_");
        Page<Organ> pages=organService.queryByPage(query,pageable);
        return JsonResponse.success().data(pages);
    }
    /**
    *@author作者：王恒腾
    *功能：保存实体
    *时间：2018-03-21/16:04
    */
    @RequestMapping("save")
    public JsonResponse save(@RequestBody Organ organ){
        for (BankAccount account : organ.getBankAccounts()){
            account.setOrgan(organ);
        }
        organService.save(organ);
        return JsonResponse.success().message("保存成功");
    }
    /**
    *@author作者：王恒腾
    *功能：返回组织机构树
    *时间：2018-03-22/17:36
    */
    @RequestMapping("tree")
    public JsonResponse organTree(){
        List<Organ> organs=organService.buildOrgChild("0");
        return JsonResponse.success().data(organs);
    }

    /**
    *@author作者：王恒腾
    *功能：根据部门id获取部门
    *时间：2018-03-21/16:11
    */
    @RequestMapping("info/{id}")
    public JsonResponse getOrganById(@PathVariable String id){
        Organ organ=organService.findById(id);
        if(organ!=null) {
            return JsonResponse.success().data(organ);
        }else {
            return JsonResponse.fail().message("未找到该组织");
        }
    }
    /**
    *@author作者：王恒腾
    *功能：删除部门
    *时间：2018-03-21/16:12
    */
    @RequestMapping("delete/{id}")
    public JsonResponse delete(@PathVariable String id){
        organService.deleteById(id);
        return JsonResponse.success();
    }
    /**
    *@author作者：王恒腾
    *类名：
    *功能：部门停用启用
    *时间：2018-03-28/18:24
    */
    @RequestMapping("{id}/status/{status}")
    public JsonResponse changeStatus(@PathVariable String id,@PathVariable OrganStatus status){
        Organ organ=new Organ();
        organ.setId(id);
        organ.setStatus(status);
        organService.update(organ,"status");
        return JsonResponse.success().data(organ).message("修改成功");
    }
}
