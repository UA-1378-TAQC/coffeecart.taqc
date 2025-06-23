
Feature: Discounted Mocha Removal
  As a user
  I want to verify discounted mocha behavior when removing other items
  So that I understand the special offer rules

  @issue-31 @owner-nataliia-hrusha
  Scenario: Verify Discounted Mocha disappears after removing other items
    Given I am on the menu page
    When I click on "Espresso" drink 3 times
    Then I verify "Lucky Day" modal appears with extra cup of Mocha
    When I click on "Yes, of course!" button
    And I verify cart counter shows "4" items
    And I go to cart page
    And I verify discounted mocha is present in cart with quantity 1
    When I remove all non-discounted items from cart
    Then I verify "Discounted Mocha" is not present in cart
    And I verify total button is not displayed
    And I verify empty cart message is displayed
    And I verify cart counter shows "0" items
