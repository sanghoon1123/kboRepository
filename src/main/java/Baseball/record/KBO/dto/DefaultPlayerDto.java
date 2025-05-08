package Baseball.record.KBO.dto;

import Baseball.record.KBO.domain.team.TeamName;

import java.time.LocalDate;

public class DefaultPlayerDto extends PlayerDto{
    public DefaultPlayerDto(String name, LocalDate birthDate, int game, TeamName teamName, String playerType) {
        super(name, birthDate, game, teamName, playerType);
    }
}
