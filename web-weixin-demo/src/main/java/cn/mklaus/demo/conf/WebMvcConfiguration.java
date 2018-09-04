package cn.mklaus.demo.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Mklaus
 * @date 2018-07-24 上午10:41
 */
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH")
                .allowCredentials(true).maxAge(3600);
    }

    /**
     * 添加到配置文件生效：cn.mklaus.config.use-default-resource-handler=false
     * @param registry ResourceHandlerRegistry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(wxMpUserInterceptor()).addPathPatterns("/mp/**");
        registry.addInterceptor(wxMaUserTokenInterceptor()).addPathPatterns("/ma/**");
        super.addInterceptors(registry);
    }

    @Bean
    public HandlerInterceptor wxMpUserInterceptor() {
        return new WxMpUserInterceptor();
    }

    @Bean
    public HandlerInterceptor wxMaUserTokenInterceptor() {
        return new WxMaUserTokenInterceptor();
    }

}
