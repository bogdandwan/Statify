package org.example.statify.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.example.statify.model.TeamModel;

@Entity
@Getter
@Setter
@Table(name = "teams")
@Accessors(chain = true)
public class DBTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "api_id", unique = true, nullable = false)
    private Integer apiId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "country")
    private String country;

    @Column(name = "founded")
    private Integer founded;

    @Column(name = "national")
    private Boolean national;

    @Column(name = "logo")
    private String logo;

    public static DBTeam fromTeamIdOnly(TeamModel teamModel) {
        return new  DBTeam()
                .setId(teamModel.getId());
    }
}
