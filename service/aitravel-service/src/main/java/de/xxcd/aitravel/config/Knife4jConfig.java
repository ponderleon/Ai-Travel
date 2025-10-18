package de.xxcd.aitravel.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Knife4jConfig {

    /**
     * 自定义接口文档信息
     * @return OpenAPI
     */
    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .contact(new Contact()
                                .name("kv:v") // 作者名称
                                .email("aitravel@nv.do"))  // 作者邮箱
                        .title("AiTravel API文档") // 设置标题
                        .version("1.0") // 设置版本
                        .description("AiTravel API接口文档")); // 设置描述
    }
}
