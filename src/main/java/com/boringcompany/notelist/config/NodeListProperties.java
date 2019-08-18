package com.boringcompany.notelist.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ConfigurationProperties("boringcompany.notelist")
@AllArgsConstructor
@NoArgsConstructor
@Data
public final class NodeListProperties {
  private String title;
  private String license;
  private String version;
  private int findAllLimit;

  private Swagger swagger;

  public Pageable getLimitedPageable() {
    return PageRequest.of(0, getFindAllLimit());
  }

  @AllArgsConstructor
  @NoArgsConstructor
  @Data
  public static final class Swagger {
    private String uiPath;
    private String resourcesPath1;
    private String resourcesPath2;
  }
}
