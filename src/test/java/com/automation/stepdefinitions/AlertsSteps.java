package com.automation.stepdefinitions;

import com.automation.config.ConfigReader;
import com.automation.pages.AlertsPage;
import io.cucumber.java.en.*;
import org.testng.Assert;

/**
 * AlertsSteps - JavaScript alert handling step definitions
 */
public class AlertsSteps {

    private final AlertsPage alertsPage = new AlertsPage();
    private final ConfigReader config = ConfigReader.getInstance();

    @Given("I navigate to the alerts page")
    public void iNavigateToAlertsPage() {
        alertsPage.navigateTo(config.getBaseUrl() + "/javascript_alerts");
    }

    @When("I click the JS Alert button")
    public void iClickJsAlertButton() {
        alertsPage.clickJsAlert();
    }

    @When("I click the JS Confirm button")
    public void iClickJsConfirmButton() {
        alertsPage.clickJsConfirm();
    }

    @When("I click the JS Prompt button")
    public void iClickJsPromptButton() {
        alertsPage.clickJsPrompt();
    }

    @When("I accept the alert")
    public void iAcceptTheAlert() {
        alertsPage.acceptAlert();
    }

    @When("I dismiss the alert")
    public void iDismissTheAlert() {
        alertsPage.dismissAlert();
    }

    @When("I enter {string} in the prompt")
    public void iEnterTextInThePrompt(String text) {
        // Alert is already open from previous step (clickJsPrompt was called)
        if (text.isEmpty()) {
            alertsPage.acceptAlert(); // Accept empty prompt
        } else {
            alertsPage.typeInAlert(text);
        }
    }

    @Then("the result text should contain {string}")
    public void resultTextShouldContain(String expectedText) {
        String actualText = alertsPage.getResultText();
        Assert.assertTrue(actualText.contains(expectedText),
            "Expected result to contain: '" + expectedText + "' but got: '" + actualText + "'");
    }

    @Then("the alert message should be {string}")
    public void alertMessageShouldBe(String expectedMsg) {
        String alertMsg = alertsPage.getAlertMessage();
        Assert.assertEquals(alertMsg, expectedMsg,
            "Alert message mismatch. Expected: '" + expectedMsg + "' | Got: '" + alertMsg + "'");
    }
}
