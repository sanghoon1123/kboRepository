package Baseball.record.KBO.dto;

import Baseball.record.KBO.domain.team.TeamName;

public class DefaultPlayerDto extends PlayerDto{
    public DefaultPlayerDto(String name, int age, int game, TeamName teamName, String playerType) {
        super(name, age, game, teamName, playerType);
    }
}
