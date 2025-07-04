package com.coffeecart.ui;

import com.coffeecart.data.DrinkEnum;
import com.coffeecart.ui.component.ShortItemComponent;
import com.coffeecart.ui.page.MenuPage;
import com.coffeecart.ui.testrunners.BaseTestRunner;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Owner;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.*;

public class DiscardLuckyPropositionTest extends BaseTestRunner {
    @Test
    @Description("Verify that lucky proposition drink is not added to cart after refusing from it")
    @Feature("Verify lucky proposition discard")
    @Issue("10")
    @Owner("Dmytro Zubenko")
    public void testLuckyPropositionDiscard() {
        MenuPage menuPage = new MenuPage(driver);
        menuPage.clickDrink(DrinkEnum.getName(DrinkEnum.FLAT_WHITE))
                .clickDrink(DrinkEnum.getName(DrinkEnum.FLAT_WHITE))
                .clickDrink(DrinkEnum.getName(DrinkEnum.FLAT_WHITE))
                .getLuckyDayComponent()
                .clickSkip()
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

        menuPage.clickDrink(DrinkEnum.getName(DrinkEnum.AMERICANO))
                .clickDrink(DrinkEnum.getName(DrinkEnum.ESPRESSO_MACCHIATO))
                .clickDrink(DrinkEnum.getName(DrinkEnum.ESPRESSO))
                .getLuckyDayComponent()
                .clickSkip();

        List<String> expected = new ArrayList<>(List.of(
                DrinkEnum.getName(DrinkEnum.AMERICANO),
                DrinkEnum.getName(DrinkEnum.ESPRESSO_MACCHIATO),
                DrinkEnum.getName(DrinkEnum.ESPRESSO)
        ));

        List<String> actual = menuPage.getButtonElement()
                .hoverTotalButton()
                .getShortItems()
                .stream()
                .map(ShortItemComponent::getName)
                .toList();

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEqualsNoOrder(actual.toArray(), expected.toArray(),
                "Cart contains unexpected drinks (order doesn't matter)");
        softAssert.assertFalse(actual.contains(DrinkEnum.getName(DrinkEnum.MOCHA)),
                "Mocha should NOT be in the cart");

        softAssert.assertAll();
    }
}
