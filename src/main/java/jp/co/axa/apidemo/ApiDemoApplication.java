package jp.co.axa.apidemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@EnableCaching
@EnableRedisRepositories
@RestController
public class ApiDemoApplication {

	private static ConfigurableApplicationContext context;

	public static void main(String[] args) {

		context = SpringApplication.run(ApiDemoApplication.class, args);

	}

	@PostMapping("/shutdown")
	public void shutdown() {
		if (context != null) {

			SpringApplication.exit(context);

		}

	}

}
