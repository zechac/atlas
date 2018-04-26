package org.zechac.atlas.controller;

import org.zechac.atlas.common.web.JsonResponse;
import org.zechac.atlas.common.web.RequestParamUtils;
import org.zechac.atlas.entity.dynamic.DynamicForm;
import org.zechac.atlas.entity.dynamic.Module;
import org.zechac.atlas.service.FormService;
import org.zechac.atlas.service.ModuleService;
import org.zechac.atlas.entity.dynamic.DynamicForm;
import org.zechac.atlas.entity.dynamic.Module;
import org.zechac.atlas.service.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("dynamic")
public class DynamicController {

    @Autowired
    private ModuleService moduleService;

    @Qualifier("dynamicFormService")
    private FormService formService;

    @RequestMapping("module/{id}")
    public JsonResponse getModule(@PathVariable String id) {
        Module module = moduleService.getModuleById(id);
        return JsonResponse.success().data(module);
    }

    @RequestMapping("form/save")
    public JsonResponse saveForm(@RequestBody DynamicForm dynamicForm) {
        formService.save(dynamicForm);
        return JsonResponse.success().data(dynamicForm);
    }

    @RequestMapping("form/get/{id}")
    public JsonResponse saveForm(@PathVariable String id) {
        DynamicForm dynamicForm = formService.findById(id);
        if (dynamicForm != null) {
            return JsonResponse.success().data(dynamicForm);
        } else {
            return JsonResponse.fail().message("没有找到相关信息");
        }
    }

    @RequestMapping("list")
    public JsonResponse list(Pageable pageable, @RequestParam Map param, String[] select) {

        Map query = RequestParamUtils.mapRequestParam(param, "s_");
        Page<DynamicForm> forms = formService.queryByPage(query, pageable, select);
        return JsonResponse.success().data(forms);
    }


}
