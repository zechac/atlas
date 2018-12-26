package org.zechac.atlas.rbac.controller;

import org.zechac.atlas.common.constant.EnableStatus;
import org.zechac.atlas.common.web.JsonResponse;
import org.zechac.atlas.common.web.RequestParamUtils;
import org.zechac.atlas.rbac.entity.Resource;
import org.zechac.atlas.rbac.ResourceType;
import org.zechac.atlas.rbac.dto.ResourceDto;
import org.zechac.atlas.rbac.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/rbac/resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    /**
     * 获取模块菜单
     *
     * @return
     */
    @RequestMapping("subSys")
    public JsonResponse getsubSys() {
        Map query = new HashMap();
        query.put("status", EnableStatus.USE);
        query.put("type", ResourceType.SUBSYS);
        List<Resource> resources = resourceService.queryList(query);
        return JsonResponse.success().data(resources);
    }

    /**
     * 获取子菜单
     *
     * @return
     */
    @RequestMapping("module/{parentId}")
    public JsonResponse getsubSys(@RequestParam Map map, @PathVariable String parentId) {
        Map query = RequestParamUtils.mapRequestParam(map, "s_");
        query.put("parent__id", parentId);
        List<Resource> resources = resourceService.queryList(query);
        return JsonResponse.success().data(resources);
    }

    /**
     * 获取模块下级菜单
     *
     * @return
     */
    @RequestMapping("children/{parentId}")
    public JsonResponse children(@PathVariable String parentId, @RequestParam Map map) {
        Map query = RequestParamUtils.mapRequestParam(map, "s_");
        List<ResourceDto> menus = resourceService.children(parentId, query);
        return JsonResponse.success().data(menus);
    }

    /**
     * 获取所有菜单
     *
     * @return
     */
    @RequestMapping("all")
    public JsonResponse all() {
        List<ResourceDto> menus = resourceService.all();
        return JsonResponse.success().data(menus);
    }

    @RequestMapping("save")
    public JsonResponse saveForm(@RequestBody Resource resource) {
        if ("0".equals(resource.getParent().getId())) {
            resource.setParent(null);
        }
        resourceService.save(resource);
        return JsonResponse.success().data(resource);
    }

    /**
     * 状态修改 启用，停用
     *
     * @return
     */
    @RequestMapping("{id}/status/{status}")
    public JsonResponse status(@PathVariable String id, @PathVariable EnableStatus status) {
        Resource resource = new Resource();
        resource.setId(id);
        resource.setStatus(status);
        resourceService.update(resource, "status");
        return JsonResponse.success().data(resource).message("修改成功");
    }
}
