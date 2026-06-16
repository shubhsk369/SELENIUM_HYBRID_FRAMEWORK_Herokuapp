package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * FramesPage - https://the-internet.herokuapp.com/iframe
 * TinyMCE editor iframe — the id includes an auto-generated suffix,
 * so we locate by CSS attribute selector pattern instead of exact id.
 */
public class FramesPage extends BasePage {

    // Use a CSS selector that matches any iframe whose id ends with "_ifr"
    // (TinyMCE always generates ids like mce_0_ifr, mce_1_ifr, etc.)
    private final By iframeLocator     = By.cssSelector("iframe[id$='_ifr']");
    private final By editorBodyLocator = By.cssSelector("#tinymce p");
    private final By editorBody        = By.cssSelector("#tinymce");

    // ─── iFrame Actions ──────────────────────────────────────────────────────

    public void switchToIframe() {
        log.info("Waiting for TinyMCE iframe...");
        // Wait up to 20s for TinyMCE to initialise
        waitUtils.waitForVisible(iframeLocator);
        WebElement iframe = driver.findElement(iframeLocator);
        driver.switchTo().frame(iframe);
        log.info("Switched to TinyMCE iframe");
    }

    public String getIframeBodyText() {
        switchToIframe();
        String text = "";
        try {
            text = findElement(editorBodyLocator).getText();
        } catch (Exception e) {
            // body may be empty
        }
        switchToDefaultContent();
        return text;
    }

    public void clearAndTypeInIframe(String text) {
        switchToIframe();
        try {
            WebElement body = findElement(editorBody);
            body.clear();
            body.sendKeys(text);
        } catch (Exception e) {
            log.warn("Could not type in iframe body: {}", e.getMessage());
        }
        switchToDefaultContent();
    }

    // ─── Nested Frames ───────────────────────────────────────────────────────

    public String getNestedFrameText() {
        driver.switchTo().frame("frame-top");
        driver.switchTo().frame("frame-middle");
        String text = findElement(By.cssSelector("#content")).getText();
        switchToDefaultContent();
        return text;
    }

    public boolean isFrameLoaded() {
        return !driver.findElements(iframeLocator).isEmpty();
    }
}
