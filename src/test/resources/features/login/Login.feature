# ============================================================
# Feature: Login Functionality
# URL: https://the-internet.herokuapp.com/login
# Valid credentials: tomsmith / SuperSecretPassword!
# ============================================================

@Regression @Login
Feature: Login Functionality
  As a registered user
  I want to log in to the secure area
  So that I can access protected content

  Background:
    Given I navigate to the login page

  # ──────────────────────────────────────────────────────────
  # POSITIVE SCENARIOS
  # ──────────────────────────────────────────────────────────

  @Smoke @Positive
  Scenario: Successful login with valid credentials
    When I enter username "tomsmith"
    And I enter password "SuperSecretPassword!"
    And I click the login button
    Then I should be redirected to the secure area
    And I should see the success message "You logged into a secure area!"

  @Positive
  Scenario Outline: Login with multiple valid credential sets
    When I enter username "<username>"
    And I enter password "<password>"
    And I click the login button
    Then I should be redirected to the secure area

    Examples:
      | username  | password             |
      | tomsmith  | SuperSecretPassword! |

  @Positive
  Scenario: Verify page elements on login page
    Then the login page should display username field
    And the login page should display password field
    And the login page should display login button

  # ──────────────────────────────────────────────────────────
  # NEGATIVE SCENARIOS
  # ──────────────────────────────────────────────────────────

  @Negative
  Scenario: Login fails with invalid username
    When I enter username "invalidUser"
    And I enter password "SuperSecretPassword!"
    And I click the login button
    Then I should see an error message containing "Your username is invalid"
    And I should remain on the login page

  @Negative
  Scenario: Login fails with invalid password
    When I enter username "tomsmith"
    And I enter password "wrongPassword"
    And I click the login button
    Then I should see an error message containing "Your password is invalid"
    And I should remain on the login page

  @Negative
  Scenario: Login fails with empty credentials
    When I enter username ""
    And I enter password ""
    And I click the login button
    Then I should see an error message containing "Your username is invalid"

  @Negative
  Scenario: Login fails with empty username
    When I enter username ""
    And I enter password "SuperSecretPassword!"
    And I click the login button
    Then I should see an error message containing "Your username is invalid"

  @Negative
  Scenario: Login fails with empty password
    When I enter username "tomsmith"
    And I enter password ""
    And I click the login button
    Then I should see an error message containing "Your password is invalid"
