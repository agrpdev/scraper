package cz.atlascon.examplescraper.cfg;

import jakarta.annotation.PostConstruct;
import org.openqa.selenium.chrome.ChromeOptions;

public record SelenideCfg(
        ChromeOptions options,
        boolean headless,
        int timeoutInMiliseconds,
        int pageLoadTimeout
) {
    @PostConstruct
    void initSelenide() {
        com.codeborne.selenide.Configuration.timeout = timeoutInMiliseconds;
        com.codeborne.selenide.Configuration.headless = headless;
        com.codeborne.selenide.Configuration.browser = "chrome";
        com.codeborne.selenide.Configuration.pageLoadTimeout = pageLoadTimeout;
        com.codeborne.selenide.Configuration.browserCapabilities = options;
    }
}
