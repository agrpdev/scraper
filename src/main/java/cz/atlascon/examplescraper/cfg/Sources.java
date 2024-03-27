package cz.atlascon.examplescraper.cfg;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import cz.atlascon.scraper.control.api.scraperclient.config.NewSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class Sources {

    private static final Logger LOGGER = LoggerFactory.getLogger(Sources.class);
    private final Map<String, NewSource> allSources = Maps.newHashMap();
    private final Map<String, NewSource> allowedSources = Maps.newHashMap();

    public Sources(@Value("${sources.whitelist:}") final Set<String> whitelist,
                   @Value("${sources.blacklist:}") final Set<String> blacklist,
                   final List<NewSource> sources) {
        sources.forEach(source -> {
                    final NewSource existing = allSources.put(source.customId(), source);
                    allowedSources.put(source.customId(), source);
                    Preconditions.checkArgument(existing == null, "Duplicate source id " + source.customId());
                });
        if (!whitelist.isEmpty()) {
            // only add if whitelisted
            allowedSources.keySet().retainAll(whitelist);
        }
        if (!blacklist.isEmpty()) {
            LOGGER.info("Will not use blacklisted sources {}", blacklist);
            if (blacklist.contains("ALL")) {
                allowedSources.clear();
            } else {
                allowedSources.keySet().removeAll(blacklist);
            }
        }
        LOGGER.info("All allowed sources: {}", allowedSources.keySet());
    }

    /**
     * Returns all allowed sources (whitelist/blacklist applied)
     *
     * @return all allowed sources
     */
    public Set<NewSource> getAllAllowedSources() {
        return Set.copyOf(allowedSources.values());
    }
    public Map<String, NewSource> getAllAllowedSourcesMap() {
        return Map.copyOf(allowedSources);
    }

}
