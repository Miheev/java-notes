package com.boringcompany.notelist.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NoteDTO {
  private long id;
  private String name;
  private String description;

  public NoteDTO(final Note note) {
    id = note.getId();
    name = note.getName();
    description = note.getDescription();
  }
}
