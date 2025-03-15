package Baseball.record.KBO.repository;

import Baseball.record.KBO.domain.team.TeamName;
import Baseball.record.KBO.dto.PlayerDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PlayerRepositoryCustom {
    Optional<PlayerDto> findPlayerByName(String playerName);
    public Page<? extends PlayerDto> findAllPlayersWithDetails(TeamName teamName, String playerType, Pageable pageable);
}
