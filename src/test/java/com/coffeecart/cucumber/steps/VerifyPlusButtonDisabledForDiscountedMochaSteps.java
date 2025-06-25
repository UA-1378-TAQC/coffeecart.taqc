package com.coffeecart.cucumber.steps;

import com.coffeecart.cucumber.hooks.Hooks;
import com.coffeecart.ui.component.FullItemComponent;
import com.coffeecart.ui.page.CartPage;
import com.coffeecart.ui.page.MenuPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class VerifyPlusButtonDisabledForDiscountedMochaSteps {
    private final Hooks hooks;
    private MenuPage menuPage;
    private CartPage cartPage;
    private FullItemComponent discountedMocha;
    private int quantityBefore;
    private double totalSumBefore;
    private int cartCounterBefore;
    private final SoftAssert softAssert;

    public VerifyPlusButtonDisabledForDiscountedMochaSteps(Hooks hooks) {
        this.hooks = hooks;
        this.softAssert = hooks.getSoftAssert();
    }

    @Given("I am on the menu page")
    public void iAmOnTheMenuPage() {
        menuPage = new MenuPage(hooks.getDriver());
    }

    @When("I click on any coffee cup")
    public void iClickOnAnyCoffeeCup() {
        menuPage.clickDrink("Espresso");
    }

    @Then("I verify the cart counter in header is {string}")
    public void iVerifyCartCounterInHeaderIs(String expected) {
        int actual = menuPage.getHeader().getTotalNumberItemsFromCartLink();
        softAssert.assertEquals(actual, Integer.parseInt(expected), "Cart counter in header");
    }

    @And("I verify Lucky Day modal appears")
    public void iVerifyLuckyDayModalAppears() {
        softAssert.assertTrue(menuPage.getLuckyDayModalRoot().isDisplayed(), "Lucky Day modal should be displayed");
    }

    @When("I click \"Yes, of course!\" in Lucky Day modal")
    public void iClickYesOfCourseInLuckyDayModal() {
        menuPage.getLuckyDayComponent().clickYes();
    }

    @Then("I verify the cart counter in header is {string}")
    public void iVerifyCartCounterAgain(String expected) {
        int actual = menuPage.getHeader().getTotalNumberItemsFromCartLink();
        softAssert.assertEquals(actual, Integer.parseInt(expected), "Cart counter in header");
    }

    @And("I verify Lucky Day modal disappears")
    public void iVerifyLuckyDayModalDisappears() {
        softAssert.assertFalse(menuPage.getLuckyDayModalRoot().isDisplayed(), "Lucky Day modal should disappear");
    }

    @When("I go to the cart page")
    public void iGoToCartPage() {
        cartPage = menuPage.goToCartPage();
    }

    @Then("I should see \\(Discounted) Mocha and three other coffee cups in the cart")
    public void iShouldSeeDiscountedMochaAndOthers() {
        List<FullItemComponent> items = cartPage.getFullItems();
        long discountedMochaCount = items.stream().filter(i -> i.getItemLabelString().contains("(Discounted) Mocha")).count();
        softAssert.assertEquals(discountedMochaCount, 1, "(Discounted) Mocha should be present");
        softAssert.assertEquals(items.size(), 4, "There should be 4 items in the cart");
        discountedMocha = items.stream().filter(i -> i.getItemLabelString().contains("(Discounted) Mocha")).findFirst().orElse(null);
    }

    @And("I verify the quantity of \\(Discounted) Mocha is {string}")
    public void iVerifyQuantityOfDiscountedMochaIs(String expectedQty) {
        softAssert.assertNotNull(discountedMocha, "(Discounted) Mocha must exist in cart");
        softAssert.assertEquals(discountedMocha.getCount(), Integer.parseInt(expectedQty), "Discounted Mocha quantity");
        quantityBefore = discountedMocha.getCount();
    }

    @And("I verify the price of \\(Discounted) Mocha is {string}")
    public void iVerifyPriceOfDiscountedMochaIs(String expectedPrice) {
        softAssert.assertTrue(discountedMocha.getUnitDescString().contains(expectedPrice), "Discounted Mocha price");
    }

    @And("I verify the plus button for \\(Discounted) Mocha is disabled")
    public void iVerifyPlusButtonForDiscountedMochaIsDisabled() {
        softAssert.assertFalse(discountedMocha.plusButtonIsEnabled(), "'+' button should be disabled for Discounted Mocha");
    }

    @When("I try to click the plus button for \\(Discounted) Mocha")
    public void iTryToClickPlusButtonForDiscountedMocha() {
        totalSumBefore = cartPage.getSumOfTotalPricesFromCart();
        cartCounterBefore = cartPage.getHeader().getTotalNumberItemsFromCartLink();
        try {
            discountedMocha.clickOnAddButton();
        } catch (Exception ignored) {}
    }

    @Then("I verify the plus button remains disabled")
    public void iVerifyPlusButtonRemainsDisabled() {
        softAssert.assertFalse(discountedMocha.plusButtonIsEnabled(), "'+' button should remain disabled");
    }

    @And("I verify the quantity of \\(Discounted) Mocha is still {string}")
    public void iVerifyQuantityStill(String expectedQty) {
        softAssert.assertEquals(discountedMocha.getCount(), Integer.parseInt(expectedQty), "Discounted Mocha quantity should not increase");
    }

    @And("I verify the total sum in the cart does not increase")
    public void iVerifyTotalSumNotIncrease() {
        double totalSumAfter = cartPage.getSumOfTotalPricesFromCart();
        softAssert.assertEquals(totalSumAfter, totalSumBefore, "Total sum should not increase");
    }

    @And("I verify the cart counter in header does not increase")
    public void iVerifyCartCounterHeaderNotIncrease() {
        int cartCounterAfter = cartPage.getHeader().getTotalNumberItemsFromCartLink();
        softAssert.assertEquals(cartCounterAfter, cartCounterBefore, "Cart counter in header should not increase");
        softAssert.assertAll();
    }
}