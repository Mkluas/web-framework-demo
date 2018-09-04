package cn.mklaus.demo.conf;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.reflect.WildcardType;

import static springfox.documentation.schema.AlternateTypeRules.newRule;

/**
 * @author Mklaus
 * @date 2018-07-24 上午10:37
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Autowired
    private TypeResolver typeResolver;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
//                .protocols(Sets.newHashSet("https"))
                .apiInfo(apiInfo())
                .host("api.mklaus.cn")
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.mklaus.demo.web"))
                .paths(PathSelectors.any())
                .build()
                .alternateTypeRules( //自定义规则，如果遇到DeferredResult，则把泛型类转成json
                        newRule(typeResolver.resolve(DeferredResult.class,
                                typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
                                typeResolver.resolve(WildcardType.class)))
                ;

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
