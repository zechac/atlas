package org.zechac.atlas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class AtlasApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(AtlasApplication.class);
        springApplication.run(args);
    }
}
