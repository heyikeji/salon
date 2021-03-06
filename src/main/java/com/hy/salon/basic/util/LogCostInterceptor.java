package com.hy.salon.basic.util;

import com.alibaba.fastjson.JSONObject;
import com.hy.salon.basic.common.StatusUtil;
import com.zhxh.admin.entity.SystemUser;
import com.zhxh.admin.service.AuthenticateService;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogCostInterceptor implements HandlerInterceptor {

    @Resource(name = "authenticateService")
    private AuthenticateService authenticateService;

    long start = System.currentTimeMillis();
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse response, Object o) throws Exception {

       String method= httpServletRequest.getMethod();

        SystemUser user = authenticateService.getCurrentLogin();
        if(null == user){
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");
            JSONObject jsonObj = new JSONObject();
         if(method.equals("GET")){
             jsonObj.put("msgcode", "70010");
             jsonObj.put("msg", "登录超时");
             jsonObj.put("data", "请重新登录");
             response.getWriter().write(jsonObj.toJSONString());
             return false;
         }else if(method.equals("POST")){
             JSONObject jsonObj2 = new JSONObject();
             jsonObj.put("msgcode", "70010");
             jsonObj.put("msg", "登录超时");
             jsonObj.put("data", "请重新登录");
             jsonObj2.put("data",jsonObj);
             response.getWriter().write(jsonObj2.toJSONString());
             return false;
         }

        }

        return true;


    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        System.out.println("Interceptor cost="+(System.currentTimeMillis()-start));
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
