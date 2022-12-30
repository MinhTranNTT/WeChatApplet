package com.zw.backstage.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.zw.common.utils.TokenUtil;
import com.zw.common.utils.annotation.Authorize;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Slf4j
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {
    @Autowired
    TokenUtil tokenUtil;

    // 拦截器，请求之前的 preHandler
    @Override
    public  boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) {
        // 如果不是映射到方法，直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();

        // 检查有没有需要用户权限的注解
        // 如果有注解 Authorize ，则需要验证token
        if(method.isAnnotationPresent(Authorize.class)){
            Authorize userLoinToken = method.getAnnotation(Authorize.class);
            if (userLoinToken.required()){
                // 从请求头中获取token
                String token = httpServletRequest.getHeader("Authorization");
                // 执行认证
                if(token == null || token.isEmpty()){
                    log.info("token为空");
                    httpServletResponse.setStatus(700);
                    return false;
                }
                // 验证 token
                try{
                    String rawToken=tokenUtil.getTokenClaim(token).getSubject().toString();
                    JSONObject jsonObject = JSONObject.parseObject(rawToken);
                    String openid=jsonObject.getString("openid");
                    // 添加token到request参数，方便后续获取
                    httpServletRequest.setAttribute("openid",openid);
                    return true;
                }catch (Exception e){
                    log.info("token验证失败");
                    httpServletResponse.setStatus(701);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
