package com.hongdee.atlas.rbac.dto;

import com.hongdee.atlas.rbac.MenuStatus;
import com.hongdee.atlas.rbac.MenuType;
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
