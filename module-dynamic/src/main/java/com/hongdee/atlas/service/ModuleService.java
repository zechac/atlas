package com.hongdee.atlas.service;

import com.hongdee.atlas.common.services.BaseServiceImpl;
import com.hongdee.atlas.dynamic.DynamicView;
import com.hongdee.atlas.entity.dynamic.DynamicForm;
import com.hongdee.atlas.entity.dynamic.Module;
import com.hongdee.atlas.repo.ModuleRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ModuleService extends BaseServiceImpl<Module,ModuleRepo>{

    /**
     * 获取一个动态模块
     * @param id
     * @return
     */
    public Module getModuleById(String id){
        Module module=findById(id);
        List<DynamicView> dynamicViews=new ArrayList<>();
        List<DynamicForm> dynamicForms =module.getDynamicForms();
        dynamicViews.addAll(dynamicForms);
        module.setDatas(dynamicViews);
        return module;
    }
}
