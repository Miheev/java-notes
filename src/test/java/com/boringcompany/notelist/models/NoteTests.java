package com.boringcompany.notelist.models;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pl.pojo.tester.api.assertion.Assertions;

import static com.boringcompany.notelist.test.TestUtils.NOT_EXISTING_ENTITY_ID;
import static com.boringcompany.notelist.test.TestUtils.assertEqualsAndHash;

@SpringBootTest
public class NoteTests {
  @Test
  public void shouldPassPOJOTests() {
    Assertions.assertPojoMethodsFor(Note.class)
      .create(Note.class, new Object[] {}, new Class<?>[] {})
      .areWellImplemented();

    assertEqualsAndHash(Note.class,
      new Object[] {NOT_EXISTING_ENTITY_ID, null, null},
      new Class<?>[] {long.class, String.class, String.class});
  }
}
