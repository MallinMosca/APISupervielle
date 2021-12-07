package com.mallinmosca.supervielleAPI.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration 
@EnableSwagger2
@EnableWebMvc
public class SwaggerConfig {

            @Bean
            public Docket api() {
                        return new Docket(DocumentationType.SWAGGER_2)
                                    .select()
                                    .apis(RequestHandlerSelectors.basePackage("com.mallinmosca.supervielleAPI"))
                                    .paths(PathSelectors.any())
                                    .build()
                                    .apiInfo(getApiInfo());
            }
            
            private ApiInfo getApiInfo() {
        		return new ApiInfo(
        				"API REST",
        				"API creada en respuesta a una prueba técnica",
        				"1.0",
        				"http://codmind.com/terms",
        				new Contact("Mallin Mosca", "https://github.com/MallinMosca", "mallinmosca@gmail.com"),
        				"LICENSE",
        				"LICENSE URL",
        				Collections.emptyList()
        				);
        	}
   
}
