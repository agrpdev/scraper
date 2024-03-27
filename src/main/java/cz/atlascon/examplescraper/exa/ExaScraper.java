package cz.atlascon.examplescraper.exa;

import cz.atlascon.scraper.control.api.scraperclient.ScraperClientScraping;
import cz.atlascon.scraper.control.api.scraperclient.scrape.ScrapingEnvironment;
import cz.atlascon.scraper.control.api.work.ScrapeWorkItemDto;

import static com.codeborne.selenide.Selenide.$;

public class ExaScraper implements ScraperClientScraping<ExaItemToScrape> {
    @Override
    public void scrape(ExaItemToScrape itemToScrape, ScrapeWorkItemDto scrapeRequest, ScrapingEnvironment env) {
    }
}
