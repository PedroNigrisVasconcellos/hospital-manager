package br.codenation.hospital.manager.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Hospital {

  private final String id;
  private final String name;
  private final String address;
  private final long beds;
  private final long availableBeds;
}
