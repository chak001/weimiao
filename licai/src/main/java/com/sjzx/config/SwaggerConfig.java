package com.sjzx.config;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Slf4j
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        Predicate<RequestHandler> selector1 = RequestHandlerSelectors.basePackage("com.sjzx.controller");
        boolean enable = true;
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(enable)
                .apiInfo(apiInfo())
                .select()
                //为当前包路径
                .apis(Predicates.or(selector1))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("微淼-财务分析后台V1.0版本--基于RESTful风格API接口文档")
                //描述
                .description("微淼-财务分析后API接口服务（API）V1.0版本")
                //创建人
                .contact(new Contact("Horus", "http://localhost:8090/swagger-ui.html", "258712004@qq.com"))
                //版本号
                .version("2.0")
                .build();
    }

}
