package org.example.statify.search;

import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PlayerSearch {

  private Long id;
  private Long apiId;

  private String name;
  private String firstname;
  private String lastname;

  private LocalDate birthDateFrom;
  private LocalDate birthDateTo;

  private String birthPlace;
  private String birthCountry;
  private String nationality;

  private String position;
  private Integer number;
}
