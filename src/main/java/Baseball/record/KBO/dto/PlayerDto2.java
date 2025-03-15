package Baseball.record.KBO.dto;

import Baseball.record.KBO.domain.player.Player;
import Baseball.record.KBO.domain.team.TeamName;
import lombok.Getter;

@Getter
public class PlayerDto2 {
    private String name;
    private int age;
    private int game;
    private TeamName teamName;
    private String playerType;

    public PlayerDto2(Player player) {
        name = player.getName();
        age = player.getAge();
        game = player.getGame();
        teamName = player.getTeam().getName();
        playerType = player.getPlayerType();
    }
}
