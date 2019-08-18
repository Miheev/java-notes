package com.boringcompany.notelist;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NoteListApplicationTests {

  @Test
  public void shouldApplicationLoad() {
    MatcherAssert.assertThat(new NoteListApplication(), Matchers.notNullValue());
  }
}
