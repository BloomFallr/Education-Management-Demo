package cn.edu.ustc.sscs.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Knife4jConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("选课系统接口文档")
                        .description("这是基于Knife4j OpenApi3的测试接口文档")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("朱东胜")
                                .email("zy937@mail.ustc.edu.cn")));
    }
}
