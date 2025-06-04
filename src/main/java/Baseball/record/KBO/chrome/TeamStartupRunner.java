package Baseball.record.KBO.chrome;

import Baseball.record.KBO.chrome.crawlerService.TeamCrawlerService;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Order(1)
@Profile("!test")
public class TeamStartupRunner implements CommandLineRunner {

    private final TeamCrawlerService teamCrawlerService;

    @Override
    public void run(String... args) {
        teamCrawlerService.crawlTeamRecords();
    }
}
