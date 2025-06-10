Feature: '+' button is disabled for 'Discounted' Mocha in the Cart Modal
    Verify '+' button is disabled for '(Discounted) Mocha' in the Cart Modal 

    Background:
        Given I am on the coffee cart menu page

    @issue-21 @owner-dmytro-slobodianiuk
    Scenario: Verify '+' button disability in the Cart Modal for 'Discounted' Mocha
        When I select an Espresso drink
        And I select an Espresso drink