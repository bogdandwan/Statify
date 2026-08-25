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

    private Integer id;
    /**
     * ids - You can get more fixtures(20 max) in only one API call
     **/
    private String ids;
    private String live;
    private String date;
    private Integer league;
    private Integer season;
    private Integer team;
    private Integer last;
    private Integer next;
    private String from;
    private String to;
    private String round;
    private String status;
    private Integer venue;
    private String timezone;



    /*private final Long leagueId;
    private final Integer seasonYear;
    private Boolean current;
    *//**
     * If this parameter is true api return fixture dates in response
     **//*
    private Boolean dates = Boolean.TRUE;*/
}
