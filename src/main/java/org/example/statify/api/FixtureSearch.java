package org.example.statify.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain=true)
@RequiredArgsConstructor
public class FixtureSearch {
    private final Long leagueId;
    private final Integer seasonYear;
    private Boolean current;
    /**
     * If this parameter is true api return fixture dates in response
     **/
    private Boolean dates = Boolean.TRUE;
}
