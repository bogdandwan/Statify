package org.example.statify.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.statify.model.CountryModel;

import java.util.List;

@Getter
@Setter
public class CountryResponseModel {

    private String get;
    private Object parameters;
    private Object errors;
    private Integer results;
    private List<CountryModel> response;
}
