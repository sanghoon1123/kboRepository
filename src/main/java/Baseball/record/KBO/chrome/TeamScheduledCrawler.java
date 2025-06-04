package Baseball.record.KBO.chrome;

import Baseball.record.KBO.chrome.crawlerService.TeamCrawlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("!test")
public class TeamScheduledCrawler {

    private final TeamCrawlerService teamCrawlerService;

    @Scheduled(cron = "0 0 * * * *") // 매시간 정각
    public void scheduledCrawl() {
        teamCrawlerService.crawlTeamRecords();
    }
}
