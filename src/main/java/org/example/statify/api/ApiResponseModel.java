package org.example.statify.api;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.example.statify.model.PagingModel;

@Getter
@Setter
public class ApiResponseModel<T> {

  private String get;
  private Object parameters;
  private Object errors;
  private Integer results;
  private PagingModel paging;
  private List<T> response;
}
