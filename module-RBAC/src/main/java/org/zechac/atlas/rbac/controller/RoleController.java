package org.zechac.atlas.rbac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.zechac.atlas.common.web.JsonResponse;
import org.zechac.atlas.common.web.RequestParamUtils;
import org.zechac.atlas.rbac.entity.Role;
import org.zechac.atlas.rbac.entity.User;
import org.zechac.atlas.rbac.service.ResourceService;
import org.zechac.atlas.rbac.service.RoleService;
import org.zechac.atlas.rbac.service.UserService;

import javax.persistence.JoinColumn;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色
 */
@RestController
@RequestMapping("api/rbac/role")
public class RoleController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;
    @Autowired
    private ResourceService resourceService;

    /**
     * 分页查询
     * @param pageable
     * @param map
     * @return
     */
    @RequestMapping("page")
    public JsonResponse page(Pageable pageable, @RequestParam Map map) {
        Map query = RequestParamUtils.mapRequestParam(map, "s_");
        Page<Role> users = roleService.queryByPage(query, pageable);
        return JsonResponse.success().data(users);
    }

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    @RequestMapping("get/{id}")
    public JsonResponse getById(@PathVariable String id) {
        Role role = roleService.findById(id);
        if (role != null) {
            return JsonResponse.success().data(role);
        } else {
            return JsonResponse.fail().message("未找到角色信息");
        }
    }

    /**
     * 新增/修改
     *
     * @param
     * @return
     */
    @RequestMapping("save")
    public JsonResponse save(@RequestBody Role role) {
        roleService.save(role);
        return JsonResponse.success().message("成功");
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping("delete/{id}")
    public JsonResponse delete(@PathVariable String id) {
        roleService.deleteById(id);
        return JsonResponse.success();
    }

}
