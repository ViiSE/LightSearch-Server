/*
 *  Copyright 2019 ViiSE.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package lightsearch.server.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
@EnableWebMvc
@PropertySource("classpath:swagger.properties")
public class SwaggerConfiguration {

    @Bean
    public Docket adminsAPI() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("admins")
                .apiInfo(adminApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("lightsearch.server.controller.admin"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket clientsAPI() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("clients")
                .apiInfo(clientApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("lightsearch.server.controller.client"))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(Collections.singletonList(apiKeyAuthScheme()));
    }

    private static SecurityScheme apiKeyAuthScheme() {
        return new ApiKey("Bearer", "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth())
                .forPaths(PathSelectors.any()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope(
                "global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(new SecurityReference("Bearer",
                authorizationScopes));
    }

    @Bean
    public SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder().scopeSeparator(",")
                .additionalQueryStringParams(null)
                .useBasicAuthenticationWithAccessCodeGrant(false).build();
    }

    private ApiInfo adminApiInfo() {
        return new ApiInfoBuilder()
                .title("LightSearch")
                .description("Добро пожаловать в документацию LightSearch! Для доступа к API необходимо " +
                        "авторизоваться в LightSearch как администратор.")
                .license("Apache License v2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0.txt")
                .version("3.0.0")
                .build();
    }

    private ApiInfo clientApiInfo() {
        return new ApiInfoBuilder()
                .title("LightSearch")
                .description("Добро пожаловать в документацию LightSearch!")
                .license("Apache License v2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0.txt")
                .version("3.0.0")
                .build();
    }
}
