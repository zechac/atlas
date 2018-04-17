package com.hongdee.atlas.service;

import com.hongdee.atlas.common.services.BaseServiceImpl;
import com.hongdee.atlas.entity.rbac.Menu;
import com.hongdee.atlas.rbac.MenuStatus;
import com.hongdee.atlas.rbac.dto.MenuDto;
import com.hongdee.atlas.repo.MenuRepo;
import org.apache.commons.beanutils.BeanUtils;
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
public class MenuService extends BaseServiceImpl<Menu,MenuRepo> {

    private void copyProperties(MenuDto menuDto,Menu menu){
        menuDto.setId(menu.getId());
        menuDto.setName(menu.getName());
        menuDto.setStatus(menu.getStatus());
        menuDto.setType(menu.getType());
        menuDto.setUrl(menu.getUrl());
        menuDto.setCode(menu.getCode());
        menuDto.setParams(menu.getParams());
        menuDto.setDynamicPageId(menu.getDynamicPageId());
        if(menu.getParent()!=null) {
            menuDto.setParentId(menu.getParent().getId());
        }
    }

    /**
     * 获取菜单的树形结构表示
     * @param menuDto
     * @param menu
     */
    private void fillMenuDtoChildren(MenuDto menuDto,Menu menu){
        copyProperties(menuDto,menu);
        List<MenuDto> menuDtos=new ArrayList<>();
        for(Menu menu1: menu.getChildren()){
            MenuDto menuDto1=new MenuDto();
            fillMenuDtoChildren(menuDto1,menu1);
            menuDtos.add(menuDto1);
        }
        menuDto.setChildren(menuDtos);
    }

    /**
     * 获取所有菜单
     * @return
     */
    public List<MenuDto> all(){
        Map query=new HashMap();
        //query.put("status", MenuStatus.USE);
        query.put("parent$NULL",null);
        List<Menu> menus=queryList(query);
        List<MenuDto> menuDtos=new ArrayList<>();
        for(Menu menu:menus){
            MenuDto menuDto=new MenuDto();
            fillMenuDtoChildren(menuDto,menu);
            menuDtos.add(menuDto);
        }
        return menuDtos;
    }

    /**
     * 获取下级菜单
     * @param parentId
     * @return
     */
    public List<MenuDto> children(String parentId,Map query){
        //query.put("status", MenuStatus.USE);
        query.put("parent__id",parentId);
        List<Menu> menus=queryList(query);
        List<MenuDto> menuDtos=new ArrayList<>();
        for(Menu menu:menus){
            MenuDto menuDto=new MenuDto();
            fillMenuDtoChildren(menuDto,menu);
            menuDtos.add(menuDto);
        }
        return menuDtos;
    }
}
