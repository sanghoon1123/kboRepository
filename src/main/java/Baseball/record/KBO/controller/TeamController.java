package Baseball.record.KBO.controller;

import Baseball.record.KBO.dto.TeamRecordDto;
import Baseball.record.KBO.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/team")
@Tag(name = "Team API", description = "팀 정보 및 기록 관련 API")
public class TeamController {

    private final TeamService teamService;

    @Operation(summary = "모든 팀 기록 조회", description = "모든 팀의 시즌 기록을 조회합니다.")
    @GetMapping("/record")
    public List<TeamRecordDto> allTeamRecord(){
        return teamService.getAllTeamRecords();
    }
}
