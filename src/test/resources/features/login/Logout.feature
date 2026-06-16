# ============================================================
# Feature: Logout Functionality
# URL: https://the-internet.herokuapp.com/secure
# ============================================================

@Regression @Logout
Feature: Logout Functionality
  As a logged-in user
  I want to log out from the secure area
  So that my session is terminated securely

  Background:
    Given I am logged in as "tomsmith" with password "SuperSecretPassword!"

  # ──────────────────────────────────────────────────────────
  # POSITIVE SCENARIOS
  # ──────────────────────────────────────────────────────────

  @Smoke @Positive
  Scenario: Successful logout from secure area
    When I click the logout button
    Then I should be redirected to the login page
    And I should see the logout success message

  @Positive
  Scenario: Verify secure area is displayed before logout
    Then I should be on the secure area page
    And the logout button should be visible

  @Positive
  Scenario: Cannot access secure area after logout
    When I click the logout button
    And I navigate back to the secure area directly
    Then I should be redirected to the login page

  # ──────────────────────────────────────────────────────────
  # NEGATIVE SCENARIOS
  # ──────────────────────────────────────────────────────────

  @Negative
  Scenario: Accessing secure area without login redirects to login page
    Given I navigate directly to the secure area without login
    Then I should be on the login page
