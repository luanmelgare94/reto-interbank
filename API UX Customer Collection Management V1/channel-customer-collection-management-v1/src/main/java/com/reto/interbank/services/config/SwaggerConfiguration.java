package com.reto.interbank.services.config;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.classmate.types.ResolvedArrayType;
import java.util.ArrayList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.method.HandlerMethod;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.readers.operation.HandlerMethodResolver;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;

@Configuration
@EnableSwagger2WebFlux
public class SwaggerConfiguration {

	public static final Contact DEFAULT_CONTACT = new Contact(
            "Luis Melgarejo Narcizo", "https://www.interbank.com",
			"lmelgarejo94@gmail.com");
	public static final ApiInfo DEFAULT_API_INFO = new ApiInfo(
            "Channel Customer Collection Management V1",
            "Channel Customer Collection Management",
            "1.0.0",
			"PREMIUM",
            DEFAULT_CONTACT,
            "Apache 2.0",
            "http://www.apache.org/licenses/LICENSE-2.0",
			new ArrayList<>());

	@Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
        		.apiInfo(DEFAULT_API_INFO)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
	}
	
	@Bean
    @Primary
    public HandlerMethodResolver fluxMethodResolver(TypeResolver resolver) {
        return new HandlerMethodResolver(resolver) {
            @Override
            public ResolvedType methodReturnType(HandlerMethod handlerMethod) {
                var retType = super.methodReturnType(handlerMethod);


                while (
                        retType.getErasedType() == Mono.class
                        || retType.getErasedType() == Flux.class
                        || retType.getErasedType() == ResponseEntity.class
                ) {
                    if ( retType.getErasedType() == Flux.class ) {

                        var type = retType.getTypeBindings().getBoundType(0);
                        retType = new ResolvedArrayType(type.getErasedType(),
                                type.getTypeBindings(), type);
                    } else {
                        retType = retType.getTypeBindings().getBoundType(0);
                    }
                }

                return retType;
            }
        };
	}
	
}
