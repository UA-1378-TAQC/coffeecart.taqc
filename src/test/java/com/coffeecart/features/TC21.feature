Feature: '+' button is disabled for 'Discounted' Mocha in the Cart Modal
    Verify '+' button is disabled for '(Discounted) Mocha' in the Cart Modal 

    Background:
        Given I am on the coffee cart menu page
        Given I am on the menu page

    @issue-21 @owner-dmytro-slobodianiuk
    Scenario: Verify '+' button disability in the Cart Modal for 'Discounted' Mocha
        When I select an Espresso drink
        And I select an Espresso drink
        And I select an Espresso drink

        Then I verify Lucky Day option appear

        When I click on "Yes, of course!" button
        And I hover total button on menu page

        Then I verify is dicounted mocha in cart
        And I verify "+" button should be disabled for Discounted Mocha

        When I click on "+" button on discounted mocha
        And I verify sum
