package com.coffeecart.ui.discardLuckyProposition;

import com.coffeecart.data.DrinkEnum;
import com.coffeecart.ui.BaseTest;
import com.coffeecart.ui.component.ShortItemComponent;
import com.coffeecart.ui.page.MenuPage;
import com.coffeecart.ui.testrunners.BaseTestRunner;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class DiscardLuckyPropositionTest extends BaseTest {

    @Test
    public void testLuckyPropositionDiscard() {
        DrinkEnum flatWhite = DrinkEnum.FLAT_WHITE;
        DrinkEnum americano = DrinkEnum.AMERICANO;
        DrinkEnum espressoMacchiato = DrinkEnum.ESPRESSO_MACCHIATO;
        DrinkEnum espresso = DrinkEnum.ESPRESSO;
        DrinkEnum discountedDrink = DrinkEnum.MOCHA;

        MenuPage menuPage = new MenuPage(driver);

        menuPage.clickDrink(flatWhite.getRecipe().getName())
                .clickDrink(flatWhite.getRecipe().getName())
                .clickDrink(flatWhite.getRecipe().getName())
                .getLuckyDayComponent()
                .clickSkip()
                .getButtonElement()
                .hoverTotalButton()
                .getShortItems().stream()
                .filter(item -> item.getName().equals(flatWhite.getRecipe().getName()))
                .findFirst()
                .ifPresent(item -> item.clickMinus().clickMinus().clickMinus());

        menuPage.clickDrink(americano.getRecipe().getName())
                .clickDrink(espressoMacchiato.getRecipe().getName())
                .clickDrink(espresso.getRecipe().getName())
                .getLuckyDayComponent()
                .clickSkip();

        List<String> expected = new ArrayList<>(List.of(
                americano.getRecipe().getName(),
                espressoMacchiato.getRecipe().getName(),
                espresso.getRecipe().getName()
        ));

        List<String> actual = menuPage.getButtonElement()
                .hoverTotalButton()
                .getShortItems().stream()
                .map(ShortItemComponent::getName)
                .collect(Collectors.toList());

        Collections.sort(expected);
        Collections.sort(actual);

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(actual, expected, "Cart contains unexpected drinks");
        softAssert.assertFalse(actual.contains(discountedDrink.getRecipe().getName()), "Mocha should NOT be in the cart");

        softAssert.assertAll();
    }
}
