package com.mallinmosca.supervielleAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import com.mallinmosca.supervielleAPI.config.SwaggerConfig;

@SpringBootApplication
@Import(SwaggerConfig.class)
public class SupervielleApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SupervielleApiApplication.class, args);
	}
	
	
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
 
           registry.addResourceHandler("swagger-ui.html")
                   .addResourceLocations("classpath:/META-INF/resources/");
 
    }

}
