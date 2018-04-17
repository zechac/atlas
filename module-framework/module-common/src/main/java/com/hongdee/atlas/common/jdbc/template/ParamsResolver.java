package com.hongdee.atlas.common.jdbc.template;

import com.hongdee.atlas.common.exception.AtlasSqlException;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParamsResolver {

    /**
     * 匹配#{}里的内容
     */
    Pattern pattern=Pattern.compile("(?<=#\\{)[^}]*(?=\\})");

    <T> Object getParamValue(T parmas,String props){
        if(Map.class.isAssignableFrom(parmas.getClass())){
            return ((Map)parmas).get(props);
        }else{
            try {
               return PropertyUtils.getProperty(parmas,props);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        throw new AtlasSqlException(ParamsResolver.class,String.format("参数解析错误[%s]=%s",parmas.getClass().toString(),props));
    }

    <T> Object[] resolver(String sql,T params){
        Matcher matcher=pattern.matcher(sql);
        List list=new ArrayList();
        while (matcher.find()){
            list.add(getParamValue(params,matcher.group()));
        }
        return list.toArray();
    }
}
