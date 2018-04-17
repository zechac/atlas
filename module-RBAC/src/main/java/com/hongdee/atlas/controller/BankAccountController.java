package com.hongdee.atlas.controller;

/**
 * Created by My on 2018-03-28.
 */

import com.hongdee.atlas.common.web.JsonResponse;
import com.hongdee.atlas.common.web.RequestParamUtils;
import com.hongdee.atlas.entity.rbac.BankAccount;
import com.hongdee.atlas.entity.rbac.Organ;
import com.hongdee.atlas.rbac.BankAccountStatus;
import com.hongdee.atlas.service.BankAccountService;
import com.hongdee.atlas.service.OrganService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
*@author作者：王恒腾
*类名：
*功能：银行卡实体层
*时间：2018-03-28/20:34
*/
@RestController
@RequestMapping("bank")
public class BankAccountController {
    @Autowired
    public BankAccountService bankAccountService;
    /*@author作者：王恒腾
    *功能：保存实体
    *时间：2018-03-21/16:04
    */
    @RequestMapping("save")
    public JsonResponse save(@RequestBody BankAccount bankAccount){
        bankAccountService.save(bankAccount);
        return JsonResponse.success().message("保存成功");
    }
    /**
     * 方法名称:
     * 方法描述:信用卡启用/停用
     * 参数名称:
     * 参数名称:
     * 参数名称:
     * 返回内容:
     * 异常说明:
     * 创建用户: 王恒腾
     * 创建时间:
     * 修改用户:
     * 修改时间:
     * 修改内容:
     */
    @RequestMapping("{id}/status/{status}")
    public JsonResponse changeStatus(@PathVariable String id, @PathVariable BankAccountStatus sta){
        BankAccount bankAccount=new BankAccount();
        bankAccount.setId(id);
        bankAccount.setStatus(sta);
        bankAccountService.update(bankAccount,"status");
        return JsonResponse.success().data(bankAccount).message("修改成功");
    }
    @RequestMapping("list/{oId}")
    public JsonResponse list(@RequestParam Map param){
        Map query= RequestParamUtils.mapRequestParam(param,"s_");
        List<BankAccount> lists=bankAccountService.queryList(query);
        return JsonResponse.success().data(lists);
    }
    /**
     *@author作者：王恒腾
     *功能：删除银行卡
     *时间：2018-03-21/16:12
     */
    @RequestMapping("delete/{id}")
    public JsonResponse deleteBank (@PathVariable String id){
        bankAccountService.deleteById(id);
        return JsonResponse.success().message("移除成功！");
    }
}
