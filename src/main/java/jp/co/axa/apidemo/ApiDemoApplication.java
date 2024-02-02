package jp.co.axa.apidemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@EnableCaching
@EnableRedisRepositories
public class ApiDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiDemoApplication.class, args);
	}

}
