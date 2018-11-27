package org.zechac.atlas.rbac.service;

import org.zechac.atlas.common.services.BaseServiceImpl;
import org.zechac.atlas.rbac.entity.Resource;
import org.zechac.atlas.rbac.dto.ResourceDto;
import org.zechac.atlas.rbac.repo.ResourceRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhaozh
 */
@Service
@Transactional
public class ResourceService extends BaseServiceImpl<Resource, ResourceRepo> {

    private void copyProperties(ResourceDto resourceDto, Resource resource) {
        resourceDto.setId(resource.getId());
        resourceDto.setName(resource.getName());
        resourceDto.setStatus(resource.getStatus());
        resourceDto.setType(resource.getType());
        resourceDto.setUrl(resource.getUrl());
        resourceDto.setCode(resource.getCode());
        resourceDto.setParams(resource.getParams());
        resourceDto.setDynamicPageId(resource.getDynamicPageId());
        if (resource.getParent() != null) {
            resourceDto.setParentId(resource.getParent().getId());
        }
    }

    /**
     * 获取菜单的树形结构表示
     *
     * @param resourceDto
     * @param resource
     */
    private void fillMenuDtoChildren(ResourceDto resourceDto, Resource resource) {
        copyProperties(resourceDto, resource);
        List<ResourceDto> resourceDtos = new ArrayList<>();
        for (Resource resource1 : resource.getChildren()) {
            ResourceDto resourceDto1 = new ResourceDto();
            fillMenuDtoChildren(resourceDto1, resource1);
            resourceDtos.add(resourceDto1);
        }
        resourceDto.setChildren(resourceDtos);
    }

    /**
     * 获取所有菜单
     *
     * @return
     */
    public List<ResourceDto> all() {
        Map query = new HashMap();
        //query.put("status", EnableStatus.USE);
        query.put("parent$NULL", null);
        List<Resource> resources = queryList(query);
        List<ResourceDto> resourceDtos = new ArrayList<>();
        for (Resource resource : resources) {
            ResourceDto resourceDto = new ResourceDto();
            fillMenuDtoChildren(resourceDto, resource);
            resourceDtos.add(resourceDto);
        }
        return resourceDtos;
    }

    /**
     * 获取下级菜单
     *
     * @param parentId
     * @return
     */
    public List<ResourceDto> children(String parentId, Map query) {
        //query.put("status", EnableStatus.USE);
        query.put("parent__id", parentId);
        List<Resource> resources = queryList(query);
        List<ResourceDto> resourceDtos = new ArrayList<>();
        for (Resource resource : resources) {
            ResourceDto resourceDto = new ResourceDto();
            fillMenuDtoChildren(resourceDto, resource);
            resourceDtos.add(resourceDto);
        }
        return resourceDtos;
    }
}
