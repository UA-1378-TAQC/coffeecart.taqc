Feature: Discounted Mocha removal via “-” button
  As a user
  I want the (Discounted) Mocha to disappear
  When I remove the other cups with the minus button
  So that I understand the Lucky-Day offer rules

  @issue-37 @owner-roman-fedko @pri:high @type:UI
  Scenario: (Discounted) Mocha disappears after “–”-removal of the other items
    Given I am on the menu page
    When  I click on "Espresso" drink 3 times
    Then  I verify "Lucky Day" modal appears with extra cup of Mocha
    When  I click on "Yes, of course!" button
    And   I verify cart counter shows "4" items
    And   I go to cart page
    And   I verify discounted mocha is present in cart with quantity 1
    When  I minus all non-discounted items from cart
    Then  I verify "(Discounted) Mocha" is not present in cart
    And   I verify total button is not displayed
    And   I verify empty cart message is displayed
    And   I verify cart counter shows "0" items
