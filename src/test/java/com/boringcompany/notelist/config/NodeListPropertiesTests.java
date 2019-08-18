package com.boringcompany.notelist.config;

import static com.boringcompany.notelist.test.TestUtils.STRING1;
import static com.boringcompany.notelist.test.TestUtils.STRING2;
import static com.boringcompany.notelist.test.TestUtils.STRING3;
import static com.boringcompany.notelist.test.TestUtils.INT;
import static com.boringcompany.notelist.test.TestUtils.assertEqualsAndHash;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pl.pojo.tester.api.assertion.Assertions;

@SpringBootTest
public class NodeListPropertiesTests {
  @Test
  public void shouldPassAppConfigPOJOTest() {
    Assertions.assertPojoMethodsFor(NodeListProperties.class).areWellImplemented();

    assertEqualsAndHash(NodeListProperties.class,
      new Object[] {STRING1, STRING2, STRING3, INT, new NodeListProperties.Swagger(STRING1, STRING2, STRING3)},
      new Class<?>[] { String.class, String.class, String.class, int.class, NodeListProperties.Swagger.class });
  }

  @Test
  public void shouldPassSwaggerConfigPOJOTest() {
    Assertions.assertPojoMethodsFor(NodeListProperties.Swagger.class).areWellImplemented();

    assertEqualsAndHash(NodeListProperties.Swagger.class,
      new Object[] {STRING1, STRING2, STRING3},
      new Class<?>[] {String.class, String.class, String.class});
  }
}
