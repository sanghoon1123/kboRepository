package Baseball.record.KBO;

import Baseball.record.KBO.chrome.crawlerService.BatterCrawlerService;
import Baseball.record.KBO.chrome.crawlerService.PitcherCrawlerService;
import Baseball.record.KBO.chrome.crawlerService.TeamCrawlerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class KboApplicationTests {

	@Autowired private TeamCrawlerService teamCrawlerService;
	@Autowired private PitcherCrawlerService pitcherCrawlerService;
	@Autowired private BatterCrawlerService batterCrawlerService;

	@Test
	void runTeamCrawlerService() throws Exception {
		teamCrawlerService.crawlTeamRecords();
		pitcherCrawlerService.crawl();
		batterCrawlerService.crawl();
	}

}
