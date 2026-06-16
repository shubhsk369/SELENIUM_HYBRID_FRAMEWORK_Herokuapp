package com.automation.pages;

import com.automation.drivers.DriverManager;
import com.automation.utils.WaitUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

/**
 * BasePage - Parent class for all Page Object classes
 * Contains reusable Selenium helper methods
 */
public class BasePage {

    protected final Logger log = LogManager.getLogger(this.getClass());
    public WebDriver driver;
    protected WaitUtils waitUtils;
    protected Actions actions;
    protected JavascriptExecutor js;

    public BasePage() {
        this.driver = DriverManager.getDriver();
        this.waitUtils = new WaitUtils(driver);
        this.actions = new Actions(driver);
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    // ─── Navigation ──────────────────────────────────────────────────────────

    public void navigateTo(String url) {
        log.info("Navigating to: {}", url);
        driver.get(url);
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    // ─── Element Interactions ────────────────────────────────────────────────

    public void click(By locator) {
        log.debug("Clicking element: {}", locator);
        waitUtils.waitForClickable(locator).click();
    }

    public void click(WebElement element) {
        log.debug("Clicking WebElement");
        waitUtils.waitForClickable(element).click();
    }

    public void type(By locator, String text) {
        log.debug("Typing '{}' into: {}", text, locator);
        WebElement el = waitUtils.waitForVisible(locator);
        el.clear();
        el.sendKeys(text);
    }

    public void type(WebElement element, String text) {
        waitUtils.waitForClickable(element).clear();
        element.sendKeys(text);
    }

    public String getText(By locator) {
        return waitUtils.waitForVisible(locator).getText();
    }

    public String getText(WebElement element) {
        return element.getText();
    }

    public String getAttribute(By locator, String attribute) {
        return waitUtils.waitForVisible(locator).getAttribute(attribute);
    }

    public boolean isDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    public boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public List<WebElement> findElements(By locator) {
        return driver.findElements(locator);
    }

    public WebElement findElement(By locator) {
        return driver.findElement(locator);
    }

    // ─── Dropdown ────────────────────────────────────────────────────────────

    public void selectByVisibleText(By locator, String text) {
        log.debug("Selecting '{}' from dropdown: {}", text, locator);
        new Select(waitUtils.waitForVisible(locator)).selectByVisibleText(text);
    }

    public void selectByValue(By locator, String value) {
        new Select(waitUtils.waitForVisible(locator)).selectByValue(value);
    }

    public void selectByIndex(By locator, int index) {
        new Select(waitUtils.waitForVisible(locator)).selectByIndex(index);
    }

    public String getSelectedOption(By locator) {
        return new Select(driver.findElement(locator)).getFirstSelectedOption().getText();
    }

    // ─── Alerts ──────────────────────────────────────────────────────────────

    public String getAlertText() {
        Alert alert = waitUtils.waitForAlert();
        return alert.getText();
    }

    public void acceptAlert() {
        log.debug("Accepting alert");
        waitUtils.waitForAlert().accept();
    }

    public void dismissAlert() {
        log.debug("Dismissing alert");
        waitUtils.waitForAlert().dismiss();
    }

    public void typeInAlert(String text) {
        Alert alert = waitUtils.waitForAlert();
        alert.sendKeys(text);
        alert.accept();
    }

    // ─── Frames ──────────────────────────────────────────────────────────────

    public void switchToFrame(By locator) {
        log.debug("Switching to frame: {}", locator);
        driver.switchTo().frame(driver.findElement(locator));
    }

    public void switchToFrame(int index) {
        driver.switchTo().frame(index);
    }

    public void switchToFrame(String nameOrId) {
        driver.switchTo().frame(nameOrId);
    }

    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    // ─── Windows ─────────────────────────────────────────────────────────────

    public String getMainWindowHandle() {
        return driver.getWindowHandle();
    }

    public void switchToNewWindow(String mainHandle) {
        log.debug("Switching to new window");
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(mainHandle)) {
                driver.switchTo().window(handle);
                break;
            }
        }
    }

    public void switchToWindow(String handle) {
        driver.switchTo().window(handle);
    }

    public void closeCurrentWindow() {
        driver.close();
    }

    // ─── Actions ─────────────────────────────────────────────────────────────

    public void hoverOver(By locator) {
        log.debug("Hovering over: {}", locator);
        WebElement element = waitUtils.waitForVisible(locator);
        actions.moveToElement(element).perform();
    }

    public void hoverOver(WebElement element) {
        actions.moveToElement(element).perform();
    }

    public void dragAndDrop(By source, By target) {
        log.debug("Drag and Drop: {} -> {}", source, target);
        WebElement src = waitUtils.waitForVisible(source);
        WebElement tgt = waitUtils.waitForVisible(target);
        actions.dragAndDrop(src, tgt).perform();
    }

    public void dragAndDrop(WebElement source, WebElement target) {
        actions.dragAndDrop(source, target).perform();
    }

    // ─── JavaScript ──────────────────────────────────────────────────────────

    public void jsClick(By locator) {
        WebElement element = driver.findElement(locator);
        js.executeScript("arguments[0].click();", element);
    }

    public void jsClick(WebElement element) {
        js.executeScript("arguments[0].click();", element);
    }

    public void scrollIntoView(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void scrollToBottom() {
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    // ─── Checkbox / Radio ────────────────────────────────────────────────────

    public void checkCheckbox(WebElement element) {
        if (!element.isSelected()) {
            element.click();
        }
    }

    public void uncheckCheckbox(WebElement element) {
        if (element.isSelected()) {
            element.click();
        }
    }

    public boolean isChecked(WebElement element) {
        return element.isSelected();
    }

    // ─── File Upload ─────────────────────────────────────────────────────────

    public void uploadFile(By locator, String absoluteFilePath) {
        log.info("Uploading file: {}", absoluteFilePath);
        driver.findElement(locator).sendKeys(absoluteFilePath);
    }

    // ─── Utility ─────────────────────────────────────────────────────────────

    public void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public int getElementCount(By locator) {
        return driver.findElements(locator).size();
    }
}
