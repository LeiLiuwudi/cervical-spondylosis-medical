package com.zju.edu.gcs.filter;

import com.zju.edu.gcs.common.exception.NirException;
import com.zju.edu.gcs.common.exception.NirExceptionEnum;
import com.zju.edu.gcs.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(urlPatterns = {"/patient/queryPatien"})
public class TokenFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        Result<Void> result = new Result<>();
//        result.setCode(e.getCode());
//        result.setMsg(e.getMessage());
//        servletResponse.setHeader("content-type", "application/json;charset=utf-8");
//        servletResponse.setHeader("Access-Control-Allow-Origin","*");
//        servletResponse.setHeader("Access-Control-Allow-Methods","POST,OPTIONS");
//        httpServletResponse.setHeader("Access-Control-Allow-Credentials","true");
//        httpServletResponse.setHeader("Access-Control-Allow-Headers","Content-Type,WithCredentials,Authorization");
//        PrintWriter out = httpServletResponse.getWriter();
//        out.println(JSON.toJSONString(result));
//        throw new NirException(NirExceptionEnum.USER_UNLOGIN);
    }

    @Override
    public void destroy() {

    }
}

