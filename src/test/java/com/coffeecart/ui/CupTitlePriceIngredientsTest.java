package com.coffeecart.ui;

import com.coffeecart.dataprovider.CheckCupPriceCostAndIngredientsDataProvider;
import com.coffeecart.ui.component.CardComponent;
import com.coffeecart.ui.component.CupComponent;
import com.coffeecart.ui.page.MenuPage;
import com.coffeecart.ui.testrunners.BaseTestRunner;
import com.coffeecart.dataprovider.CheckCupPriceCostAndIngredientsDataProvider.*;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CupTitlePriceIngredientsTest extends BaseTestRunner {
    private SoftAssert softAssert = new SoftAssert();
    private static final int expectedCount = 9;

    @Test
    public void checkCardsLength(){
        List<CardComponent> cards = new MenuPage(driver).getCards();
        softAssert.assertEquals(expectedCount, cards.size());
        softAssert.assertAll();
    }

    @Test(dataProvider = "checkCorrectCardTitlesDataProvider", dataProviderClass = CheckCupPriceCostAndIngredientsDataProvider.class)
    public void checkCorrectCardTitles(String cartTitle){
        List<CardComponent> cards = new MenuPage(driver).getCards();

        checkCardWithSuchTitleExists(cards, cartTitle, true);

        softAssert.assertAll();
    }

    @Test(dataProvider = "checkIncorrectCardTitlesDataProvider", dataProviderClass = CheckCupPriceCostAndIngredientsDataProvider.class)
    public void checkIncorrectCardTitles(String cartTitle){
        List<CardComponent> cards = new MenuPage(driver).getCards();

        checkCardWithSuchTitleExists(cards, cartTitle, false);

        softAssert.assertAll();
    }

    @Test(dataProvider = "checkCorrectCardPricesDataProvider", dataProviderClass = CheckCupPriceCostAndIngredientsDataProvider.class)
    public void checkCorrectCardPrices(String cartTitle, double expectedPrice){
        List<CardComponent> cards = new MenuPage(driver).getCards();

        CardComponent neededCard = getCardByTitle(cards, cartTitle);
        softAssert.assertEquals(expectedPrice, neededCard.getPrice(), "Price is not appropriate with title " + cartTitle);

        softAssert.assertAll();
    }

    @Test(dataProvider = "checkIncorrectCardPricesDataProvider", dataProviderClass = CheckCupPriceCostAndIngredientsDataProvider.class)
    public void checkIncorrectCardPrices(String cartTitle, double expectedPrice){
        List<CardComponent> cards = new MenuPage(driver).getCards();

        CardComponent neededCard = getCardByTitle(cards, cartTitle);
        softAssert.assertNotEquals(expectedPrice, neededCard.getPrice(), "Price should not be appropriate with title " + cartTitle);

        softAssert.assertAll();
    }

    @Test(dataProvider = "checkCorrectCardIngredientsDataProvider", dataProviderClass = CheckCupPriceCostAndIngredientsDataProvider.class)
    public void checkFullComplianceCardIngredients(String cartTitle, String ... expectedIngredients){
        List<CardComponent> cards = new MenuPage(driver).getCards();

        CardComponent neededCard = getCardByTitle(cards, cartTitle);
        CupComponent cupComponent = neededCard.getCupComponent();

        checkAllCupIngredientsFullCompliance(cupComponent, true, expectedIngredients);

        checkCupIngredientsLength(cupComponent, expectedIngredients.length, true);

        softAssert.assertAll();
    }

    @Test(dataProvider = "checkIncorrectFullComplianceCardIngredientsDataProvider", dataProviderClass = CheckCupPriceCostAndIngredientsDataProvider.class)
    public void checkIncorrectFullComplianceCardIngredients(String cartTitle, String ... expectedIngredients){
        List<CardComponent> cards = new MenuPage(driver).getCards();

        CardComponent neededCard = getCardByTitle(cards, cartTitle);
        CupComponent cupComponent = neededCard.getCupComponent();

        checkAllCupIngredientsFullCompliance(cupComponent, false, expectedIngredients);

        softAssert.assertAll();
    }

    @Test(dataProvider = "checkIncorrectCardIngredientsDataProvider", dataProviderClass = CheckCupPriceCostAndIngredientsDataProvider.class)
    public void checkIncorrectCardIngredients(String cartTitle, String ... expectedIngredients){
        List<CardComponent> cards = new MenuPage(driver).getCards();

        CardComponent neededCard = getCardByTitle(cards, cartTitle);
        CupComponent cupComponent = neededCard.getCupComponent();

        checkAllCupIngredients(cupComponent, expectedIngredients);

        softAssert.assertAll();
    }

    @Test(dataProvider = "checkIncorrectCardIngredientsLengthDataProvider", dataProviderClass = CheckCupPriceCostAndIngredientsDataProvider.class)
    public void checkIncorrectCardIngredientsLength(String cartTitle, String ... expectedIngredients){
        List<CardComponent> cards = new MenuPage(driver).getCards();

        CardComponent neededCard = getCardByTitle(cards, cartTitle);
        CupComponent cupComponent = neededCard.getCupComponent();

        checkAllCupIngredients(cupComponent, expectedIngredients);

        checkCupIngredientsLength(cupComponent, expectedIngredients.length, false);

        softAssert.assertAll();
    }

    private void checkCardWithSuchTitleExists(List<CardComponent> cards, String title, boolean exist){
        if(exist)
            softAssert.assertTrue(cards.stream().anyMatch(s -> s.getName().equals(title)), "There is no card with such title " + title);
        else
            softAssert.assertFalse(cards.stream().anyMatch(s -> s.getName().equals(title)), "There is card with such title " + title);
    }

    private CardComponent getCardByTitle(List<CardComponent> cards, String title){
        List<CardComponent> foundCards = cards.stream().filter(s -> s.getName().equals(title)).toList();
        softAssert.assertEquals(1, foundCards.size(), "There should be only one card with such title " + title);
        return foundCards.getFirst();
    }

    private void checkAllCupIngredients(CupComponent cup, String ... ingredients){
        String indicator = "!";
        for (String ingredient : ingredients) {
            if(!ingredient.contains(indicator))
                softAssert.assertTrue(cup.containsIngredient(ingredient));
            else
                softAssert.assertFalse(cup.containsIngredient(ingredient.split(indicator)[1]));
        }
    }

    private void checkCupIngredientsLength(CupComponent cup, int expectedCount, boolean isCorrect){
        if(isCorrect)
            softAssert.assertEquals(expectedCount, cup.getIngredientLayersCount());
        else
            softAssert.assertNotEquals(expectedCount, cup.getIngredientLayersCount());
    }

    private void checkAllCupIngredientsFullCompliance(CupComponent cup, boolean isCorrect, String... expectedIngredients) {
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
