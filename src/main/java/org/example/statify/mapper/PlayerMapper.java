package org.example.statify.mapper;

import org.example.statify.dto.player.PlayerDataResponseModel;
import org.example.statify.dto.player.PlayerResponseModel;
import org.example.statify.entity.DBPlayer;
import org.example.statify.model.PlayerModel;
import org.springframework.stereotype.Component;

@Component
public class PlayerMapper {

    public PlayerModel toModel(PlayerResponseModel playerResponseModel) {

        PlayerDataResponseModel data = playerResponseModel.getPlayer();

        PlayerModel model = new PlayerModel();

        model.setId(data.getId());
        model.setName(data.getName());
        model.setFirstname(data.getFirstname());
        model.setLastname(data.getLastname());
        if (data.getBirth() != null) {
            model.setBirthDate(data.getBirth().getDate());
            model.setBirthPlace(data.getBirth().getPlace());
            model.setBirthCountry(data.getBirth().getCountry());
        }
        model.setNationality(data.getNationality());
        model.setHeight(data.getHeight());
        model.setWeight(data.getWeight());
        model.setNumber(data.getNumber());
        model.setPosition(data.getPosition());
        model.setPhoto(data.getPhoto());

        return model;
    }

    public DBPlayer toEntity(PlayerModel model) {

        DBPlayer player = new DBPlayer();

        player.setApiId(model.getId());
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
