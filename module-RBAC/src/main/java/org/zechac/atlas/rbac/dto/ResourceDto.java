package org.zechac.atlas.rbac.dto;

import org.zechac.atlas.common.constant.EnableStatus;
import org.zechac.atlas.rbac.ResourceType;
import lombok.Data;

import java.util.List;

@Data
public class ResourceDto {

    private String id;

    private String name;

    private ResourceType type;

    private String url;

    private String parentId;

    private List<ResourceDto> children;

    private String code;

    private String params;

    private String dynamicPageId;

    private EnableStatus status;

}
