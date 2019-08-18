package com.boringcompany.notelist.controllers;

import com.boringcompany.notelist.models.Note;
import com.boringcompany.notelist.services.NoteService;
import com.boringcompany.notelist.test.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NoteControllerTests {
  private static final String COMMON_NAME = "test note";
  private static final String COMMON_DESCRIPTION = "test description";
  private static final String SUFFIX = "222";
  private static final Note NOTE_SAMPLE = new Note(TestUtils.DEFAULT_ENTITY_ID, COMMON_NAME, COMMON_DESCRIPTION);

  private MockMvc mockMvc;

  @Autowired
  private TestRestTemplate template;

  @Autowired
  private NoteService noteService;

  @Mock
  private NoteController noteController;

  @BeforeEach
  public void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(noteController).build();
  }

  @Test
  public void shouldGetAllNotes() {
    HttpEntity<Object> entity = TestUtils.getHttpEntity(null);

    ResponseEntity<Object> response = template.exchange(NoteController.ENDPOINT, HttpMethod.GET, entity, Object.class);

    Assertions.assertTrue(response.getBody() instanceof List);
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void shouldCreateNote() {
    HttpEntity<Object> entity = noteCreateRequest();

    ResponseEntity<Note> response = template.exchange(NoteController.ENDPOINT, HttpMethod.POST, entity, Note.class);

    Assertions.assertNotNull(response.getBody());
    Assertions.assertTrue(response.getBody().getId() > 0);
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void shouldGetNote() {
    HttpEntity<Object> entity = TestUtils.getHttpEntity(null);
    Note noteSample = saveNote();

    ResponseEntity<Note> response = template.exchange(NoteController.ENDPOINT_ENTITY, HttpMethod.GET, entity,
      Note.class, noteSample.getId());

    Assertions.assertNotNull(response.getBody());
    Assertions.assertEquals(response.getBody().getId(), noteSample.getId());
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void shouldNoteNotFoundOnGet() {
    HttpEntity<Object> entity = TestUtils.getHttpEntity(null);

    ResponseEntity<Note> response = template.exchange(NoteController.ENDPOINT_ENTITY, HttpMethod.GET, entity,
      Note.class, TestUtils.NOT_EXISTING_ENTITY_ID);

    Assertions.assertNull(response.getBody());
    Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  public void shouldUpdateNode() {
    HttpEntity<Object> entity = noteUpdateRequest();
    Note noteSample = saveNote();

    ResponseEntity<Note> response = template.exchange(NoteController.ENDPOINT_ENTITY, HttpMethod.PUT, entity,
      Note.class, noteSample.getId());

    Assertions.assertNotNull(response.getBody());
    Assertions.assertEquals(response.getBody().getId(), noteSample.getId());
    Assertions.assertEquals(response.getBody().getName(), noteSample.getName() + SUFFIX);
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void shouldNoteNotFoundOnUpdate() {
    HttpEntity<Object> entity = noteUpdateRequest();

    ResponseEntity<Note> response = template.exchange(NoteController.ENDPOINT_ENTITY, HttpMethod.PUT, entity,
      Note.class, TestUtils.NOT_EXISTING_ENTITY_ID);

    Assertions.assertNull(response.getBody());
    Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  public void shouldDeleteNode() {
    HttpEntity<Object> entity = TestUtils.getHttpEntity(null);
    Note noteSample = saveNote();

    ResponseEntity<Note> response = template.exchange(NoteController.ENDPOINT_ENTITY, HttpMethod.DELETE, entity,
      Note.class, noteSample.getId());

    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

    /*
      Cover note not exist case
     */
    response = template.exchange(NoteController.ENDPOINT_ENTITY, HttpMethod.DELETE, entity,
      Note.class, noteSample.getId());

    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  private Note saveNote() {
    return noteService.createNote(NOTE_SAMPLE);
  }

  private HttpEntity<Object> noteCreateRequest() {
    return TestUtils.getHttpEntity(String.join("",
      "{\"name\": \"", COMMON_NAME, "\", \"description\": \"", COMMON_DESCRIPTION, "\"}")
    );
  }

  private HttpEntity<Object> noteUpdateRequest() {
    return TestUtils.getHttpEntity(String.join("",
      "{\"name\": \"", COMMON_NAME, SUFFIX, "\", \"description\": \"", COMMON_DESCRIPTION, SUFFIX, "\"}")
    );
  }
}
