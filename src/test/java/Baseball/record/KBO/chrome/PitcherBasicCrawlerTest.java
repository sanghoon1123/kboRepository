package Baseball.record.KBO.chrome;

import Baseball.record.KBO.chrome.crawlerService.PitcherCrawlerService;
import Baseball.record.KBO.service.PitcherCrawlerServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@Order(2)
@ActiveProfiles("test")
public class PitcherBasicCrawlerTest {

    @Autowired
    private PitcherCrawlerServiceTest pitcherCrawlerService;

    @Test
    public void crawlAndSavePitcher() throws Exception {
        pitcherCrawlerService.crawlAndSavePitcher();
    }

}
