package com.coffeecart.cucumber.steps;

import com.coffeecart.cucumber.hooks.Hooks;
import com.coffeecart.data.DrinkEnum;
import com.coffeecart.ui.component.ShortItemComponent;
import com.coffeecart.ui.page.MenuPage;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.stream.IntStream;

public class LuckyWindowSteps {
    private final Hooks hooks;
    private MenuPage menuPage;
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
}


