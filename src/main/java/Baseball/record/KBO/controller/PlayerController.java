package Baseball.record.KBO.controller;

import Baseball.record.KBO.domain.player.Batter;
import Baseball.record.KBO.domain.player.Pitcher;
import Baseball.record.KBO.domain.player.Player;
import Baseball.record.KBO.domain.team.Team;
import Baseball.record.KBO.domain.team.TeamName;
import Baseball.record.KBO.dto.BatterDto;
import Baseball.record.KBO.dto.PitcherDto;
import Baseball.record.KBO.dto.PlayerDto;
import Baseball.record.KBO.dto.PlayerDto2;
import Baseball.record.KBO.service.PlayerService;
import Baseball.record.KBO.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/kbo")
@Tag(name = "Player API", description = "선수 정보 관련 API")
public class PlayerController {
    private final PlayerService playerService;
    private final TeamService teamService;


    @PostMapping("/save/Player")
    public ResponseEntity<?> savePitcher(@RequestBody PlayerDto playerDto){
        Team team = teamService.findTeamName(playerDto.getTeamName());

        Player player;
        if (playerDto instanceof BatterDto batterDto){
            player = batterDto.toEntity(team);
        } else if (playerDto instanceof PitcherDto pitcherDto){
            player = pitcherDto.toEntity(team);
        } else {
            return ResponseEntity.badRequest().body("Invalid player type");
        }

        playerService.save(player);

        return ResponseEntity.ok("Player 저장 완료");
    }

    @Operation(summary = "선수 목록 조회", description = "모든 선수의 정보를 가져옵니다.")
    @GetMapping("/players")
    public List<PlayerDto2> getPlayers() {
        return playerService.findAll();
    }


    @GetMapping("/search/teamName")
    public List<PlayerDto> searchTeamName(@RequestParam String teamName){
        return playerService.findTeamNameWithPlayer(TeamName.valueOf(teamName));
    }

    @Operation(summary = "선수 전체 조회", description = "선수 이름을 입력하면 해당 선수의 기록을 조회할 수 있습니다.")
    @GetMapping("/find/PlayerName")
    public ResponseEntity<?> getPlayerByName(@RequestParam String name) {
        try {
            PlayerDto player = playerService.findPlayerByName(name);
            return ResponseEntity.ok(player);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "선수를 찾을 수 없습니다."));
        }
    }

    @GetMapping("/findParam/PlayerName")
    public ResponseEntity<PlayerDto> getPlayerByNameJson(@RequestParam String name){
        PlayerDto player = playerService.findPlayerByName(name);
        return ResponseEntity.ok(player);
    }

    @Operation(summary = "선수 조건 조회", description = "팀 이름, 플레이어 타입, 페이징 조건으로 선수를 조회할 수 있습니다.")
    @GetMapping("/details/players")
    public ResponseEntity<Page<? extends PlayerDto>> getAllPlayerWithDetails(
            @RequestParam(required = false) TeamName teamName,
            @RequestParam(required = false) String playerType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page,size);
        Page<? extends PlayerDto> players = playerService.findAllPlayersWithDetails(teamName, playerType, pageable);
        return ResponseEntity.ok(players);
    }

    @PutMapping("/update/{playerId}")
    public ResponseEntity<?> updatePlayer(@PathVariable Long playerId,
                                          @RequestBody PlayerDto playerDto){
        playerService.updatePlayer(playerId, playerDto);
        return ResponseEntity.ok("player 업데이트 완료");
    }

    @DeleteMapping("/delete/{playerId}")
    public ResponseEntity<?> deletePlayer(@PathVariable Long playerId){
        playerService.deletePlayer(playerId);
        return ResponseEntity.ok("player 삭제 완료");
    }
}
