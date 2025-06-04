package Baseball.record.KBO.chrome;

import Baseball.record.KBO.chrome.crawlerService.TeamCrawlerService;
import Baseball.record.KBO.service.TeamCrawlerServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Order(1)
public class TeamCrawlerTest {

    @Autowired
    private TeamCrawlerServiceTest teamCrawlerService;

    @Test
    public void crawlAndSaveTeamData() throws Exception {
        System.out.println("=== 크롤링 테스트 시작 ===");
        teamCrawlerService.crawlAndSaveTeamData();
    }
}

