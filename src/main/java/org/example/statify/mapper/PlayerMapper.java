package org.example.statify.mapper;

import org.example.statify.api.player.PlayerDataResponseModel;
import org.example.statify.api.player.PlayerResponseModel;
import org.example.statify.entity.DBPlayer;
import org.example.statify.model.PlayerModel;
import org.springframework.stereotype.Component;

@Component
public class PlayerMapper {

  public PlayerModel toModel(PlayerResponseModel playerResponseModel) {

    PlayerDataResponseModel responseModel = playerResponseModel.getPlayer();

    PlayerModel model = new PlayerModel();

    model.setApiId(responseModel.getId().intValue());
    model.setName(responseModel.getName());
    model.setFirstname(responseModel.getFirstname());
    model.setLastname(responseModel.getLastname());
    if (responseModel.getBirth() != null) {
      model.setBirthDate(responseModel.getBirth().getDate());
      model.setBirthPlace(responseModel.getBirth().getPlace());
      model.setBirthCountry(responseModel.getBirth().getCountry());
    }
    model.setNationality(responseModel.getNationality());
    model.setHeight(responseModel.getHeight());
    model.setWeight(responseModel.getWeight());
    model.setNumber(responseModel.getNumber());
    model.setPosition(responseModel.getPosition());
    model.setPhoto(responseModel.getPhoto());

    return model;
  }

  public DBPlayer toEntity(PlayerModel model) {

    DBPlayer player = new DBPlayer();

    player.setApiId(model.getApiId());
    player.setName(model.getName());
    player.setFirstname(model.getFirstname());
    player.setLastname(model.getLastname());
    player.setBirthDate(model.getBirthDate());
    player.setBirthPlace(model.getBirthPlace());
    player.setBirthCountry(model.getBirthCountry());
    player.setNationality(model.getNationality());
    player.setHeight(model.getHeight());
    player.setWeight(model.getWeight());
    player.setNumber(model.getNumber());
    player.setPosition(model.getPosition());
    player.setPhoto(model.getPhoto());

    return player;
  }
}
