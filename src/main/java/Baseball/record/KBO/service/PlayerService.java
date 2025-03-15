package Baseball.record.KBO.service;

import Baseball.record.KBO.domain.player.Batter;
import Baseball.record.KBO.domain.player.Pitcher;
import Baseball.record.KBO.domain.player.Player;
import Baseball.record.KBO.domain.team.Team;
import Baseball.record.KBO.domain.team.TeamName;
import Baseball.record.KBO.dto.BatterDto;
import Baseball.record.KBO.dto.PitcherDto;
import Baseball.record.KBO.dto.PlayerDto;
import Baseball.record.KBO.dto.PlayerDto2;
import Baseball.record.KBO.repository.PlayerRepository;
import Baseball.record.KBO.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Transactional
@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    public <T extends Player> T save(T player){
        T savedPlayer = (T) playerRepository.save(player);

        if (player.getName().equals("RollbackTest")) {
            throw new RuntimeException("강제 오류 발생! 트랜잭션 롤백");
        }
        return savedPlayer;
    }

    public List<PlayerDto2> findAll(){
        return playerRepository.findAll().stream().map(p -> new PlayerDto2(p)).collect(toList());


    }


    public Player findOne(Long id){
        return playerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("player id not found : "+id));
    }

    public List<PlayerDto> findTeamNameWithPlayer(TeamName teamName){
        List<Player> findPlayers = playerRepository.findByTeam_Name(teamName);
        return findPlayers.stream()
                .map(p -> {
                    if (p instanceof Batter) {  // ✅ Batter인 경우
                        Batter b = (Batter) p;
                        return new BatterDto(
                                b.getName(), b.getAge(), b.getGame(), b.getTeam().getName(), b.getPlayerType(),
                                b.getAverage(), b.getHit(), b.getHomeRun(), b.getRbi(),
                                b.getOps(), b.getSteal(), b.getBatterPosition()
                        );
                    } else if (p instanceof Pitcher) {  // ✅ Pitcher인 경우
                        Pitcher pitch = (Pitcher) p;
                        return new PitcherDto(
                                pitch.getName(), pitch.getAge(), pitch.getGame(), pitch.getTeam().getName(), pitch.getPlayerType(),
                                pitch.getWin(), pitch.getLose(), pitch.getIp(), pitch.getEra(),
                                pitch.getStrikeouts(), pitch.getHold(), pitch.getSave(), pitch.getPitcherPosition()
                        );
                    } else {
                        return null; // 다른 타입의 Player가 있을 경우 (예외 처리)
                    }
                })
                .filter(Objects::nonNull)  // ✅ null 값 제거
                .collect(toList());  // ✅ 변환된 리스트 반환
    }

    public PlayerDto findPlayerByName(String playerName){
        return playerRepository.findPlayerByName(playerName)
                .orElseThrow(() ->  new EntityNotFoundException("player not found : "+ playerName));
    }

    public Page<? extends PlayerDto> findAllPlayersWithDetails(TeamName teamName, String playerType, Pageable pageable){
        return playerRepository.findAllPlayersWithDetails(teamName, playerType, pageable);
    }

    public void updatePlayer(Long playerId, PlayerDto playerDto){
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new EntityNotFoundException("아이디를 찾을 수 없습니다" + playerId));

        Team team = teamRepository.findByName(playerDto.getTeamName())
                .orElseThrow(() -> new EntityNotFoundException("팀 없음"));

        player.updateFields(playerDto, team);

        playerRepository.save(player);
    }

    public void deletePlayer(Long playerId){
        playerRepository.deleteById(playerId);
    }



}
