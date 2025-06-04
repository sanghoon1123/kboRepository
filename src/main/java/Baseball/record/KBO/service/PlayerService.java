package Baseball.record.KBO.service;

import Baseball.record.KBO.domain.player.*;


import Baseball.record.KBO.domain.team.Team;
import Baseball.record.KBO.domain.team.TeamName;
import Baseball.record.KBO.domain.team.TeamRecord;
import Baseball.record.KBO.dto.*;
import Baseball.record.KBO.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final BatterRepository batterRepository;
    private final PitcherRepository pitcherRepository;
    private final TeamRecordRepository teamRecordRepository;

    public <T extends Player> T save(T player) {
        T savedPlayer = (T) playerRepository.save(player);

        if (player.getName().equals("RollbackTest")) {
            throw new RuntimeException("강제 오류 발생! 트랜잭션 롤백");
        }
        return savedPlayer;
    }

    public List<PlayerDto2> findAll() {
        return playerRepository.findAll().stream().map(p -> new PlayerDto2(p)).collect(toList());


    }


    public Player findOne(Long id) {
        return playerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("player id not found : " + id));
    }

    public List<PlayerDto> findTeamNameWithPlayer(TeamName teamName) {
        List<Player> findPlayers = playerRepository.findByTeamName(teamName);
        return findPlayers.stream()
                .map(p -> {
                    if (p instanceof Batter) {  // ✅ Batter인 경우
                        Batter b = (Batter) p;
                        return new BatterDto(
                                b.getName(), b.getBirthDate(), b.getGame(), b.getTeam().getName(), b.getPlayerType(),
                                b.getAverage(), b.getHit(), b.getHomeRun(), b.getRbi(),
                                b.getOps(), b.getBatterPosition()
                        );
                    } else if (p instanceof Pitcher) {  // ✅ Pitcher인 경우
                        Pitcher pitch = (Pitcher) p;
                        return new PitcherDto(
                                pitch.getName(), pitch.getBirthDate(), pitch.getGame(), pitch.getTeam().getName(), pitch.getPlayerType(),
                                pitch.getWin(), pitch.getLose(), pitch.getIp(), pitch.getEra(),
                                pitch.getStrikeouts(), pitch.getHold(), pitch.getSave(), pitch.getPosition(), pitch.isQualifiedInnings()
                        );
                    } else {
                        return null; // 다른 타입의 Player가 있을 경우 (예외 처리)
                    }
                })
                .filter(Objects::nonNull)  // ✅ null 값 제거
                .collect(toList());  // ✅ 변환된 리스트 반환
    }

        public PlayerDto findPlayerByName(String playerName) {
            return playerRepository.findPlayerByName(playerName)
                    .orElseThrow(() -> new EntityNotFoundException("player not found : " + playerName));
        }

    public Page<? extends PlayerDto> findAllPlayersWithDetails(TeamName teamName, String playerType, Pageable pageable) {
        return playerRepository.findAllPlayersWithDetails(teamName, playerType, pageable);
    }

    public void updatePlayer(Long playerId, PlayerDto playerDto) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new EntityNotFoundException("아이디를 찾을 수 없습니다" + playerId));

        Team team = teamRepository.findByName(playerDto.getTeamName())
                .orElseThrow(() -> new EntityNotFoundException("팀 없음"));

        player.updateFields(playerDto, team);

        playerRepository.save(player);
    }

    public List<Batter> saveAllBatters(Map<String, PlayerDto2> playerMap) {
        batterRepository.deleteAll();

        List<Batter> batters = playerMap.values().stream()
                .filter(dto -> dto instanceof BatterDto2)
                .map(dto -> {
                    BatterDto2 batterDto = (BatterDto2) dto;
                    Team team = teamRepository.findByName(batterDto.getTeamName()).
                            orElseThrow(() ->
                                    new IllegalArgumentException("해당 팀이 존재하지 않습니다. " + batterDto.getTeamName()));

                    return new Batter(
                            batterDto.getName(),
                            batterDto.getBirthDate(),
                            batterDto.getGame(),
                            team,
                            batterDto.getAverage(),
                            batterDto.getHit(),
                            batterDto.getHomeRun(),
                            batterDto.getRbi(),
                            batterDto.getOps(),
                            batterDto.getBatterPosition()
                    );
                })
                .collect(toList());

        return playerRepository.saveAll(batters);
    }

    public List<Pitcher> saveAllPitchers(Map<String, PitcherDto2> playerMap) {
        pitcherRepository.deleteAll();

        List<Pitcher> pitchers = playerMap.values().stream()
                .filter(dto -> dto != null)
                .map(dto -> {
                    PitcherDto2 pitcherDto = dto;
                    Team team = teamRepository.findByName(pitcherDto.getTeamName()).
                            orElseThrow(() ->
                                    new IllegalArgumentException("해당 팀이 존재하지 않습니다. " + pitcherDto.getTeamName()));

                    return new Pitcher(
                            pitcherDto.getName(),
                            pitcherDto.getBirthDate(),
                            pitcherDto.getGame(),
                            team,
                            pitcherDto.getWin(),
                            pitcherDto.getLose(),
                            pitcherDto.getIp(),
                            pitcherDto.getEra(),
                            pitcherDto.getStrikeouts(),
                            pitcherDto.getHold(),
                            pitcherDto.getSave(),
                            pitcherDto.getPosition(),
                            pitcherDto.isQualifiedInnings()
                    );
                })
                .collect(toList());

        return playerRepository.saveAll(pitchers);
    }

    public void deletePlayer(Long playerId) {
        playerRepository.deleteById(playerId);
    }

    @Transactional(readOnly = true)
    public Map<String, Object> opsTop5(String playerName) {
        List<Batter> top5Batters = playerRepository.findTop5ByOrderByOpsDesc();
        List<BatterDto2> top5Dtos = top5Batters.stream()
                .map((Batter b) -> new BatterDto2(b, b))  // ✅ 타입 명시적으로 지정
                .collect(toList());

        List<Batter> allBatters = playerRepository.findAllByOrderByOpsDesc();
        allBatters.size();

        int playerRank = -1;
        BatterDto2 playerDto = null;
        boolean isPlayerInTop5 = false;

        for (int i = 0; i < allBatters.size(); i++) {
            if (allBatters.get(i).getName().equals(playerName)) {
                playerRank = i + 1;
                playerDto = new BatterDto2(allBatters.get(i), allBatters.get(i));

                isPlayerInTop5 = top5Batters.contains(allBatters.get(i));
                break;
            }
        }

        if (!isPlayerInTop5 && playerDto != null) {
            top5Dtos.add(playerDto);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("top5", top5Dtos);
        response.put("playerRank", playerRank);
        response.put("player", playerDto);

        return response;
    }

    public void pitcherPositionFilter(Map<String, PitcherDto2> pitcherMap) {
        for (PitcherDto2 pitcher : pitcherMap.values()) {
            double ipPerGame = pitcher.getGame() == 0 ? 0.0 : pitcher.getIp() / pitcher.getGame();
            PitcherPosition position = ipPerGame > 3.0 ? PitcherPosition.SP : PitcherPosition.RP;
            pitcher.setPosition(position);
        }

        pitcherMap.values().stream()
                .collect(Collectors.groupingBy(PitcherDto2::getTeamName))
                .forEach((team, pitchers) -> {
                    int maxSave = pitchers.stream()
                            .filter(p -> p.getPosition() == PitcherPosition.RP)
                            .mapToInt(PitcherDto2::getSave)
                            .max()
                            .orElse(0);

                    pitchers.stream()
                            .filter(p -> p.getPosition() == PitcherPosition.RP && p.getSave() == maxSave)
                            .forEach(p -> p.setPosition(PitcherPosition.CP));
                });
    }

    public Map<String, Object> getBatters(String teamName, String batterPosition) {

        // Enum 변환
        TeamName team = parseTeamName(teamName);
        BatterPosition position = parseBatterPosition(batterPosition);

        List<Batter> results;

        if (team == null && position == null) {
            results = batterRepository.findAll();
        } else if (team != null && position != null) {
            results = batterRepository.findByTeamNameAndBatterPosition(team, position);
        } else if (team != null) {
            results = batterRepository.findByTeamName(team);
        } else {
            results = batterRepository.findByBatterPosition(position);
        }

        List<BatterResponseDto> dtoList = results.stream()
                .map(BatterResponseDto::new).toList();

        return Map.of(
                "content", dtoList
        );
    }

    public Map<String, Object> getPitchers(String teamName, String position) {
        List<Pitcher> results;

        TeamName team = parseTeamName(teamName);
        PitcherPosition pitcherPosition = parsePitcherPosition(position);

        if (team == null && pitcherPosition == null) {
            results = pitcherRepository.findAll();
        } else if (team != null && pitcherPosition != null) {
            results = pitcherRepository.findByTeamNameAndPosition(team, pitcherPosition);
        } else if (team != null) {
            results = pitcherRepository.findByTeamName(team); // ✅ team만 필터링
        } else {
            results = pitcherRepository.findByPosition(pitcherPosition); // ✅ position만 필터링
        }

        List<PitcherResponseDto> dtoList = results.stream()
                .map(PitcherResponseDto::new)
                .toList();

        return Map.of("content", dtoList);
    }


    private TeamName parseTeamName(String name) {
        try {
            return (name == null || name.equals("ALL")) ? null : TeamName.valueOf(name);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private BatterPosition parseBatterPosition(String position) {
        try {
            return (position == null || position.equals("ALL")) ? null : BatterPosition.valueOf(position);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private PitcherPosition parsePitcherPosition(String position) {
        try {
            return (position == null || position.equals("ALL")) ? null : PitcherPosition.valueOf(position);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public void markQualifiedInnings(Map<String, PitcherDto2> pitcherMap) {
        for (PitcherDto2 pitcher : pitcherMap.values()) {
            try {
                TeamName teamName = TeamName.fromKoreanName(String.valueOf(pitcher.getTeamName()))
                        .orElseThrow(() -> new IllegalArgumentException("팀 이름 매칭 실패: " + pitcher.getTeamName()));

                TeamRecord latestRecord = teamRecordRepository.findTopByTeamNameOrderByDateDesc(teamName)
                        .orElseThrow(() -> new IllegalStateException("팀 기록 없음: " + teamName));

                double innings = pitcher.getIp();
                int teamGames = latestRecord.getGame();
                boolean isQualified = innings >= teamGames;

                pitcher.setQualifiedInnings(isQualified);
            } catch (Exception e) {
                System.out.println("⚠️ 규정 이닝 판단 실패: " + pitcher.getName() + " - " + e.getMessage());
            }
        }
    }

}







