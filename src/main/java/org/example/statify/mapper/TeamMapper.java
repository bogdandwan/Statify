package org.example.statify.mapper;

import org.example.statify.api.team.TeamApiResponseModel;
import org.example.statify.entity.DBTeam;
import org.example.statify.model.TeamModel;
import org.springframework.stereotype.Component;

@Component
public class TeamMapper {

  public TeamModel toModel(TeamApiResponseModel response) {

    TeamModel model = new TeamModel();

    model.setApiId(response.getTeam().getId().intValue());
    model.setName(response.getTeam().getName());
    model.setCode(response.getTeam().getCode());
    model.setCountry(response.getTeam().getCountry());
    model.setFounded(response.getTeam().getFounded());
    model.setNational(response.getTeam().getNational());
    model.setLogo(response.getTeam().getLogo());

    return model;
  }

  public DBTeam toEntity(TeamModel model) {

    DBTeam entity = new DBTeam();

    entity.setApiId(model.getApiId());
    entity.setName(model.getName());
    entity.setCode(model.getCode());
    entity.setCountry(model.getCountry());
    entity.setFounded(model.getFounded());
    entity.setNational(model.getNational());
    entity.setLogo(model.getLogo());

    return entity;
  }
}
