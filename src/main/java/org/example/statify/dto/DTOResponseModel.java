package org.example.statify.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.statify.model.PagingModel;

import java.util.List;

@Getter
@Setter
public class DTOResponseModel<T> {

    private String get;
    private Object parameters;
    private Object errors;
    private Integer results;
    private PagingModel paging;
    private List<T> response;
}
