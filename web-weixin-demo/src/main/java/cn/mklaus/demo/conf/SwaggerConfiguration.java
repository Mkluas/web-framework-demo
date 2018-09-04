package cn.mklaus.demo.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * @author Mklaus
 * @date 2018-07-24 上午10:37
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
//                .protocols(Sets.newHashSet("https"))
                .apiInfo(apiInfo())
                .host("api.mklaus.cn")
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.mklaus.demo.web"))
                .paths(PathSelectors.any())
                .build();

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Web-demo")
                .description("API接口文档 （超级管理员帐号：admin 密码：12345678）")
                .termsOfServiceUrl("http://api.mklaus.cn")
                .version("1.0")
                .build();
    }

}
