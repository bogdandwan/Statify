package org.example.statify.model;

import lombok.Getter;
import lombok.Setter;
import org.example.statify.entity.DBCountry;

@Getter
@Setter
public class CountryModel {

    private Long id;
    private String name;
    private String code;
    private String flag;

    public CountryModel(DBCountry dbCountry) {
        this.id = dbCountry.getId();
        this.name = dbCountry.getName();
        this.code = dbCountry.getCode();
        this.flag = dbCountry.getFlag();
    }
}
