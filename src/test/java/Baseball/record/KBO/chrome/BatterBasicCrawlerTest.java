package Baseball.record.KBO.chrome;

import Baseball.record.KBO.service.BatterCrawlerService;
import Baseball.record.KBO.service.TeamCrawlerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Order(3)
class BatterBasicCrawlerTest {

    @Autowired
    private BatterCrawlerService batterCrawlerService;

    @Test
    public void crawlAndSaveBatter() throws Exception {
        batterCrawlerService.crawlAndSaveBatterData();
    }


}