package org.example.statify.api.player;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BirthResponseModel {

  private LocalDate date;
  private String place;
  private String country;
}
