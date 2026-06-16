package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * MultipleWindowsPage - https://the-internet.herokuapp.com/windows
 */
public class MultipleWindowsPage extends BasePage {

    @FindBy(css = "a[href='/windows/new']")
    private WebElement clickHereLink;

    @FindBy(css = "h3")
    private WebElement newWindowHeading;

    private final By clickHereLocator = By.cssSelector("a[href='/windows/new']");
    private final By newWindowHeadingLocator = By.cssSelector("h3");

    public String openNewWindow() {
        String mainHandle = getMainWindowHandle();
        log.info("Main window handle: {}", mainHandle);

        click(clickHereLocator);
        sleep(500);

        switchToNewWindow(mainHandle);
        log.info("Switched to new window: {}", driver.getWindowHandle());
        return mainHandle;
    }

    public String getNewWindowHeading() {
        waitUtils.waitForVisible(newWindowHeadingLocator);
        return getText(newWindowHeadingLocator);
    }

    public void closeNewWindowAndSwitchBack(String mainHandle) {
        closeCurrentWindow();
        switchToWindow(mainHandle);
        log.info("Switched back to main window");
    }

    public int getOpenWindowCount() {
        return driver.getWindowHandles().size();
    }

    public boolean isNewWindowHeadingDisplayed() {
        return isDisplayed(newWindowHeadingLocator);
    }
}
