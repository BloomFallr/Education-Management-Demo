package cn.edu.ustc.sscs.config;

import cn.edu.ustc.sscs.interceptor.AdmLoginCheckInterceptor;
import cn.edu.ustc.sscs.interceptor.StuLoginCheckInterceptor;
import cn.edu.ustc.sscs.interceptor.TeaLoginCheckInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class WebConfig implements WebMvcConfigurer {

    private final AdmLoginCheckInterceptor admLoginCheckInterceptor;
    private final StuLoginCheckInterceptor stuLoginCheckInterceptor;
    private final TeaLoginCheckInterceptor teaLoginCheckInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(admLoginCheckInterceptor)
                .addPathPatterns("/adm/**").excludePathPatterns("/adm/login");
        registry.addInterceptor(stuLoginCheckInterceptor)
                .addPathPatterns("/stu/**").excludePathPatterns("/stu/login");
        registry.addInterceptor(teaLoginCheckInterceptor)
                .addPathPatterns("/tea/**").excludePathPatterns("/tea/login");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/static/favicon.ico");
    }
}
