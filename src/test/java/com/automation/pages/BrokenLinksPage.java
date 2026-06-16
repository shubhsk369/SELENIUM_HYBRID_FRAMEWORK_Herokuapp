package com.automation.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * BrokenLinksPage - https://the-internet.herokuapp.com/broken_images
 * Also checks general page links for broken status
 */
public class BrokenLinksPage extends BasePage {

    private static final Logger log = LogManager.getLogger(BrokenLinksPage.class);

    @FindBy(css = "a")
    private List<WebElement> allLinks;

    @FindBy(css = "img")
    private List<WebElement> allImages;

    private final By linksLocator  = By.cssSelector("a");
    private final By imagesLocator = By.cssSelector("img");

    /**
     * Returns list of broken link URLs (HTTP status >= 400)
     */
    public List<String> getBrokenLinks() {
        List<String> brokenLinks = new ArrayList<>();
        List<WebElement> links = findElements(linksLocator);

        for (WebElement link : links) {
            String href = link.getAttribute("href");
            if (href == null || href.isEmpty() || href.startsWith("javascript") || href.startsWith("mailto")) {
                continue;
            }
            try {
                int statusCode = getHttpStatusCode(href);
                if (statusCode >= 400) {
                    log.warn("Broken link found: {} [Status: {}]", href, statusCode);
                    brokenLinks.add(href + " [" + statusCode + "]");
                }
            } catch (Exception e) {
                log.warn("Could not check link: {} - {}", href, e.getMessage());
            }
        }
        return brokenLinks;
    }

    /**
     * Returns count of broken images (src returning 404 or invalid)
     */
    public int getBrokenImageCount() {
        int count = 0;
        List<WebElement> images = findElements(imagesLocator);
        for (WebElement img : images) {
            String src = img.getAttribute("src");
            if (src == null || src.isEmpty()) {
                count++;
                continue;
            }
            try {
                int statusCode = getHttpStatusCode(src);
                if (statusCode >= 400) {
                    count++;
                    log.warn("Broken image: {} [{}]", src, statusCode);
                }
            } catch (Exception e) {
                count++;
            }
        }
        return count;
    }

    public int getTotalLinkCount() {
        return findElements(linksLocator).size();
    }

    public int getTotalImageCount() {
        return findElements(imagesLocator).size();
    }

    private int getHttpStatusCode(String urlString) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(urlString).openConnection();
        connection.setRequestMethod("HEAD");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.connect();
        int code = connection.getResponseCode();
        connection.disconnect();
        return code;
    }
}
