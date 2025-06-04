package Baseball.record.KBO.chrome;

import Baseball.record.KBO.chrome.crawlerService.BatterCrawlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Order(3)
@Profile("!test")
public class BatterStartupRunner implements CommandLineRunner {

    private final BatterCrawlerService batterCrawlerService;

    @Override
    public void run(String... args) {
        batterCrawlerService.crawl();
    }
}


