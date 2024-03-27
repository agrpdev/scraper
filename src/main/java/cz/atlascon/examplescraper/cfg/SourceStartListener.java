package cz.atlascon.examplescraper.cfg;

import cz.atlascon.scraper.control.api.scraperclient.ScraperExecutor;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class SourceStartListener implements ApplicationListener<ApplicationEvent> {
    private final ScraperExecutor scraperExecutor;
    public SourceStartListener(final ScraperExecutor scraperExecutor) {
        this.scraperExecutor = scraperExecutor;
    }
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        scraperExecutor.run();
    }
}
