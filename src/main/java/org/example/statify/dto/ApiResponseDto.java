package org.example.statify.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ApiResponseDto<T> {

    private String get;

    private Object parameters;

    private Object errors;

    private Integer results;

    private PagingDto paging;

    private List<T> response;
}
