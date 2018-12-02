package org.zechac.atlas.rbac.security;

import com.alibaba.fastjson.JSON;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;
import org.zechac.atlas.common.web.JsonResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 登陆失败后的处理
 * 如果是post请求 返回json格式的登陆失败
 * get forword到相应的页面
 */
public class MAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private final String forwardUrl;

    /**
     * @param forwardUrl
     */
    public MAuthenticationFailureHandler(String forwardUrl) {
        Assert.isTrue(UrlUtils.isValidRedirectUrl(forwardUrl), "'"
                + forwardUrl + "' is not a valid forward URL");
        this.forwardUrl = forwardUrl;
    }

    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if(request.getMethod().toLowerCase().equals("post")){
            response.setContentType("application/json;charset=utf-8");//指定返回的格式为JSON格式
            response.setCharacterEncoding("UTF-8");//setContentType与setCharacterEncoding的顺序不能调换，否则还是无法解决中文乱码的问题
            PrintWriter out =null ;
            out =response.getWriter() ;
            out.write(JSON.toJSONString(JsonResponse.fail().message("登陆失败").getBody()));
            out.close();
        }else {
            request.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, exception);
            request.getRequestDispatcher(forwardUrl).forward(request, response);
        }
    }
}
