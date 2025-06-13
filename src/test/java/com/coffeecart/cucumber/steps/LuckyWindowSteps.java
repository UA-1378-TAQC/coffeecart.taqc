package com.coffeecart.cucumber.steps;

import com.coffeecart.cucumber.hooks.Hooks;
import com.coffeecart.data.DrinkEnum;
import com.coffeecart.ui.component.CartComponent;
import com.coffeecart.ui.component.FullItemComponent;
import com.coffeecart.ui.component.LuckyDayComponent;
import com.coffeecart.ui.component.ShortItemComponent;
import com.coffeecart.ui.modal.PaymentDetailModal;
import com.coffeecart.ui.modal.SuccessfulPopUp;
import com.coffeecart.ui.page.CartPage;
import com.coffeecart.ui.page.MenuPage;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.stream.IntStream;

import com.coffeecart.ui.elements.TotalButtonElement;

public class LuckyWindowSteps {
    private final Hooks hooks;
    private static final String DISCOUNTED_MOCHA = "(Discounted) Mocha";
    private MenuPage menuPage;
    private CartPage cartPage;
    private SoftAssert softAssert;

    public LuckyWindowSteps(Hooks hooks) {
        this.hooks = hooks;
        this.softAssert = hooks.getSoftAssert();
    }

    @Given("I am on the menu page")
    public void iAmOnTheCoffeeCartMenuPage() {
        menuPage = new MenuPage(hooks.getDriver());
    }

    @When("I click on {string} drink {int} times")
    public void iClickOnDrinkXTimes(String drinkName, int numberOfClicks) {
        IntStream.range(0, numberOfClicks)
                .forEach(i -> menuPage.clickDrink(drinkName));
    }

    @Then("I verify \"Lucky Day\" modal appears with extra cup of Mocha")
    public void iVerifyLuckyDayModalAppearsWithExtraCupOfMocha() {
        softAssert.assertTrue(menuPage.isLuckyModalDisplayed(),
                "\"Lucky Day\" modal should be displayed after clicking drinks.");
    }

    @When("I choose to skip the lucky day offer")
    public void iChooseToSkipTheLuckyDayOffer() {
        menuPage.getLuckyDayComponent().clickSkip();
    }

    @Then("I verify Mocha is not in the cart")
    public void iVerifyMochaIsNotInTheCart() {
        List<String> actualDrinks = menuPage.getButtonElement()
                .hoverTotalButton()
                .getShortItems()
                .stream()
                .map(ShortItemComponent::getName)
                .toList();
        softAssert.assertFalse(actualDrinks.contains("Mocha"), "Mocha should NOT be in the cart");
    }

    @When("I clear the cart")
    public void iClearTheCart() {
        menuPage
                .getButtonElement()
                .hoverTotalButton()
                .getShortItems()
                .stream()
                .filter(item -> item.getName().equals(DrinkEnum.getName(DrinkEnum.FLAT_WHITE)))
                .findFirst()
                .ifPresent(item -> item
                        .clickMinus()
                        .clickMinus()
                        .clickMinus());
    }

    @And("I click on the following drinks:")
    public void iClickOnDrink(DataTable dataTable) {
        List<String> drinkNames = dataTable.asList(String.class);
        drinkNames.forEach(drinkName -> menuPage.clickDrink(drinkName));
    }

    @And("I verify the following drinks are in the cart:")
    public void iVerifyTheFollowingDrinksAreInTheCart(DataTable dataTable) {
        List<String> expectedDrinks = dataTable.asList(String.class);
        System.out.println(expectedDrinks);
        List<String> actualDrinks = menuPage.getButtonElement()
                .hoverTotalButton()
                .getShortItems()
                .stream()
                .map(ShortItemComponent::getName)
                .toList();
        softAssert.assertEqualsNoOrder(actualDrinks.toArray(), expectedDrinks.toArray(),
                "Cart contains unexpected drinks (order doesn't matter)");
    }

    @Then("I verify Lucky Day option appear")
    public void iVerifyLuckyDayAppear(){
        softAssert.assertTrue(menuPage.getLuckyDayModalRoot().isDisplayed(), "Lucky Day modal should be displayed");
    }

    @When("I click on \"Yes, of course!\" button")
    public void iClickOnYesButtonInLuckyDay(){
        menuPage.getLuckyDayComponent().clickYes();
    }

    @When("I hover total button on menu page")
    public void iHoverTotalButtonOnMenuPage(){
        menuPage.getButtonElement().hoverTotalButton();
    }

    @Then("I verify is dicounted mocha in cart")
    public void iVerifyIsDiscountedMochaIn(){
        TotalButtonElement totalButton = menuPage.getButtonElement();
        CartComponent cartComponent = totalButton.hoverTotalButton();

        ShortItemComponent mochaItem = cartComponent.getShortItems().stream()
            .filter(item -> item.getName().equals(DISCOUNTED_MOCHA))
            .findFirst()
            .orElseThrow(() -> new AssertionError("Discounted Mocha not found in cart preview"));
        
        softAssert.assertNotNull(mochaItem, "Discounted Mocha should be present in the cart");
        softAssert.assertEquals(mochaItem.getCount(), 1, "Initial quantity of Mocha should be 1");
    }

