package com.hongdee.atlas.controller;




import com.google.common.io.ByteSource;
import freemarker.template.SimpleHash;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


@RestController
@RequestMapping("upload")
@CrossOrigin
public class UploadController {

    @RequestMapping("check")
    @ResponseBody
    /**
     * 上传检查。
     * 上传之前先check,查询已上传字节数。文件不存在就返回0
     * @param request
     * @return
     */
    public String check(HttpServletRequest request) {
        String name = request.getParameter("name");
        String size = request.getParameter("size");
        String type = request.getParameter("type");
        String lastModified = request.getParameter("lastModified");
        String nk = size+type+lastModified;
        //String fileName = nk + "-" + name;
        String fileName = name;
        File file = new File("..//" + fileName);
        if (!file.exists()) {
            return "0";
        } else {
            return file.length() + "";
        }
    }

    @RequestMapping("upload")
    @ResponseBody
    /**
     * 上传方法
     * 主体就是将InputStream流中的数据写入目标文件中，注意是append
     * @param request
     * @return
     */
    public String upload(HttpServletRequest request) {
        String name = request.getParameter("name");
        String size = request.getParameter("size");
        String type = request.getParameter("type");
        String lastModified = request.getParameter("lastModified");
        String nk = size+type+lastModified;
        //String fileName = nk + "-" + name;
        String fileName = name;
        ServletInputStream is = null;
        FileOutputStream os = null;
        File file = null;
        try {
            is = request.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] b = new byte[1024 * 1024];
            file = new File("..//" + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            // append
            os = new FileOutputStream(file, true);
            int n = 0;
            while ((n = bis.read(b)) > 0) {
                os.write(b, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "SUCCESS";
    }
}
