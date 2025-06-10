Feature: Lucky Day Offer Rejection
  As a user
  I want to reject the "Lucky Day" offer
  So that my cart does not include any unwanted items

  Background:
    Given I am on the menu page

  @issue-192 @owner-dmytro-zubenko @regression
  Scenario: Verify rejecting the "extra cup of Mocha" keeps cart unchanged
    When I click on "Flat White" drink 3 times
    Then I verify "Lucky Day" modal appears with extra cup of Mocha
    When I choose to skip the lucky day offer
    Then I verify Mocha is not in the cart
    When I clear the cart
    And I click on the following drinks:
      | Americano         |
      | Espresso Macchiato|
      | Espresso          |
    Then I verify "Lucky Day" modal appears with extra cup of Mocha
    When I choose to skip the lucky day offer
    Then I verify Mocha is not in the cart
    And I verify the following drinks are in the cart:
      | Americano         |
      | Espresso Macchiato|
      | Espresso          |
