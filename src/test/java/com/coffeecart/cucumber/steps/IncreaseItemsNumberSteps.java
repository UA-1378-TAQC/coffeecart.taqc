package com.coffeecart.cucumber.steps;

import com.coffeecart.cucumber.hooks.Hooks;
import com.coffeecart.ui.component.FullItemComponent;
import com.coffeecart.ui.component.HeaderComponent;
import com.coffeecart.ui.page.CartPage;
import com.coffeecart.ui.page.MenuPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.asserts.SoftAssert;

import java.util.Optional;

public class IncreaseItemsNumberSteps {
    private final Hooks hooks;
    private MenuPage menuPage;
    private CartPage cartPage;
    private FullItemComponent item;
    private SoftAssert softAssert;

    public IncreaseItemsNumberSteps(Hooks hooks) {
        this.hooks = hooks;
        this.softAssert = hooks.getSoftAssert();
    }

    @Given("I am on menu page")
    public void iAmOnCoffeeCartMenuPage() {
        menuPage = new MenuPage(hooks.getDriver());
    }

    @When("I click {string}")
    public void iClickEspressoMacchiato(String drinkName) {
        menuPage.clickDrink(drinkName);
    }

    @Then("I verify {string} is in the cart")
    public void iVerifyEspressoMacchiatoIsInTheCart(String drinkName) {
        cartPage = menuPage.goToCartPage();
        Optional<FullItemComponent> optionalItem = cartPage.getFullItems().stream()
                .filter(item -> item.getItemLabelString().equals(drinkName))
                .findFirst();
        softAssert.assertTrue(optionalItem.isPresent(), "The drink '" + drinkName + "' should be present in the cart");
        item = optionalItem.get();
    }

    @When("I click the add button for {string}")
    public void iClickTheAddButtonForEspressoMacchiato(String drinkName) {
        item.clickOnAddButton();
    }

    @Then("I verify the item count is {string}")
    public void iVerifyTheItemCountIs(String expectedCount) {
        softAssert.assertEquals(item.getCount(), Integer.parseInt(expectedCount), "Item count should be " + expectedCount + " after adding");
    }

    @And("I verify the unit description is {string}")
    public void iVerifyTheUnitDescriptionIs(String expectedUnitDescription) {
        softAssert.assertEquals(item.getUnitDescString(), "$12.00 x 2", "Unit description should match expected format");
    }

    @And("I verify the cart count in the header is {string}")
    public void iVerifyTheCartCountInTheHeaderIs(String expectedCartCount) {
        HeaderComponent header = cartPage.getHeader();
        int actualCartCount = header.getTotalNumberItemsFromCartLink();
        softAssert.assertEquals(actualCartCount, Integer.parseInt(expectedCartCount), "Header cart count should reflect " + expectedCartCount + " items");
    }

}
