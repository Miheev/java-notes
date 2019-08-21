package com.boringcompany.notelist;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class ServletInitializerTests {

  @Test
  public void shouldLoadServlets() {
    MatcherAssert.assertThat(new ServletInitializer(), Matchers.notNullValue());
  }
}
