package org.zechac.atlas.service;

import org.zechac.atlas.common.services.BaseServiceImpl;
import org.zechac.atlas.dynamic.DynamicView;
import org.zechac.atlas.entity.dynamic.DynamicForm;
import org.zechac.atlas.entity.dynamic.Module;
import org.zechac.atlas.repo.ModuleRepo;
import org.zechac.atlas.common.services.BaseServiceImpl;
import org.zechac.atlas.dynamic.DynamicView;
import org.zechac.atlas.entity.dynamic.DynamicForm;
import org.zechac.atlas.entity.dynamic.Module;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ModuleService extends BaseServiceImpl<Module, ModuleRepo> {

    /**
     * 获取一个动态模块
     *
     * @param id
     * @return
     */
    public Module getModuleById(String id) {
        Module module = findById(id);
        List<DynamicView> dynamicViews = new ArrayList<>();
        List<DynamicForm> dynamicForms = module.getDynamicForms();
        dynamicViews.addAll(dynamicForms);
        module.setDatas(dynamicViews);
        return module;
    }
}
