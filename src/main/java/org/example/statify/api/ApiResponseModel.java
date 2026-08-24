package org.example.statify.api;

import lombok.Getter;
import lombok.Setter;
import org.example.statify.model.PagingModel;

import java.util.List;

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
