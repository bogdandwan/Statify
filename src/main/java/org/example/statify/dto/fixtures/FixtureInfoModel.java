package org.example.statify.dto.fixtures;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class FixtureInfoModel {

    private Long id;
    private String referee;
    private OffsetDateTime date;
    private FixtureStatusModel status;
}
