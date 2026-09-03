package org.example.statify.api.country;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.example.statify.model.CountryModel;

@Getter
@Setter
public class CountryResponseModel {

  private String get;
  private Object parameters;
  private Object errors;
  private Integer results;
  private List<CountryModel> response;
}
