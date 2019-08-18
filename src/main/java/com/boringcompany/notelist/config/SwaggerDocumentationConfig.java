package com.boringcompany.notelist.config;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@EnableSwagger2
@Configuration
public class SwaggerDocumentationConfig {
  public static final String NOTE_LIST = "NoteList";

  private static final String ROOT_PATH = "/";
  private static final String DESCRIPTION_FILE = "api.md";

  private final NodeListProperties appConfig;

  @SuppressWarnings("checkstyle:DesignForExtension")
  @Bean
  public Docket api() throws IOException {
    return new Docket(DocumentationType.SWAGGER_2)
      .useDefaultResponseMessages(false)
      .select()
      .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
      .paths(PathSelectors.any())
//      .paths(PathSelectors.ant(ROOT_PATH))
      .build()
      .apiInfo(apiInfo());
  }

  private ApiInfo apiInfo() throws IOException {
    try (InputStream inputStream = (new ClassPathResource(DESCRIPTION_FILE)).getInputStream()) {
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
      String description = reader.lines().collect(Collectors.joining(System.lineSeparator()));

      return new ApiInfoBuilder()
        .title(appConfig.getTitle())
        .description(description)
        .license(appConfig.getLicense())
        .version(appConfig.getVersion())
        .build();
    }
  }
}
