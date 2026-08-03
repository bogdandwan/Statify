package org.example.statify.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagingDto {

    private Integer current;

    private Integer total;
}
