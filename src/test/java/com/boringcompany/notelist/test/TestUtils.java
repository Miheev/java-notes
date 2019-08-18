package com.boringcompany.notelist.test;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import pl.pojo.tester.api.assertion.Assertions;
import pl.pojo.tester.api.assertion.Method;

public class TestUtils {
  public static final String STRING1 = "1";
  public static final String STRING2 = "2";
  public static final String STRING3 = "3";
  public static final int INT = 4;

  public static final long NOT_EXISTING_ENTITY_ID = 5000L;
  public static final long DEFAULT_ENTITY_ID = 0L;

  public static HttpEntity<Object> getHttpEntity(final Object body) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return new HttpEntity<>(body, headers);
  }

  public static void assertEqualsAndHash(final Class className, final Object[] params, final Class[] paramTypes) {
    Assertions.assertPojoMethodsFor(className)
      .testing(Method.EQUALS, Method.HASH_CODE)
      .create(className, params, paramTypes)
      .areWellImplemented();
  }
}
