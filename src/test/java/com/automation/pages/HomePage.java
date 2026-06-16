package com.automation.pages;

import com.automation.config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * HomePage - The Internet Herokuapp home page
 * URL: https://the-internet.herokuapp.com
 */
public class HomePage extends BasePage {

    private final String BASE_URL = ConfigReader.getInstance().getBaseUrl();

    @FindBy(css = "h1.heading")
    private WebElement pageHeading;

    @FindBy(css = "ul li a")
    private List<WebElement> allLinks;

    private final By heading = By.cssSelector("h1.heading");
    private final By linksList = By.cssSelector("ul li a");

    public void openHomePage() {
        log.info("Opening home page: {}", BASE_URL);
        navigateTo(BASE_URL);
        waitUtils.waitForVisible(heading);
    }

    public String getHeadingText() {
        return getText(heading);
    }

    public void clickLink(String linkText) {
        log.info("Clicking link: {}", linkText);
        By link = By.linkText(linkText);
        waitUtils.waitForClickable(link).click();
    }

    public boolean isPageLoaded() {
        return isDisplayed(heading);
    }

    public int getLinkCount() {
        return allLinks.size();
    }

    public void navigateToSubPage(String path) {
        navigateTo(BASE_URL + path);
    }
}
