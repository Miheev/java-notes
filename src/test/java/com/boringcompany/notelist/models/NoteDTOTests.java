package com.boringcompany.notelist.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.pojo.tester.api.assertion.Assertions;

import static com.boringcompany.notelist.test.TestUtils.NOT_EXISTING_ENTITY_ID;
import static com.boringcompany.notelist.test.TestUtils.STRING1;
import static com.boringcompany.notelist.test.TestUtils.STRING2;
import static com.boringcompany.notelist.test.TestUtils.assertEqualsAndHash;

@ExtendWith(SpringExtension.class)
public class NoteDTOTests {

  @Test
  public void shouldPassPOJOTests() {
    Assertions.assertPojoMethodsFor(NoteDTO.class)
      .create(NoteDTO.class, new Object[] {}, new Class<?>[] {})
      .areWellImplemented();

    assertEqualsAndHash(NoteDTO.class,
      new Object[] {NOT_EXISTING_ENTITY_ID, STRING1, STRING2},
      new Class<?>[] {long.class, String.class, String.class});
  }
}
