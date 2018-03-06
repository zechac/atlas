package com.hongdee.atlas.common.convert;

import com.hongdee.atlas.common.exception.AtlasConvertException;
import com.hongdee.atlas.common.exception.ModuleException;
import org.springframework.core.convert.converter.Converter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/10/25/025.
 */
public class StringToDateConverter implements Converter<String,Date> {

    public StringToDateConverter(String format){
        this.simpleDateFormat=new SimpleDateFormat(format);
    }

    private SimpleDateFormat simpleDateFormat=null;


    @Override
    public Date convert(String source) {
        try {
            return simpleDateFormat.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new AtlasConvertException(StringToDateConverter.class,"[String]->[Date]类型转换失败：值（"+source+"）");
        }
    }
}
