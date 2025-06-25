package com.coffeecart.cucumber.steps;

import com.coffeecart.cucumber.hooks.Hooks;
import com.coffeecart.ui.component.CartComponent;
import com.coffeecart.ui.component.HeaderComponent;
import com.coffeecart.ui.component.ShortItemComponent;
import com.coffeecart.ui.elements.TotalButtonElement;
import com.coffeecart.ui.page.MenuPage;
import io.cucumber.java.en.*;
import org.testng.asserts.SoftAssert;

public class VerificationOfPopUpCartFunctioningSteps {
    private final Hooks hooks;
    private MenuPage menuPage;
    private TotalButtonElement totalButtonElement;
    private CartComponent cartComponent;
    private ShortItemComponent item;
    private SoftAssert softAssert;

    public VerificationOfPopUpCartFunctioningSteps(Hooks hooks) {
        this.hooks = hooks;
        this.softAssert = hooks.getSoftAssert();
        this.menuPage = new MenuPage(hooks.getDriver()); // Ініціалізуємо тут
    }

    @Given("Coffee Cart page is opened")
    public void coffeeCartMenuPageIsOpened() {
        softAssert = hooks.getSoftAssert();
    }
    @And("I see the initial cart counter value")
    public void getInitialCartCounter() {
        HeaderComponent header = menuPage.getHeader();
        int initialCounter = header.getTotalNumberItemsFromCartLink();
        softAssert.assertEquals(initialCounter, 0, "Initial counter should be 0");
    }


    @When("I click on the drink {string}")
    public void clickOnDrink(String drinkName) {
        menuPage.clickDrink(drinkName);
        totalButtonElement = menuPage.getButtonElement();
        cartComponent = menuPage.getCartPreview(totalButtonElement.getTotalButton());
        item = cartComponent.findItemByName(drinkName);
        softAssert.assertNotNull(item, "Item should appear in the pop-up cart after adding");
    }

    @Then("the cart pop-up should display the item {string} with quantity 1")
    public void verifyCartPopupDisplaysDrinkWithQuantityOne(String drinkName) {
        softAssert.assertEquals(item.getCount(), 1, "Quantity should be 1 after adding");
    }

    @When("I increase the quantity of {string} by clicking \"+\"")
    public void increaseQuantity(String drinkName) {
        item.clickPlus();
    }

    @Then("the cart should display quantity 2 for {string}")
    public void verifyQuantityIsTwo(String drinkName) {
        softAssert.assertEquals(item.getCount(), 2, "Quantity should be 2 after increment");
    }

    @When("I decrease the quantity of {string} by clicking \"-\"")
    public void decreaseQuantity(String drinkName) {
        item.clickMinus();
    }

    @Then("the cart should display quantity 1 for {string}")
    public void verifyQuantityIsOne(String drinkName) {
        softAssert.assertEquals(item.getCount(), 1, "Quantity should be 1 after decrement");
    }

    @When("I decrease the quantity of {string} by clicking \"-\" again")
    public void decreaseQuantityToZero(String drinkName) {
        item.clickMinus();
    }

    @Then("the total price in the cart should be \"Total: $0.00\"")
    public void verifyTotalPriceIsZero() {
        String totalText = totalButtonElement.getTotalButton().getText();
        softAssert.assertEquals(totalText, "Total: $0.00", "Total price should be $0.00 after removing the item");
        softAssert.assertAll();
    }
}




