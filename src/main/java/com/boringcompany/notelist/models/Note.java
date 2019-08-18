package com.boringcompany.notelist.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "note")
public final class Note implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  /**
   * Actual name length is 99 = 200 / 2 - 1.
   * Division for reserving space for 2 bytes characters.
   * Subtraction for taking in account the last symbol, which considered empty.
   */
  @SuppressWarnings("checkstyle:magicnumber")
  @Column(length = 200)
  private String name = "";

  @Column(columnDefinition = "TEXT")
  private String description = "";
}
