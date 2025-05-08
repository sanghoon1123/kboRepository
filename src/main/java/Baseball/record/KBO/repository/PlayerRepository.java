package Baseball.record.KBO.repository;

import Baseball.record.KBO.domain.player.Batter;
import Baseball.record.KBO.domain.player.Pitcher;
import Baseball.record.KBO.domain.player.PitcherPosition;
import Baseball.record.KBO.domain.player.Player;
import Baseball.record.KBO.domain.team.TeamName;
import Baseball.record.KBO.dto.PlayerDto;
import Baseball.record.KBO.dto.PlayerDto2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long>, PlayerRepositoryCustom{
    List<Player> findByTeam_Name(TeamName teamName);
    List<Batter> findTop5ByOrderByOpsDesc();
    List<Batter> findAllByOrderByOpsDesc();
}
