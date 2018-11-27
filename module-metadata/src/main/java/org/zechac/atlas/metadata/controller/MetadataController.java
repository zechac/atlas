package org.zechac.atlas.metadata.controller;

import org.zechac.atlas.common.web.JsonResponse;
import org.zechac.atlas.common.web.RequestParamUtils;
import org.zechac.atlas.metadata.entity.Metadata;
import org.zechac.atlas.metadata.entity.MetadataGroup;
import org.zechac.atlas.metadata.service.MetadataGroupService;
import org.zechac.atlas.metadata.service.MetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

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
     *
     * @param pageable /metadata/group/list?page=0&size=10&s_name=2
     * @return
     */
    @RequestMapping("group/list")
    public JsonResponse groupList(Pageable pageable, @RequestParam Map param, String[] select) {
        Map query = RequestParamUtils.mapRequestParam(param, "s_");
        Page<MetadataGroup> metadataGroups = metadataGroupService.queryByPage(query, pageable, select);
        return JsonResponse.success().data(metadataGroups);
    }

    /**
     * 获取元数据
     *
     * @param pageable
     * @return
     */
    @RequestMapping("list")
    public JsonResponse list(Pageable pageable, @RequestParam Map param) {
        Map query = RequestParamUtils.mapRequestParam(param, "s_");
        Page<Metadata> metadataGroups = metadataService.queryByPage(query, pageable);
        return JsonResponse.success().data(metadataGroups);
    }

    /**
     * 修改元数据
     *
     * @return
     */
    @RequestMapping("save")
    public JsonResponse mdy(@RequestBody Metadata metadata) {
        metadataService.syncSave(metadata);
        return JsonResponse.success().data(metadata);
    }

    /**
     * 修改元数据组
     * /metadata/group/mdy?name=2
     *
     * @return
     */
    @RequestMapping("group/save")
    public JsonResponse groupMdy(@RequestBody MetadataGroup metadataGroup) {
        metadataGroupService.syncSave(metadataGroup);
        return JsonResponse.success().data(metadataGroup);
    }

    /**
     * 删除元数据组
     * /metadata/group/delete/1
     *
     * @return
     */
    @RequestMapping("group/delete/{id}")
    public JsonResponse groupDelete(@PathVariable String id) {
        MetadataGroup metadataGroup = metadataGroupService.findById(id);
        if (metadataGroup.isStandard()) {
            return JsonResponse.fail().message("不能删除标准数据");
        }
        metadataGroupService.delete(id);
        return JsonResponse.success();
    }

    /**
     * 删除元数据
     *
     * @return
     */
    @RequestMapping("delete/{id}")
    public JsonResponse delete(@PathVariable String id) {
        metadataService.delete(id);
        return JsonResponse.success();
    }

    /**
     * 刷新元数据
     *
     * @return
     */
    @RequestMapping("flush/{groupId}")
    public JsonResponse flush(@PathVariable String groupId) {
        metadataService.flush(groupId);
        return JsonResponse.success();
    }

    /**
     * 刷新所有元数据
     *
     * @return
     */
    @RequestMapping("flush/all")
    public JsonResponse flush() {
        metadataGroupService.flush();
        return JsonResponse.success();
    }

    /**
     * 刷新元数据组
     *
     * @return
     */
    @RequestMapping("group/flush")
    public JsonResponse flushGroup() {
        metadataGroupService.flushGroup();
        return JsonResponse.success();
    }
}
