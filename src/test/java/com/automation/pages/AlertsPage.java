package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * AlertsPage - https://the-internet.herokuapp.com/javascript_alerts
 */
public class AlertsPage extends BasePage {

    @FindBy(css = "button[onclick='jsAlert()']")
    private WebElement jsAlertButton;

    @FindBy(css = "button[onclick='jsConfirm()']")
    private WebElement jsConfirmButton;

    @FindBy(css = "button[onclick='jsPrompt()']")
    private WebElement jsPromptButton;

    @FindBy(id = "result")
    private WebElement resultText;

    private final By resultLocator = By.id("result");

    public void clickJsAlert() {
        log.info("Clicking JS Alert button");
        jsAlertButton.click();
    }

    public void clickJsConfirm() {
        log.info("Clicking JS Confirm button");
        jsConfirmButton.click();
    }

    public void clickJsPrompt() {
        log.info("Clicking JS Prompt button");
        jsPromptButton.click();
    }

    public String getResultText() {
        waitUtils.waitForVisible(resultLocator);
        return resultText.getText();
    }

    public String getAlertMessage() {
        return getAlertText();
    }

    public void acceptJsAlert() {
        clickJsAlert();
        acceptAlert();
    }

    public void dismissJsConfirm() {
        clickJsConfirm();
        dismissAlert();
    }

    public void acceptJsConfirm() {
        clickJsConfirm();
        acceptAlert();
    }

    public void sendTextToPrompt(String text) {
        clickJsPrompt();
        typeInAlert(text);
    }
}
