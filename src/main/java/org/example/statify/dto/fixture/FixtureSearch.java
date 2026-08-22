package org.example.statify.dto.fixture;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Getter
@Setter
@Accessors(chain=true)
@RequiredArgsConstructor
public class FixtureSearch {
    private final Long leagueId;
    private final Integer seasonYear;
    private Boolean current;
    /**
     * If this parameter is ture api return fixture dates in response
     * */
    private Boolean dates = Boolean.TRUE;
}
