package com.somn.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Base class with, property ID.
 *
 * @author evakule
 * @version 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
}
