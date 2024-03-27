package cz.atlascon.examplescraper.cfg;

import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Map;

@Configuration
public class BeanConfig {
    @Bean
    public ChromeOptions chromeOptions() {
        //System.setProperty("webdriver.chrome.driver", "/home/krokviak/IdeaProjects/scraper/scrapers/chromedriver");

        var printSettings = """                
                 {
                   "recentDestinations": [
                     {
                       "id": "Save as PDF",
                       "origin": "local",
                       "account": ""
                     }
                   ],
                   "selectedDestinationId": "Save as PDF",
                   "version": 2,
                   "isHeaderFooterEnabled": false,
                   "mediaSize": {
                     "height_microns": 210000,
                     "name": "ISO_A4",
                     "width_microns": 148000,
                     "custom_display_name": "A4"
                   },
                   "customMargins": {},
                   "marginsType": 2,            
                   "isCssBackgroundEnabled": true
                 }
                """;
        final ChromeOptions chromeOptions = new ChromeOptions()
                .addArguments(
                        "--no-sandbox",
                        "--lang=cs-CZ",
                        "--accept-lang=cs-CZ",
                        "--remote-allow-origins=*",
                        "--kiosk-printing",
                        "--enable-print-browser"
                );
        final String downloadFolder = "/mnt/scrape";
        chromeOptions.setExperimentalOption("prefs",
                Map.of(
                        "intl.accept_languages", "cs-CZ",
                        "printing.print_preview_sticky_settings.appState", printSettings,
                        "download.default_directory", downloadFolder,
                        "savefile.default_directory", "/tmp"
                ));
        return chromeOptions;
    }

    @Bean
    public SelenideCfg selenideCfg(final ChromeOptions chromeOptions,
                                   @Value("${selenide.timeout:10000}") final int timeoutInMiliseconds,
                                   @Value("${selenide.pageLoadTimeout:90000}") final int pageLoadTimeout) {
        return new SelenideCfg(chromeOptions, true, timeoutInMiliseconds, pageLoadTimeout);
    }

    @Bean
    @Profile("local")
    public SelenideCfg selenideCfgLocal(final ChromeOptions chromeOptions,
                                        @Value("${selenide.timeout:10000}") final int timeoutInMiliseconds,
                                        @Value("${selenide.pageLoadTimeout:90000}") final int pageLoadTimeout) {
        return new SelenideCfg(chromeOptions, false, timeoutInMiliseconds, pageLoadTimeout);
    }
}