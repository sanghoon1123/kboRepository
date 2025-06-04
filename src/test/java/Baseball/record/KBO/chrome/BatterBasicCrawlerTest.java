package Baseball.record.KBO.chrome;

import Baseball.record.KBO.chrome.crawlerService.BatterCrawlerService;
import Baseball.record.KBO.service.BatterCrawlerServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Order(3)
class BatterBasicCrawlerTest {

    @Autowired
    private BatterCrawlerServiceTest batterCrawlerService;

    @Test
    public void crawlAndSaveBatter() throws Exception {
        batterCrawlerService.crawlAndSaveBatterData();
    }


}