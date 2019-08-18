package com.boringcompany.notelist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SuppressWarnings({"checkstyle:FinalClass", "checkstyle:HideUtilityClassConstructor"})
@SpringBootApplication
public class NoteListApplication {
  public static void main(final String[] args) {
    SpringApplication.run(NoteListApplication.class, args);
  }
}
