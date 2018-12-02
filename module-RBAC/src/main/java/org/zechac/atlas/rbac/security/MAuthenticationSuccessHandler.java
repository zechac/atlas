package org.zechac.atlas.rbac.security;

import com.alibaba.fastjson.JSON;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;
import org.zechac.atlas.common.web.JsonResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final String forwardUrl;

    /**
     * @param forwardUrl
     */
    public MAuthenticationSuccessHandler(String forwardUrl) {
        Assert.isTrue(UrlUtils.isValidRedirectUrl(forwardUrl), "'"
                + forwardUrl + "' is not a valid forward URL");
        this.forwardUrl = forwardUrl;
    }

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if(request.getMethod().toLowerCase().equals("post")){
            response.setContentType("application/json;charset=utf-8");//指定返回的格式为JSON格式
            response.setCharacterEncoding("UTF-8");//setContentType与setCharacterEncoding的顺序不能调换，否则还是无法解决中文乱码的问题
            PrintWriter out =null ;
            out =response.getWriter() ;
            out.write(JSON.toJSONString(JsonResponse.success().message("登陆成功").getBody()));
            out.close();
            //response.getOutputStream().write();
        }else {
            request.getRequestDispatcher(forwardUrl).forward(request, response);
        }
    }
}
