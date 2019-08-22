package com.boringcompany.notelist.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "note")
public final class Note implements Serializable {
  @Getter
  @Setter
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  /**
   * Actual name length is 99 = 200 / 2 - 1.
   * Division for reserving space for 2 bytes characters.
   * Subtraction for taking in account the last symbol, which considered empty.
   */
  @Getter
  @Setter
  @SuppressWarnings("checkstyle:magicnumber")
  @Column(length = 200)
  private String name = "";

  @Getter
  @Setter
  @Column(columnDefinition = "TEXT")
  private String description = "";

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || hashCode() != obj.hashCode() || getClass() != obj.getClass()) {
      return false;
    }

    Note note = (Note) obj;
    /*
     * Can be used only ID check due to entity usually distinguished by id
     */
    return id == note.getId()
      && Objects.equals(name, note.getName())
      && Objects.equals(description, note.getDescription());
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description);
  }

  @Override
  public String toString() {
    return String.format("%s[id=%d, name=%s, description=%s]", getClass().getName(), id, name, description);
  }
}
