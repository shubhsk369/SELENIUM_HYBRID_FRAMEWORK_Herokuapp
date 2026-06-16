package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * LoginPage - https://the-internet.herokuapp.com/login
 * Valid credentials: tomsmith / SuperSecretPassword!
 */
public class LoginPage extends BasePage {

    // ─── Locators ────────────────────────────────────────────────────────────

    @FindBy(id = "username")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(css = "button[type='submit']")
    private WebElement loginButton;

    @FindBy(css = ".flash.success")
    private WebElement successMessage;

    @FindBy(css = "#flash")
    private WebElement flashMessage;

    private final By usernameLocator    = By.id("username");
    private final By passwordLocator    = By.id("password");
    private final By loginButtonLocator = By.cssSelector("button[type='submit']");
    private final By successLocator     = By.cssSelector(".flash.success");
    private final By flashLocator       = By.cssSelector("#flash");
    private final By pageHeading        = By.cssSelector("h2");

    // ─── Actions ─────────────────────────────────────────────────────────────

    public void enterUsername(String username) {
        log.info("Entering username: {}", username);
        type(usernameLocator, username);
    }

    public void enterPassword(String password) {
        log.info("Entering password");
        type(passwordLocator, password);
    }

    public void clickLoginButton() {
        log.info("Clicking Login button");
        click(loginButtonLocator);
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    // ─── Assertions ──────────────────────────────────────────────────────────

    public boolean isLoginPageDisplayed() {
        return isDisplayed(usernameLocator) && isDisplayed(passwordLocator);
    }

    public boolean isSuccessMessageDisplayed() {
        try {
            waitUtils.waitForVisible(successLocator);
            return successMessage.getText().contains("You logged into a secure area");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get the flash message text — strips the dismiss ×  character
     * The site uses a single #flash div for both success and error messages
     */
    public String getFlashMessageText() {
        try {
            waitUtils.waitForVisible(flashLocator);
            // getText() returns the full text including the × dismiss button text
            // We strip trailing whitespace and the × character
            String raw = flashMessage.getText().trim();
            // Remove the trailing × (close button text) if present
            if (raw.endsWith("×")) {
                raw = raw.substring(0, raw.lastIndexOf("×")).trim();
            }
            return raw;
        } catch (Exception e) {
            log.warn("Could not get flash message: {}", e.getMessage());
            return "";
        }
    }

    public boolean isErrorMessageDisplayed() {
        try {
            String msg = getFlashMessageText();
            return msg.contains("Your username is invalid") ||
                   msg.contains("Your password is invalid");
        } catch (Exception e) {
            return false;
        }
    }

    public String getPageHeading() {
        return getText(pageHeading);
    }

    public boolean isUsernameFieldEmpty() {
        return usernameField.getAttribute("value").isEmpty();
    }
}
