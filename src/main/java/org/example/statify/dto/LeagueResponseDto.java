package org.example.statify.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LeagueResponseDto {

    private LeagueDto league;

    private CountryDto country;

    private List<SeasonsDto> seasons;
}
