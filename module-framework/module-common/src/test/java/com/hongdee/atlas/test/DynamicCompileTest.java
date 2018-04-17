package com.hongdee.atlas.test;

import com.alibaba.fastjson.JSON;
import com.hongdee.atlas.common.utils.DynamicCompile;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 动态编译代码测试
 */
@Slf4j
@SpringBootTest
public class DynamicCompileTest {

    @Test
    public void compileTest(){
        StringBuilder sb = new StringBuilder();
        sb.append("package com.even.test;");
        sb.append("import java.util.Map;\nimport java.text.DecimalFormat;\n");
        sb.append("public class Sum{\n");
        sb.append("private final DecimalFormat df = new DecimalFormat(\"#.#####\");\n");
        sb.append("public Double calculate(Map<String,Double> data){\n");
        sb.append("double d = (30*data.get(\"f1\") + 20*data.get(\"f2\") + 50*data.get(\"f3\"))/100;\n");
        sb.append("return Double.valueOf(df.format(d));}}\n");
        //设置编译参数
        ArrayList<String> ops = new ArrayList<String>();
        ops.add("-Xlint:unchecked");
        //编译代码，返回class
        Class<?> cls = DynamicCompile.loadClass("/com/even/test/Sum.java",sb.toString(),"com.even.test.Sum",ops);
        //准备测试数据
        Map<String,Double> data = new HashMap<String,Double>();
        data.put("f1", 10.0);
        data.put("f2", 20.0);
        data.put("f3", 30.0);
        //执行测试方法
        Object result = DynamicCompile.invoke(cls, "calculate", new Class[]{Map.class}, new Object[]{data});
        //输出结果
        log.debug(JSON.toJSONString(data));
        log.debug("(30*f1+20*f2+50*f3)/100 = "+result);
    }
}
