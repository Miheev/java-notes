package com.boringcompany.notelist;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ServletInitializerTests {

  @Test
  public void shouldLoadServlets() {
    MatcherAssert.assertThat(new ServletInitializer(), Matchers.notNullValue());
  }
}
