package org.zechac.atlas.rbac.controller;

import com.alibaba.fastjson.JSONObject;
import org.zechac.atlas.common.web.JsonResponse;
import org.zechac.atlas.rbac.service.ExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @param
 * @param
 * @param
 * @return
 * @throws
 */
@RestController
@RequestMapping("dynamic")
public class ExecutorController {

    @Autowired
    private ExecutorService executorService;

    @PostMapping("executor")
    public JsonResponse execPost(@RequestBody JSONObject data, String templateId) {
        return executorService.exec(templateId, data);
    }

    @GetMapping("executor")
    public JsonResponse execGet(@RequestParam Map data, String templateId) {
        return executorService.exec(templateId, data);
    }

}
