package com.hongdee.atlas.demo.controller;

import com.hongdee.atlas.common.web.JsonResponse;
import com.hongdee.atlas.common.web.RequestParamUtils;
import com.hongdee.atlas.demo.entity.Demo;
import com.hongdee.atlas.demo.mapper.DemoMapper;
import com.hongdee.atlas.demo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.util.Map;

@RestController
@RequestMapping("demo")
public class DemoController {

    @Autowired
    private DemoService demoService;

    @Autowired
    private DemoMapper demoMapper;

    @Autowired
    private DataSource dataSource;

    @RequestMapping("get")
    public JsonResponse getDemo(@RequestParam Map map, Pageable pageable){
        Map query= RequestParamUtils.mapRequestParam(map,"s_");
        Page<Demo> demoPage=demoService.queryByPage(query,pageable);
        return JsonResponse.success().Data(demoPage);
    }

    @RequestMapping("save")
    public JsonResponse save(Demo demo){
        demoService.save(demo);
        return JsonResponse.success();
    }

    @RequestMapping("del")
    public JsonResponse del(String id){
        demoService.deleteById(id);
        return JsonResponse.success();
    }

    @RequestMapping("mybatis/get/{id}")
    public JsonResponse mybatisGet(@PathVariable String id){
        Demo demo=demoMapper.getOne(id);
        return JsonResponse.success().Data(demo);
    }
}
