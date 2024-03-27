package cz.atlascon.examplescraper.cfg;

import cz.atlascon.scraper.control.api.scraperclient.ControlPlaneConfig;
import cz.atlascon.scraper.control.api.scraperclient.ScraperClientRunner;
import cz.atlascon.scraper.control.api.scraperclient.ScraperExecutor;
import cz.atlascon.scraper.control.api.scraperclient.ScraperSetup;
import cz.atlascon.scraper.control.api.scraperclient.config.NewSource;
import io.prometheus.client.CollectorRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;


@Configuration
public class BeanConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(BeanConfiguration.class);

    @Bean
    public CollectorRegistry collectorRegistry() {
        return CollectorRegistry.defaultRegistry;
    }

    @Bean
    public ControlPlaneConfig controlPlaneConfig(@Value("${control-plane.url}") String controlPlaneUrl,
                                                 @Value("${control-plane.timeoutSeconds}") int timeoutSeconds,
                                                 @Value("${control-plane.progressIntervalSeconds:10}") int progressIntervalSeconds,
                                                 @Value("${control-plane.apiKey}") String apiKey) {
        return new ControlPlaneConfig(controlPlaneUrl, timeoutSeconds, progressIntervalSeconds, apiKey);
    }

    @Bean
    public List<ScraperClientRunner> runners(final ControlPlaneConfig controlPlaneConfig,
                                             final Sources sources) {
        final Map<String, NewSource> sourceMap = sources.getAllAllowedSourcesMap();
        final List<ScraperClientRunner> runners = sources.getAllAllowedSources()
                .stream()
                .map(src -> {
                    final NewSource source = sourceMap.get(src.customId());
                    final ScraperSetup setup = new ScraperSetup<>(source, controlPlaneConfig);
                    setup.loadCurrentOrDefaultSettings();
                    ScraperClientRunner runner = new ScraperClientRunner<>(setup, null, source.scraper(), source.crawler(), source.healthcheck(), source.stream());
                    return runner;
                }).toList();
        return runners;
    }

    @Bean
    public ScraperExecutor scraperExecutor(final List<ScraperClientRunner> runners) {
        LOGGER.info("Created {} runners", runners.size());
        if (runners.isEmpty()) {
            LOGGER.warn("No runners created, check whitelist/blacklist");
            throw new IllegalStateException("No runners created, check whitelist/blacklist");
        }
        return new ScraperExecutor(runners);
    }

}

