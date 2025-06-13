
Feature: Closing Payment Details Modal
  As a user
  I want to verify payment modal behavior when closing
  So that I understand how form data is retained

  Background:
    Given I am on the coffee cart menu page
    And I have selected an Espresso drink
    And I have opened the payment modal

  @issue-171 @owner-nataliia-hrusha
  Scenario: Verify that the Payment Details Modal retains pre-filled values after closing via the 'X' icon on the MenuPage
    When I fill in name and email fields
    And I mark the checkbox
    And I close the payment modal
    And I reopen the payment modal
    Then the name field should retain its value
    And the email field should retain its value
    And the checkbox should remain checked
    When I close the payment modal
    And I navigate to the cart page
    And I open the payment modal from cart
    Then the name field should be empty
    And the email field should be empty
    And the checkbox should be unchecked
