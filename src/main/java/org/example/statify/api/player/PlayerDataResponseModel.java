package org.example.statify.api.player;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerDataResponseModel {

    private Long id;
    private String name;
    private String firstname;
    private String lastname;
    private BirthResponseModel birth;
    private String nationality;
    private String height;
    private String weight;
    private Integer number;
    private String position;
    private String photo;
}
