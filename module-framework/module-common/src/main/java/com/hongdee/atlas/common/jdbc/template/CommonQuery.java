package com.hongdee.atlas.common.jdbc.template;

import lombok.Data;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 通用sql查询
 * @author  zhaozh
 */
@Data
public class CommonQuery {

    private JdbcTemplate jdbcTemplate;

    private ParamsResolver paramsResolver=new ParamsResolver();

    /**
     * 参数#{} 替换为？
     * @param sql
     * @return
     */
    String parseSql(String sql){
        return sql.replaceAll("#\\{[\\S\\d]+\\}","?");
    }

    /**
     * 查询列表
     * @param sql
     * @param params
     * @return
     */
    public List queryListByMap(String sql, Map params){
        Object[] p=paramsResolver.resolver(sql,params);
        sql=parseSql(sql);
        return jdbcTemplate.queryForList(sql,p);
    }

    /**
     * 单个查询
     * @param sql
     * @param params
     * @return
     */
    public Map queryOneByMap(String sql, Map params) {
        Object[] p = paramsResolver.resolver(sql, params);
        sql=parseSql(sql);
        return jdbcTemplate.queryForMap(sql, p);
    }
}
