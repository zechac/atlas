package com.hongdee.atlas.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hongdee.atlas.common.exception.AtlasException;
import com.hongdee.atlas.common.jdbc.template.CommonQuery;
import com.hongdee.atlas.common.services.BaseServiceImpl;
import com.hongdee.atlas.common.web.JsonResponse;
import com.hongdee.atlas.entity.dynamic.DynamicServiceTemplate;
import com.hongdee.atlas.entity.dynamic.ParamsCollection;
import com.hongdee.atlas.mybatis.mapper.MybatisQuery;
import com.hongdee.atlas.repo.DynamicServiceRepo;
import com.hongdee.atlas.service.kind.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

/**
 * @param
 * @param
 * @param
 * @return
 * @throws
 */
@Service
@Transactional
public class ExecutorService {

    @Autowired
    private DynamicServiceRepo dynamicServiceRepo;

    @Autowired
    private KindFactory kindFactory;
    /**
     * 执行服务
     * @param templateId
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
    public JsonResponse exec(String templateId, Map params){
        DynamicServiceTemplate template=dynamicServiceRepo.findById(templateId).orElse(null);
        ParamsCollection paramsCollection=new ParamsCollection();
        paramsCollection.putAll(params);
        for(Object o: template.getServiceTemplate()){
            JSONObject jsonObject=(JSONObject)o;
            String kind=((Map)o).get("kind").toString();
            switch (kind){
                case Kind.SqlSelectList:
                    SqlSelectList sqlSelectList=kindFactory.get(jsonObject);
                    sqlSelectList.exec(paramsCollection);
                    break;
                case Kind.SqlSelectOne:
                    SqlSelectOne sqlSelectOne=kindFactory.get(jsonObject);
                    sqlSelectOne.exec(paramsCollection);
                    break;
                case Kind.ParamsFilter:
                    ParamsFilter paramsFilter=kindFactory.get(jsonObject);
                    paramsFilter.exec(paramsCollection);
                    break;
                case Kind.MybatisSelectList:
                    MybatisSelect mybatisSelect=kindFactory.get(jsonObject);
                    mybatisSelect.exec(paramsCollection);
                    break;
                default:
                    throw new AtlasException(ExecutorService.class,"暂未实现的kind:"+kind);
            }
        }
        return JsonResponse.success().data(paramsCollection);
    }

}
