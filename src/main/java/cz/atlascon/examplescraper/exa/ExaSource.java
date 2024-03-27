package cz.atlascon.examplescraper.exa;

import cz.atlascon.scraper.control.api.scraperclient.ScraperClientCrawling;
import cz.atlascon.scraper.control.api.scraperclient.ScraperClientScraping;
import cz.atlascon.scraper.control.api.scraperclient.config.NewSource;
import org.springframework.stereotype.Component;

@Component
public class ExaSource implements NewSource<ExaSettings, ExaTaskSettings, ExaItemToScrape> {
    private final String id = "exa-scraper";

    @Override
    public String customId() {
        return id;
    }
    @Override
    public ScraperClientCrawling<ExaTaskSettings, ExaItemToScrape> crawler() {
        return new ExaCrawler();
    }

    @Override
    public ScraperClientScraping<ExaItemToScrape> scraper() {
        return new ExaScraper();
    }
}
