package cz.atlascon.examplescraper.exa;

import cz.atlascon.scraper.control.api.scraperclient.ScraperClientCrawling;
import cz.atlascon.scraper.control.api.scraperclient.crawl.CrawlingEnvironment;
import cz.atlascon.scraper.control.api.work.CrawlWorkItemDto;

public class ExaCrawler implements ScraperClientCrawling<ExaTaskSettings, ExaItemToScrape> {
    @Override
    public void crawl(ExaTaskSettings exaTaskSettings, CrawlWorkItemDto crawlRequest, CrawlingEnvironment<ExaItemToScrape> env) {
    }
}
