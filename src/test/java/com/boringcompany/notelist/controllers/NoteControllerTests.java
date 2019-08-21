package com.boringcompany.notelist.controllers;

import com.boringcompany.notelist.models.NoteDTO;
import com.boringcompany.notelist.test.ErrorDTO;
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

/**
 * In the UT scenario MockMvc should be used
 * https://developer.okta.com/blog/2019/03/28/test-java-spring-boot-junit5
 *
 * @AutoConfigureMockMvc
 * @ContextConfiguration(classes = {NoteRepository.class, NoteService.class, TestRestTemplate.class})
 * @WebMvcTest
 *
 *
 *  TestRestTemplate as (IT test tool) can be used with @SpringBootTest only
 *  https://stackoverflow.com/questions/39213531/spring-boot-test-unable-to-inject-testresttemplate-and-mockmvc
 */
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

    ResponseEntity<NoteDTO> response = template.exchange(NoteController.ENDPOINT, HttpMethod.POST, entity, NoteDTO.class);

    Assertions.assertNotNull(response.getBody());
    Assertions.assertTrue(response.getBody().getId() > 0);
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

    noteService.deleteNote(response.getBody().getId());
  }

  @Test
  public void shouldGetNote() {
    HttpEntity<Object> entity = TestUtils.getHttpEntity(null);
    Note noteSample = saveNote();

    ResponseEntity<NoteDTO> response = template.exchange(NoteController.ENDPOINT_ENTITY, HttpMethod.GET, entity,
      NoteDTO.class, noteSample.getId());

    Assertions.assertNotNull(response.getBody());
    Assertions.assertEquals(response.getBody().getId(), noteSample.getId());
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

    noteService.deleteNote(noteSample.getId());
  }

  @Test
  public void shouldNoteNotFoundOnGet() {
    HttpEntity<Object> entity = TestUtils.getHttpEntity(null);

    ResponseEntity<ErrorDTO> response = template.exchange(NoteController.ENDPOINT_ENTITY, HttpMethod.GET, entity,
      ErrorDTO.class, TestUtils.NOT_EXISTING_ENTITY_ID);

    Assertions.assertNotNull(response.getBody());
    Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  public void shouldUpdateNode() {
    HttpEntity<Object> entity = noteUpdateRequest();
    Note noteSample = saveNote();

    ResponseEntity<NoteDTO> response = template.exchange(NoteController.ENDPOINT_ENTITY, HttpMethod.PUT, entity,
      NoteDTO.class, noteSample.getId());

    Assertions.assertNotNull(response.getBody());
    Assertions.assertEquals(response.getBody().getId(), noteSample.getId());
    Assertions.assertEquals(response.getBody().getName(), noteSample.getName() + SUFFIX);
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

    noteService.deleteNote(noteSample.getId());
  }

  @Test
  public void shouldNoteNotFoundOnUpdate() {
    HttpEntity<Object> entity = noteUpdateRequest();

    ResponseEntity<ErrorDTO> response = template.exchange(NoteController.ENDPOINT_ENTITY, HttpMethod.PUT, entity,
      ErrorDTO.class, TestUtils.NOT_EXISTING_ENTITY_ID);

    Assertions.assertNotNull(response.getBody());
    Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  public void shouldDeleteNode() {
    HttpEntity<Object> entity = TestUtils.getHttpEntity(null);
    Note noteSample = saveNote();

    ResponseEntity<NoteDTO> response = template.exchange(NoteController.ENDPOINT_ENTITY, HttpMethod.DELETE, entity,
      NoteDTO.class, noteSample.getId());

    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

    /*
      Cover note not exist case
     */
    response = template.exchange(NoteController.ENDPOINT_ENTITY, HttpMethod.DELETE, entity,
      NoteDTO.class, noteSample.getId());

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
