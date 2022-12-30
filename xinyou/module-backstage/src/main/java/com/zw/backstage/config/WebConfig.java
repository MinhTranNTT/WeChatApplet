package com.zw.backstage.config;


import com.zw.backstage.interceptor.AuthorizationInterceptor;
import com.zw.common.utils.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebConfig extends WebMvcConfigurationSupport {
    @Autowired
    private UploadFile uploadFile;

    // 注册拦截器
    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;

    // 静态资源映射
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/filePath/**").addResourceLocations("file:"+uploadFile.getImagePath());
        registry.addResourceHandler("/file/**").addResourceLocations("file:"+uploadFile.getFi_p());
        super.addResourceHandlers(registry);
    }

    // 注册拦截器
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
