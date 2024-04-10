package com.team.saver.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("API 문서")
                .description("잘못된 부분이나 오류 발생 시 말씀해주세요.");
        
        return new OpenAPI()
                .info(info);
    }

}
