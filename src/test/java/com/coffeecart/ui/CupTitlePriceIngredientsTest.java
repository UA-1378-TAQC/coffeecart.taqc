package com.coffeecart.ui;

import com.coffeecart.data.DrinkEnum;
import com.coffeecart.dataprovider.CheckCupPriceCostAndIngredientsDataProvider;
import com.coffeecart.ui.component.CardComponent;
import com.coffeecart.ui.component.CupComponent;
import com.coffeecart.ui.page.MenuPage;
import com.coffeecart.ui.testrunners.BaseTestRunner;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CupTitlePriceIngredientsTest extends BaseTestRunner {
    private static final int expectedCount = 9;

    @Test
    @Description("Check length of all coffee cards on the menu page")
    @Feature("Coffee cards verification")
    @Issue("5")
    @Owner("Anatolii Orynchak")
    public void checkCardsLength(){
        SoftAssert softAssert = new SoftAssert();
        List<CardComponent> cards = new MenuPage(driver).getCards();
        softAssert.assertEquals(expectedCount, cards.size());
        softAssert.assertAll();
    }

    @Test(dataProvider = "checkCorrectCardTitlesDataProvider", dataProviderClass = CheckCupPriceCostAndIngredientsDataProvider.class)
    @Description("Check titles of all coffee cards on the menu page")
    @Feature("Coffee cards verification")
    @Issue("5")
    @Owner("Anatolii Orynchak")
    public void checkCorrectCardTitles(DrinkEnum drink){
        String cartTitle = DrinkEnum.getName(drink);
        List<CardComponent> cards = new MenuPage(driver).getCards();

        Assert.assertTrue(cards
                .stream()
                .anyMatch(s -> s.
                        getName()
                        .equals(cartTitle)), "There is no card with such title " + cartTitle);
    }

    @Test(dataProvider = "checkCorrectCardPricesDataProvider", dataProviderClass = CheckCupPriceCostAndIngredientsDataProvider.class)
    @Description("Check prices correctness for all coffee cards on the menu page")
    @Feature("Coffee cards verification")
    @Issue("5")
    @Owner("Anatolii Orynchak")
    public void checkCorrectCardPrices(String cartTitle, double expectedPrice){
        SoftAssert softAssert = new SoftAssert();

        List<CardComponent> cards = new MenuPage(driver).getCards();

        CardComponent neededCard = getCardByTitle(softAssert, cards, cartTitle);

        softAssert.assertEquals(expectedPrice, neededCard.getPrice(), "Price is not appropriate with title " + cartTitle);

        softAssert.assertAll();
    }

    @Test(dataProvider = "checkCorrectCardIngredientsDataProvider", dataProviderClass = CheckCupPriceCostAndIngredientsDataProvider.class)
    @Description("Check full ingredients compliance for coffee cards on the page")
    @Feature("Coffee cards verification")
    @Issue("5")
    @Owner("Anatolii Orynchak")
    public void checkFullComplianceCardIngredients(String cartTitle, String ... expectedIngredients){
        SoftAssert softAssert = new SoftAssert();

        List<CardComponent> cards = new MenuPage(driver).getCards();

        CardComponent neededCard = getCardByTitle(softAssert, cards, cartTitle);
        CupComponent cupComponent = neededCard.getCupComponent();

        checkAllCupIngredientsFullCompliance(softAssert, cupComponent, true, expectedIngredients);

        softAssert.assertAll();
    }

    @Test(dataProvider = "checkIncorrectFullComplianceCardIngredientsDataProvider", dataProviderClass = CheckCupPriceCostAndIngredientsDataProvider.class)
    @Description("Check incorrect given ingredients handling")
    @Feature("Coffee cards verification")
    @Issue("5")
    @Owner("Anatolii Orynchak")
    public void checkIncorrectFullComplianceCardIngredients(String cartTitle, String ... expectedIngredients){
        SoftAssert softAssert = new SoftAssert();
        List<CardComponent> cards = new MenuPage(driver).getCards();

        CardComponent neededCard = getCardByTitle(softAssert, cards, cartTitle);
        CupComponent cupComponent = neededCard.getCupComponent();

        checkAllCupIngredientsFullCompliance(softAssert, cupComponent, false, expectedIngredients);

        softAssert.assertAll();
    }

    private CardComponent getCardByTitle(SoftAssert softAssert, List<CardComponent> cards, String title){
        List<CardComponent> foundCards = cards.stream().filter(s -> s.getName().equals(title)).toList();
        softAssert.assertEquals(1, foundCards.size(), "There should be only one card with such title " + title);
        return foundCards.getFirst();
    }

    private void checkAllCupIngredientsFullCompliance(SoftAssert softAssert, CupComponent cup, boolean isCorrect, String... expectedIngredients) {
        List<String> actualIngredients = cup.getIngredientNames();

        List<String> expectedList = Arrays.stream(expectedIngredients)
                .filter(s -> !s.startsWith("!"))
                .toList();

        Map<String, Long> actualCountMap = actualIngredients.stream()
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()));

        Map<String, Long> expectedCountMap = expectedList.stream()
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()));

        if(isCorrect)
            softAssert.assertEquals(actualCountMap, expectedCountMap, "Ingredients do not match");
        else
            softAssert.assertNotEquals(actualCountMap, expectedCountMap, "Ingredients match, but mustn't");
    }
}
