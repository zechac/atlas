package org.zechac.atlas.rbac.dto;

import org.zechac.atlas.rbac.MenuStatus;
import org.zechac.atlas.rbac.MenuType;
import lombok.Data;

import java.util.List;

@Data
public class MenuDto {

    private String id;

    private String name;

    private MenuType type;

    private MenuStatus status;

    private String url;

    private String parentId;

    private List<MenuDto> children;

    private String code;

    private String params;

    private String dynamicPageId;

}