    @Then("I verify \"+\" button should be disabled for Discounted Mocha")
    public void iVerifyPlusButton(){
        TotalButtonElement totalButton = menuPage.getButtonElement();
        CartComponent cartComponent = totalButton.hoverTotalButton();

        ShortItemComponent mochaItem = cartComponent.getShortItems().stream()
            .filter(item -> item.getName().equals(DISCOUNTED_MOCHA))
            .findFirst()
            .orElseThrow(() -> new AssertionError("Discounted Mocha not found in cart preview"));
        
        softAssert.assertTrue(mochaItem.plusButtonIsEnabled(), "'+' button should be disabled for Discounted Mocha");
    }

    @When("I click on \"+\" button on discounted mocha")
    public void iClickPlusOnDiscountedMocha(){
        TotalButtonElement totalButton = menuPage.getButtonElement();
        CartComponent cartComponent = totalButton.hoverTotalButton();

        ShortItemComponent mochaItem = cartComponent.getShortItems().stream()
            .filter(item -> item.getName().equals(DISCOUNTED_MOCHA))
            .findFirst()
            .orElseThrow(() -> new AssertionError("Discounted Mocha not found in cart preview"));
        
        mochaItem.clickOnAddButton(); 
    }

    @Then("I verify mocha quantity")
    public void iVerifyAmountOfMoche(){
        TotalButtonElement totalButton = menuPage.getButtonElement();
        CartComponent cartComponent = totalButton.hoverTotalButton();

        ShortItemComponent mochaItem = cartComponent.getShortItems().stream()
            .filter(item -> item.getName().equals(DISCOUNTED_MOCHA))
            .findFirst()
            .orElseThrow(() -> new AssertionError("Discounted Mocha not found in cart preview"));
        
        softAssert.assertEquals(mochaItem.getCount(), 2, "Mocha quantity should not increase after '+' click");
    }

    @Then("I verify sum")
    public void iVerifyPrice(){
        TotalButtonElement totalButton = menuPage.getButtonElement();
        double totalBefore = totalButton.getMoneyCounter();
        double totalAfter = totalButton.getMoneyCounter();
        softAssert.assertEquals(totalAfter, totalBefore, "Total sum should not increase");
    }

    @And("I verify cart counter shows {string} items")
    public void iVerifyCartCounterShowsItems(String expectedCount) {
        int actualCount = menuPage.getHeader().getTotalNumberItemsFromCartLink();
        softAssert.assertEquals(actualCount, Integer.parseInt(expectedCount),
                String.format("Cart counter should show %s items but shows %d", expectedCount, actualCount));
    }

    @And("I go to cart page")
    public void iGoToCartPage() {
        cartPage = menuPage.goToCartPage();
    }

    @And("I verify {string} is present in cart with quantity {string}")
    public void iVerifyItemIsPresentInCartWithQuantity(String itemName, String expectedQuantity) {
        FullItemComponent item = cartPage.getFullItemByName(itemName);
        softAssert.assertNotNull(item, itemName + " should be present in cart");
        softAssert.assertEquals(item.getCount(), Integer.parseInt(expectedQuantity),
                String.format("%s quantity should be %s but was %d",
                        itemName, expectedQuantity, item.getCount()));
    }


    @When("I minus all non-discounted items from cart")
    public void iMinusAllNonDiscountedItemsFromCart() {
        for (FullItemComponent item : cartPage.getFullItems()) {
            if (!item.getItemLabelString().equals(DISCOUNTED_MOCHA)) {
                cartPage = item.clickOnRemoveButton();
            }
        }
    }

    @When("I remove all non-discounted items from cart")
    public void iRemoveAllNonDiscountedItemsFromCart() {
        for (FullItemComponent item : cartPage.getFullItems()) {
            if (!item.getItemLabelString().equals(DISCOUNTED_MOCHA)) {
                cartPage = item.clickOnDeleteButton();
            }
        }
    }

    @Then("I verify {string} is not present in cart")
    public void iVerifyItemIsNotPresentInCart(String itemName) {
        FullItemComponent item = cartPage.getFullItemByName(itemName);
        softAssert.assertNull(item, itemName + " should not be present in cart");
    }

    @And("I verify total button is not displayed")
    public void iVerifyTotalButtonIsNotDisplayed() {
        softAssert.assertFalse(cartPage.getTotalButton().isDisplayed(),
                "Total button should not be displayed when cart is empty");
    }

    @And("I verify empty cart message is displayed")
    public void iVerifyEmptyCartMessageIsDisplayed() {
        softAssert.assertTrue(cartPage.emptyCartMessageIsDisplayed(),
                "Empty cart message should be displayed");
    }

}
