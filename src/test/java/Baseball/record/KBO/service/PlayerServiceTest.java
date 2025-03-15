package Baseball.record.KBO.service;

import Baseball.record.KBO.domain.player.Pitcher;
import Baseball.record.KBO.domain.player.PitcherPosition;
import Baseball.record.KBO.domain.player.Player;
import Baseball.record.KBO.domain.team.Team;
import Baseball.record.KBO.domain.team.TeamName;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
/*
@Transactional
@SpringBootTest
class PlayerServiceTest {
    @Autowired TeamService teamService;
    @Autowired PlayerService playerService;

    @Test
    void savePlayer(){
        Team kia =  new Team(TeamName.KIA);
        teamService.save(kia);

        Player yang = playerService.save(new Pitcher("양현종", 37, kia, 10, 5, 3.27, 103,
                0, 0, PitcherPosition.STARTING));

        Assertions.assertThat(yang).isNotNull(); // 저장된 객체가 null이 아님을 확인
        Assertions.assertThat((yang).getName()).isEqualTo("양현종"); // 이름 검증
        Assertions.assertThat(yang.getAge()).isEqualTo(37); // 나이 검증
        Assertions.assertThat(yang.getTeam().getName()).isEqualTo(TeamName.KIA); // 팀 검증

        Assertions.assertThat(yang).isInstanceOf(Pitcher.class);
        Pitcher pitcher = (Pitcher) yang;
        Assertions.assertThat(pitcher.getWin()).isEqualTo(10);
        Assertions.assertThat(pitcher.getEra()).isEqualTo(3.27);
        Assertions.assertThat(pitcher.getStrikeouts()).isEqualTo(103);
        Assertions.assertThat(pitcher.getPitcherPosition()).isEqualTo(PitcherPosition.STARTING);

        Player foundPlayer = playerService.findOne(yang.getId());
        Assertions.assertThat(foundPlayer).isNotNull();
        Assertions.assertThat(foundPlayer.getName()).isEqualTo("양현종");
    }
}

 */