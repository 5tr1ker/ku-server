package com.team.saver.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class UtilConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public OpenAPI swaggerTemplate() {
        Info info = new Info()
                .title("API 문서")
                .description("잘못된 부분이나 오류 발생 시 말씀해주세요.");

        return new OpenAPI()
                .info(info);
    }
}
