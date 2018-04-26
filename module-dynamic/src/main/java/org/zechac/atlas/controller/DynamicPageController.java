package org.zechac.atlas.controller;

import org.zechac.atlas.common.web.JsonResponse;
import org.zechac.atlas.common.web.RequestParamUtils;
import org.zechac.atlas.entity.dynamic.DynamicPage;
import org.zechac.atlas.service.DynamicPageService;
import org.zechac.atlas.entity.dynamic.DynamicPage;
import org.zechac.atlas.service.DynamicPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("dynamic/page")
public class DynamicPageController {

    @Autowired
    private DynamicPageService pageService;

    @RequestMapping("save")
    public JsonResponse saveForm(@RequestBody DynamicPage dynamicPage) {
        pageService.save(dynamicPage);
        return JsonResponse.success().data(dynamicPage);
    }

    @RequestMapping("delete/{id}")
    public JsonResponse delete(@PathVariable String id) {
        pageService.deleteById(id);
        return JsonResponse.success().put("id", id);
    }

    @RequestMapping("get/{id}")
    public JsonResponse saveForm(@PathVariable String id) {
        DynamicPage DynamicPage = pageService.findById(id);
        if (DynamicPage != null) {
            return JsonResponse.success().data(DynamicPage);
        } else {
            return JsonResponse.fail().message("没有找到相关信息");
        }
    }

    @RequestMapping("list")
    public JsonResponse list(Pageable pageable, @RequestParam Map param, String[] select) {

        Map query = RequestParamUtils.mapRequestParam(param, "s_");
        Page<DynamicPage> forms = pageService.queryByPage(query, pageable, select);
        return JsonResponse.success().data(forms);
    }

    /**
     * 获取动态页面列表
     *
     * @return
     */
    @RequestMapping("all")
    public JsonResponse all() {
        Map query = new HashMap();
        query.put("id$NOTNULL", null);
        List<DynamicPage> dynamicPages = pageService.queryList(query);
        return JsonResponse.success().data(dynamicPages);
    }

}
