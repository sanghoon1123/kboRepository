package Baseball.record.KBO.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("KBO 선수 기록 API")
                        .description("KBO 선수들의 기록을 조회하는 API 문서")
                        .version("1.0"));
    }
}
