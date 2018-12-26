package org.zechac.atlas.rbac.security;

import com.alibaba.fastjson.JSON;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.zechac.atlas.common.web.JsonResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by hua on 2017/1/12.
 */
public class PostAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private String loginFormUrl;
    /**
     * @param loginFormUrl URL where the login page can be found. Should either be
     *                     relative to the web-app context path (include a leading {@code /}) or an absolute
     *                     URL.
     */
    public PostAuthenticationEntryPoint(String loginFormUrl) {
        this.loginFormUrl=loginFormUrl;
    }

    /**
     * Performs the redirect (or forward) to the login form URL.
     */
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        if(request.getMethod().toLowerCase().equals("post")){
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            JsonResponse responseParam=JsonResponse.fail();
            responseParam.message(authException.getMessage());
            //if
            PrintWriter out = null;
            try {
                out = response.getWriter();
                out.write(JSON.toJSONString(responseParam.getBody()));
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    out.close();
                }
            }
            return;
        }
        response.sendRedirect(loginFormUrl);
    }
}
