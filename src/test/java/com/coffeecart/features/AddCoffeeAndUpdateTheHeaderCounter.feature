Feature: Verification that adding multiple coffee cups updates the header counter

  Background:
    Given Coffee cart menu page is opened

  @issue-1 @owner-viktoriia-fylyk
  Scenario: Add multiple drinks to the cart and verify counter
    And I note the initial cart counter value

    When I click on the coffee cup icon number 1
    Then the cart counter should be increased by 1

    When I click on the coffee cup icon number 2
    Then the cart counter should be increased by 1

    When I click on the coffee cup icon number 3
    And I click on the coffee cup icon number 4
    And I click on the coffee cup icon number 5
    Then the cart counter should show 5


#Feature: Verification that adding multiple coffee cups updates the header counter
#
#  Background:
#    Given Coffee Cart Menu page is opened
#
#  @issue-1 @owner-viktoriia-fylyk
#  Scenario: Add multiple drinks to the cart and verify counter
#    And I note the initial cart counter value
#
#    When I click on the coffee cup icon number 1 (Espresso)
#    Then the cart counter should be increased by 1
#
#    When I click on the coffee cup icon number 2 (Espresso Macchiato)
#    Then the cart counter should be increased by 1
#
#    When I click on the coffee cup icon number 3 (Cappuchino)
#    And I click on the coffee cup icon number 4 (Mocha)
#    And I click on the coffee cup icon number 5 (Flat White)
#    Then the cart counter should show 5
