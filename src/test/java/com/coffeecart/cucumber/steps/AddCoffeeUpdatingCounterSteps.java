package com.coffeecart.cucumber.steps;

import com.coffeecart.cucumber.hooks.Hooks;
import com.coffeecart.ui.component.HeaderComponent;
import com.coffeecart.ui.elements.TotalButtonElement;
import com.coffeecart.ui.page.MenuPage;
import io.cucumber.java.en.*;
import org.testng.asserts.SoftAssert;
public class AddCoffeeUpdatingCounterSteps {
    private final Hooks hooks;
    private  MenuPage menuPage;
    private HeaderComponent header;
    private SoftAssert softAssert;
    private TotalButtonElement totalButtonElement;
    private int initialCounter = 0;
    private int expectedCounter = 0;

    public AddCoffeeUpdatingCounterSteps(Hooks hooks) { this.hooks = hooks; }

    @Given("Coffee cart menu page is opened")
    public void CoffeeCartMenuPageIsOpened() {
        menuPage = new MenuPage(hooks.getDriver());
        softAssert = hooks.getSoftAssert();
        totalButtonElement = menuPage.getButtonElement();
        header = menuPage.getHeader();
    }

    @And("I note the initial cart counter value")
    public void getInitialCounter() {
        initialCounter = header.getTotalNumberItemsFromCartLink();
        expectedCounter = initialCounter;
        softAssert.assertEquals(initialCounter, 0, "Initial counter should be 0");
    }

    @When("I click on the coffee cup icon number {int}")
    public void clickCoffeeCupIcon(int index) {
        menuPage.getCards().get(index - 1).clickCup();
        expectedCounter++;
    }

    @Then("the cart counter should be increased by 1")
    public void verifyCounterIncreasedByOne() {
        int actual = header.getTotalNumberItemsFromCartLink();
        softAssert.assertEquals(actual, expectedCounter, "Cart counter not incremented correctly");
    }

    @Then("the cart counter should show {int}")
    public void verifyCounterIs(int expectedValue) {
        int actual = header.getTotalNumberItemsFromCartLink();
        softAssert.assertEquals(actual, expectedValue, "Final cart counter mismatch");
        softAssert.assertAll();
    }
}
