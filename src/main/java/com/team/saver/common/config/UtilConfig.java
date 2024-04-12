package com.team.saver.common.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class UtilConfig {

    // RestTemplate
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // Swagger
    @Bean
    public OpenAPI swaggerTemplate() {
        Info info = new Info()
                .title("API 문서")
                .description("잘못된 부분이나 오류 발생 시 말씀해주세요.");

        return new OpenAPI()
                .info(info);
    }

    // QueryDSL
    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory(){
        return new JPAQueryFactory(entityManager);
    }

}
