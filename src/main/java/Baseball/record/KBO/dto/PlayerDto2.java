package Baseball.record.KBO.dto;

import Baseball.record.KBO.domain.player.Player;
import Baseball.record.KBO.domain.team.TeamName;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

// PlayerDto2.java
@Getter
@Setter
public class PlayerDto2 {
    private String name;
    private LocalDate birthDate;
    private int game;
    private TeamName teamName;
    private String playerType;

    public PlayerDto2() {
    }

    public PlayerDto2(String name) {
        this.name = name;
    }

    // 기존: Entity → DTO
    public PlayerDto2(Player player) {
        name = player.getName();
        birthDate = player.getBirthDate();
        game = player.getGame();
        teamName = player.getTeam().getName();
        playerType = player.getPlayerType();
    }

    @Override
    public String toString() {
        return "PlayerDto2{" +
                "name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", game=" + game +
                ", teamName=" + teamName +
                ", playerType='" + playerType + '\'' +
                '}';
    }
}

