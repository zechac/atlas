package org.zechac.atlas.common.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hua on 2016/10/13.
 */
public class RequestParamUtils {
    public static Map mapRequestParam(Map<String, Object> map, String prefix) {
        Map ret = new HashMap();
        if (map == null) {
            return ret;
        }
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String s = entry.getKey();
            Object o = entry.getValue();
            if (o != null && StringUtils.isNotBlank(o.toString())) {
                if (StringUtils.isNotBlank(prefix)) {
                    if (s.startsWith(prefix)) {
                        ret.put(s.replaceFirst(prefix, ""), o);
                    }
                } else {
                    ret.put(s, o);
                }
            }
        }
        return ret;
    }

    public static Pageable jsonToPage(JSONObject jsonObject) {
        if (!(jsonObject.containsKey("page") && jsonObject.containsKey("size"))) {
            throw new IllegalArgumentException();
        }
        Sort sort = null;
        if (jsonObject.containsKey("orders")) {
            JSONArray jsonArray = jsonObject.getJSONArray("orders");
            sort = jsonToSort(jsonArray);
        }
        if (sort != null) {
            return new PageRequest(jsonObject.getInteger("page"), jsonObject.getInteger("size"), sort);
        } else {
            return new PageRequest(jsonObject.getInteger("page"), jsonObject.getInteger("size"));
        }
    }

    public static Sort jsonToSort(JSONArray jsonArray) {
        List<Sort.Order> orderList = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            String[] s = jsonArray.getString(i).split("_");
            String dir = "DESC";
            if (s.length > 1) {
                dir = s[1];
            }
            Sort.Order order = new Sort.Order(Sort.Direction.valueOf(dir), s[0]);
            orderList.add(order);
        }
        Sort sort = new Sort(orderList);
        return sort;
    }
}
