package org.zechac.atlas.controller;

import org.zechac.atlas.common.web.JsonResponse;
import org.zechac.atlas.common.web.RequestParamUtils;
import org.zechac.atlas.entity.rbac.Menu;
import org.zechac.atlas.rbac.MenuStatus;
import org.zechac.atlas.rbac.MenuType;
import org.zechac.atlas.rbac.dto.MenuDto;
import org.zechac.atlas.service.MenuService;
import org.zechac.atlas.entity.rbac.Menu;
import org.zechac.atlas.rbac.MenuStatus;
import org.zechac.atlas.rbac.MenuType;
import org.zechac.atlas.rbac.dto.MenuDto;
import org.zechac.atlas.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 获取模块菜单
     *
     * @return
     */
    @RequestMapping("subSys")
    public JsonResponse getsubSys() {
        Map query = new HashMap();
        query.put("status", MenuStatus.USE);
        query.put("type", MenuType.SUBSYS);
        List<Menu> menus = menuService.queryList(query);
        return JsonResponse.success().data(menus);
    }

    /**
     * 获取模块菜单
     *
     * @return
     */
    @RequestMapping("module/{parentId}")
    public JsonResponse getsubSys(@RequestParam Map map, @PathVariable String parentId) {
        Map query = RequestParamUtils.mapRequestParam(map, "s_");
        query.put("parent__id", parentId);
        List<Menu> menus = menuService.queryList(query);
        return JsonResponse.success().data(menus);
    }

    /**
     * 获取模块下级菜单
     *
     * @return
     */
    @RequestMapping("children/{parentId}")
    public JsonResponse children(@PathVariable String parentId, @RequestParam Map map) {
        Map query = RequestParamUtils.mapRequestParam(map, "s_");
        List<MenuDto> menus = menuService.children(parentId, query);
        return JsonResponse.success().data(menus);
    }

    /**
     * 获取所有菜单
     *
     * @return
     */
    @RequestMapping("all")
    public JsonResponse all() {
        List<MenuDto> menus = menuService.all();
        return JsonResponse.success().data(menus);
    }

    @RequestMapping("save")
    public JsonResponse saveForm(@RequestBody Menu menu) {
        if ("0".equals(menu.getParent().getId())) {
            menu.setParent(null);
        }
        menuService.save(menu);
        return JsonResponse.success().data(menu);
    }

    /**
     * 状态修改 启用，停用
     *
     * @return
     */
    @RequestMapping("{id}/status/{status}")
    public JsonResponse status(@PathVariable String id, @PathVariable MenuStatus status) {
        Menu menu = new Menu();
        menu.setId(id);
        menu.setStatus(status);
        menuService.update(menu, "status");
        return JsonResponse.success().data(menu).message("修改成功");
    }
}
