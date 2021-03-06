package org.zechac.atlas.demo.controller;

import org.zechac.atlas.common.web.JsonResponse;
import org.zechac.atlas.common.web.RequestParamUtils;
import org.zechac.atlas.demo.entity.Demo;
import org.zechac.atlas.demo.mapper.DemoMapper;
import org.zechac.atlas.demo.mapper.DemoMapperXML;
import org.zechac.atlas.demo.service.DemoService;
import org.zechac.atlas.demo.entity.Demo;
import org.zechac.atlas.demo.mapper.DemoMapper;
import org.zechac.atlas.demo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("demo")
public class DemoController {

    @Autowired
    private DemoService demoService;

    @Autowired
    private DemoMapperXML demoMapperXML;

    @Autowired
    private DemoMapper demoMapper;

    @Autowired
    private DataSource dataSource;

    @RequestMapping("get")
    public JsonResponse getDemo(@RequestParam Map map, Pageable pageable) {
        Map query = RequestParamUtils.mapRequestParam(map, "s_");
        Page<Demo> demoPage = demoService.queryByPage(query, pageable);
        return JsonResponse.success().data(demoPage);
    }

    @RequestMapping("save")
    public JsonResponse save(Demo demo) {
        demoService.save(demo);
        return JsonResponse.success();
    }

    @RequestMapping("del")
    public JsonResponse del(String id) {
        demoService.deleteById(id);
        return JsonResponse.success();
    }

    @RequestMapping("mybatis/get/{id}")
    public JsonResponse mybatisGet(@PathVariable String id) {
        Demo demo = demoMapper.getOne(id);
        return JsonResponse.success().data(demo);
    }

    @RequestMapping("mybatis/xml/get")
    public JsonResponse mybatisXmlGet() {
        List<Demo> demo = demoMapperXML.findDemoByXml();
        return JsonResponse.success().data(demo);
    }

    @RequestMapping("mybatis/dynamic/get/{id}")
    public JsonResponse mybatisDynamicGet(@PathVariable String id) {
        Demo demo = demoMapper.getById(id);
        return JsonResponse.success().data(demo);
    }
}
