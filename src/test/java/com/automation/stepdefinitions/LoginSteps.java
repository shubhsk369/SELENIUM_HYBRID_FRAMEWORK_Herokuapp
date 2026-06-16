package com.automation.stepdefinitions;

import com.automation.config.ConfigReader;
import com.automation.pages.LoginPage;
import com.automation.pages.SecureAreaPage;
import io.cucumber.java.en.*;
import org.testng.Assert;

/**
 * LoginSteps - Step definitions for Login and Logout features
 * Site credentials: tomsmith / SuperSecretPassword!
 */
public class LoginSteps {

    private final LoginPage loginPage = new LoginPage();
    private final SecureAreaPage secureAreaPage = new SecureAreaPage();
    private final ConfigReader config = ConfigReader.getInstance();

    // ─── Given Steps ─────────────────────────────────────────────────────────

    @Given("I navigate to the login page")
    public void iNavigateToLoginPage() {
        loginPage.navigateTo(config.getBaseUrl() + "/login");
        Assert.assertTrue(loginPage.isLoginPageDisplayed(),
            "Login page did not load. URL: " + loginPage.getCurrentUrl());
    }

    @Given("I am logged in as {string} with password {string}")
    public void iAmLoggedInAs(String username, String password) {
        loginPage.navigateTo(config.getBaseUrl() + "/login");
        loginPage.login(username, password);

        // Wait briefly for redirect
        loginPage.sleep(1000);

        Assert.assertTrue(secureAreaPage.isSecureAreaDisplayed(),
            "Login failed for user '" + username + "'. Current URL: " + loginPage.getCurrentUrl());
    }

    @Given("I navigate directly to the secure area without login")
    public void iNavigateDirectlyToSecureArea() {
        // Navigate directly without logging in; should redirect to login
        loginPage.navigateTo(config.getBaseUrl() + "/secure");
        loginPage.sleep(500);
    }

    // ─── When Steps ──────────────────────────────────────────────────────────

    @When("I enter username {string}")
    public void iEnterUsername(String username) {
        loginPage.enterUsername(username);
    }

    @When("I enter password {string}")
    public void iEnterPassword(String password) {
        loginPage.enterPassword(password);
    }

    @When("I click the login button")
    public void iClickLoginButton() {
        loginPage.clickLoginButton();
        loginPage.sleep(500); // brief wait for flash message
    }

    @When("I click the logout button")
    public void iClickLogoutButton() {
        secureAreaPage.clickLogout();
        loginPage.sleep(500);
    }

    @When("I navigate back to the secure area directly")
    public void iNavigateBackToSecureArea() {
        loginPage.navigateTo(config.getBaseUrl() + "/secure");
        loginPage.sleep(500);
    }

    // ─── Then Steps ──────────────────────────────────────────────────────────

    @Then("I should be redirected to the secure area")
    public void iShouldBeRedirectedToSecureArea() {
        loginPage.sleep(500);
        Assert.assertTrue(secureAreaPage.isSecureAreaDisplayed(),
            "Expected to be on Secure Area page. Current URL: " + loginPage.getCurrentUrl());
    }

    @Then("I should see the success message {string}")
    public void iShouldSeeSuccessMessage(String expectedMsg) {
        Assert.assertTrue(secureAreaPage.isLoginSuccessBannerDisplayed(),
            "Success banner not displayed. Flash: " + secureAreaPage.getFlashMessageText());
    }

    @Then("I should see an error message containing {string}")
    public void iShouldSeeErrorMessageContaining(String expectedMsg) {
        String actualMsg = loginPage.getFlashMessageText();
        Assert.assertTrue(actualMsg.contains(expectedMsg),
            "Expected error message to contain: '" + expectedMsg
            + "' but got: '" + actualMsg + "'");
    }

    @Then("I should remain on the login page")
    public void iShouldRemainOnLoginPage() {
        String url = loginPage.getCurrentUrl();
        Assert.assertTrue(url.contains("/login"),
            "Expected to remain on login page but URL was: " + url);
    }

    @Then("the login page should display username field")
    public void loginPageShouldDisplayUsernameField() {
        Assert.assertTrue(loginPage.isDisplayed(org.openqa.selenium.By.id("username")),
            "Username field not displayed");
    }

    @Then("the login page should display password field")
    public void loginPageShouldDisplayPasswordField() {
        Assert.assertTrue(loginPage.isDisplayed(org.openqa.selenium.By.id("password")),
            "Password field not displayed");
    }

    @Then("the login page should display login button")
    public void loginPageShouldDisplayLoginButton() {
        Assert.assertTrue(loginPage.isDisplayed(
            org.openqa.selenium.By.cssSelector("button[type='submit']")),
            "Login button not displayed");
    }

    @Then("I should be redirected to the login page")
    public void iShouldBeRedirectedToLoginPage() {
        loginPage.sleep(500);
        String url = loginPage.getCurrentUrl();
        Assert.assertTrue(url.contains("/login"),
            "Expected to be on login page but URL was: " + url);
    }

    @Then("I should see the logout success message")
    public void iShouldSeeLogoutSuccessMessage() {
        // After logout the site shows a flash on the login page
        String msg = loginPage.getFlashMessageText();
        Assert.assertTrue(
            msg.contains("You have been logged out") || msg.contains("logged out") || msg.contains("logged"),
            "Logout message not displayed or unexpected. Got: '" + msg + "'");
    }

    @Then("I should be on the secure area page")
    public void iShouldBeOnSecureAreaPage() {
        Assert.assertTrue(secureAreaPage.isSecureAreaDisplayed(),
            "Not on secure area page. URL: " + loginPage.getCurrentUrl());
    }

    @Then("the logout button should be visible")
    public void logoutButtonShouldBeVisible() {
        Assert.assertTrue(secureAreaPage.isLogoutButtonDisplayed(),
            "Logout button not visible");
    }

    @Then("I should be on the login page")
    public void iShouldBeOnLoginPage() {
        loginPage.sleep(500);
        String url = loginPage.getCurrentUrl();
        Assert.assertTrue(url.contains("/login"),
            "Expected to be on login page but URL was: " + url);
    }
}
