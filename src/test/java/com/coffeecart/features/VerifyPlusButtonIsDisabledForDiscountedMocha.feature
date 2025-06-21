Feature: '+' button is disabled for (Discounted) Mocha in the Cart

  Background:
    Given I am on the menu page

  @issue-19 @owner-Roman-Kmet
  Scenario: '+' button for (Discounted) Mocha is disabled and does not allow increment
    When I click on "Espresso" drink 3 times
    Then I verify "Lucky Day" modal appears with extra cup of Mocha
    When I click on "Yes, of course!" button
    And I go to the cart page
    Then I verify Discounted Mocha and three other coffee cups are in the cart
    And I verify Discounted Mocha quantity is "1"
    And I verify Discounted Mocha price is "$2.99"
    And I verify "+" button should be disabled for Discounted Mocha
    When I click on "+" button on Discounted Mocha
    Then I verify "+" button should be disabled for Discounted Mocha
    And I verify Discounted Mocha quantity is "1"
    And I verify only one Discounted Mocha in the cart
    And I verify total sum in the cart is not increased
    And I verify the cart counter in the header is not increased