package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * SecureAreaPage - https://the-internet.herokuapp.com/secure
 * Accessible only after successful login with tomsmith / SuperSecretPassword!
 */
public class SecureAreaPage extends BasePage {

    @FindBy(css = "a[href='/logout']")
    private WebElement logoutButton;

    @FindBy(css = ".flash.success")
    private WebElement successBanner;

    @FindBy(css = "h2")
    private WebElement pageHeading;

    @FindBy(css = "#flash")
    private WebElement flashMessage;

    private final By logoutLocator  = By.cssSelector("a[href='/logout']");
    private final By successLocator = By.cssSelector(".flash.success");
    private final By headingLocator = By.cssSelector("h2");
    private final By flashLocator   = By.cssSelector("#flash");

    public boolean isSecureAreaDisplayed() {
        try {
            waitUtils.waitForVisible(headingLocator);
            String heading = pageHeading.getText();
            log.info("Secure area heading: {}", heading);
            return heading.contains("Secure Area");
        } catch (Exception e) {
            log.warn("Secure area not displayed: {}", e.getMessage());
            return false;
        }
    }

    public boolean isLoginSuccessBannerDisplayed() {
        try {
            waitUtils.waitForVisible(successLocator);
            return successBanner.getText().contains("You logged into a secure area");
        } catch (Exception e) {
            return false;
        }
    }

    public void clickLogout() {
        log.info("Clicking Logout button");
        click(logoutLocator);
    }

    public String getPageHeading() {
        return getText(headingLocator);
    }

    public boolean isLogoutButtonDisplayed() {
        return isDisplayed(logoutLocator);
    }

    /**
     * Get logout/flash message text, stripping the × dismiss character
     */
    public String getFlashMessageText() {
        try {
            waitUtils.waitForVisible(flashLocator);
            String raw = flashMessage.getText().trim();
            if (raw.endsWith("×")) {
                raw = raw.substring(0, raw.lastIndexOf("×")).trim();
            }
            return raw;
        } catch (Exception e) {
            return "";
        }
    }
}
