package org.zechac.atlas.controller;

import org.zechac.atlas.common.web.JsonResponse;
import org.zechac.atlas.common.web.RequestParamUtils;
import org.zechac.atlas.entity.rbac.Role;
import org.zechac.atlas.entity.rbac.User;
import org.zechac.atlas.service.RoleService;
import org.zechac.atlas.service.UserService;
import org.zechac.atlas.entity.rbac.Role;
import org.zechac.atlas.entity.rbac.User;
import org.zechac.atlas.service.RoleService;
import org.zechac.atlas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @RequestMapping("page")
    public JsonResponse list(Pageable pageable, @RequestParam Map map) {
        Map query = RequestParamUtils.mapRequestParam(map, "s_");
        Page<User> users = userService.queryByPage(query, pageable);
        return JsonResponse.success().data(users);
    }

    /**
     * 根据ID查询用户
     *
     * @param id
     * @return
     */
    @RequestMapping("{id}")
    public JsonResponse getUserById(@PathVariable String id) {
        User user = userService.findById(id);
        if (user != null) {
            return JsonResponse.success().data(user);
        } else {
            return JsonResponse.fail().message("未找到用户");
        }
    }

    /**
     * 新增/修改用户
     *
     * @param user
     * @return
     */
    @RequestMapping("save")
    public JsonResponse save(@RequestBody User user) {
        userService.save(user);
        return JsonResponse.success().message("成功");
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @RequestMapping("delete/{id}")
    public JsonResponse delete(@PathVariable String id) {
        userService.deleteById(id);
        return JsonResponse.success();
    }

    /**
     * TODO:用户登录
     *
     * @return
     */
    @RequestMapping("login")
    public JsonResponse login(User user) {
        User user1 = userService.login(user);
        if (user1 != null) {
            return JsonResponse.success().data(user1).message("登录成功");
        }
        return JsonResponse.fail().message("用户名/密码错误");
    }

    /**
     * 获取用户角色
     *
     * @return
     */
    @RequestMapping("roles")
    public JsonResponse roles(String uid) {
        Map q = new HashMap();
        q.put("users__id", uid);
        List<Role> roles = roleService.queryList(q);
        return JsonResponse.success().data(roles);
    }


}
