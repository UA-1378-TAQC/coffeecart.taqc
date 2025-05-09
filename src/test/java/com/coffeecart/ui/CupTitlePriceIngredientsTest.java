package com.coffeecart.ui;

import com.coffeecart.dataprovider.CheckCupPriceCostAndIngredientsDataProvider;
import com.coffeecart.ui.component.CardComponent;
import com.coffeecart.ui.component.CupComponent;
import com.coffeecart.ui.page.MenuPage;
import com.coffeecart.ui.testrunners.BaseTestRunner;
import com.coffeecart.dataprovider.CheckCupPriceCostAndIngredientsDataProvider.*;
import io.qameta.allure.*;
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
    @Issue("131")
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
    @Issue("131")
    @Owner("Anatolii Orynchak")
    public void checkCorrectCardTitles(String cartTitle){
        SoftAssert softAssert = new SoftAssert();

        List<CardComponent> cards = new MenuPage(driver).getCards();

        checkCardWithSuchTitleExists(softAssert, cards, cartTitle, true);

        softAssert.assertAll();
    }

    @Test(dataProvider = "checkIncorrectCardTitlesDataProvider", dataProviderClass = CheckCupPriceCostAndIngredientsDataProvider.class)
    @Description("Check coffee with some other titles don`t exist on the page")
    @Feature("Coffee cards verification")
    @Issue("131")
    @Owner("Anatolii Orynchak")
    public void checkIncorrectCardTitles(String cartTitle){
        SoftAssert softAssert = new SoftAssert();

        List<CardComponent> cards = new MenuPage(driver).getCards();

        checkCardWithSuchTitleExists(softAssert, cards, cartTitle, false);

        softAssert.assertAll();
    }

    @Test(dataProvider = "checkCorrectCardPricesDataProvider", dataProviderClass = CheckCupPriceCostAndIngredientsDataProvider.class)
    @Description("Check prices correctness for all coffee cards on the menu page")
    @Feature("Coffee cards verification")
    @Issue("131")
    @Owner("Anatolii Orynchak")
    public void checkCorrectCardPrices(String cartTitle, double expectedPrice){
        SoftAssert softAssert = new SoftAssert();

        List<CardComponent> cards = new MenuPage(driver).getCards();

        CardComponent neededCard = getCardByTitle(softAssert, cards, cartTitle);
        softAssert.assertEquals(expectedPrice, neededCard.getPrice(), "Price is not appropriate with title " + cartTitle);

        softAssert.assertAll();
    }

    @Test(dataProvider = "checkIncorrectCardPricesDataProvider", dataProviderClass = CheckCupPriceCostAndIngredientsDataProvider.class)
    @Description("Check negative variants for coffee prices are caught")
    @Feature("Coffee cards verification")
    @Issue("131")
    @Owner("Anatolii Orynchak")
    public void checkIncorrectCardPrices(String cartTitle, double expectedPrice){
        SoftAssert softAssert = new SoftAssert();

        List<CardComponent> cards = new MenuPage(driver).getCards();

        CardComponent neededCard = getCardByTitle(softAssert, cards, cartTitle);
        softAssert.assertNotEquals(expectedPrice, neededCard.getPrice(), "Price should not be appropriate with title " + cartTitle);

        softAssert.assertAll();
    }

    @Test(dataProvider = "checkCorrectCardIngredientsDataProvider", dataProviderClass = CheckCupPriceCostAndIngredientsDataProvider.class)
    @Description("Check full ingredients compliance for coffee cards on the page")
    @Feature("Coffee cards verification")
    @Issue("131")
    @Owner("Anatolii Orynchak")
    public void checkFullComplianceCardIngredients(String cartTitle, String ... expectedIngredients){
        SoftAssert softAssert = new SoftAssert();

        List<CardComponent> cards = new MenuPage(driver).getCards();

        CardComponent neededCard = getCardByTitle(softAssert, cards, cartTitle);
        CupComponent cupComponent = neededCard.getCupComponent();

        checkAllCupIngredientsFullCompliance(softAssert, cupComponent, true, expectedIngredients);

        checkCupIngredientsLength(softAssert, cupComponent, expectedIngredients.length, true);

        softAssert.assertAll();
    }


    @Test(dataProvider = "checkIncorrectCardIngredientsDataProvider", dataProviderClass = CheckCupPriceCostAndIngredientsDataProvider.class)
    @Description("Check coffee cards don`t contain incorrect ingredients")
    @Feature("Coffee cards verification")
    @Issue("131")
    @Owner("Anatolii Orynchak")
    public void checkIncorrectCardIngredients(String cartTitle, String ... expectedIngredients){
        SoftAssert softAssert = new SoftAssert();

        List<CardComponent> cards = new MenuPage(driver).getCards();

        CardComponent neededCard = getCardByTitle(softAssert, cards, cartTitle);
        CupComponent cupComponent = neededCard.getCupComponent();

        checkAllCupIngredients(softAssert, cupComponent, expectedIngredients);

        softAssert.assertAll();
    }

    @Test(dataProvider = "checkIncorrectCardIngredientsLengthDataProvider", dataProviderClass = CheckCupPriceCostAndIngredientsDataProvider.class)
    @Description("Check incorrect given ingredients length handling")
    @Feature("Coffee cards verification")
    @Issue("131")
    @Owner("Anatolii Orynchak")
    public void checkIncorrectCardIngredientsLength(String cartTitle, String ... expectedIngredients){
        SoftAssert softAssert = new SoftAssert();

        List<CardComponent> cards = new MenuPage(driver).getCards();

        CardComponent neededCard = getCardByTitle(softAssert, cards, cartTitle);
        CupComponent cupComponent = neededCard.getCupComponent();

        checkCupIngredientsLength(softAssert, cupComponent, expectedIngredients.length, false);

        softAssert.assertAll();
    }

    @Test(dataProvider = "checkIncorrectCardIngredientsLengthDataProvider", dataProviderClass = CheckCupPriceCostAndIngredientsDataProvider.class)
    @Description("Check incorrect given ingredients with correct length handling")
    @Feature("Coffee cards verification")
    @Issue("131")
    @Owner("Anatolii Orynchak")
    public void checkIncorrectFullComplianceCardIngredients(String cartTitle, String ... expectedIngredients){
        SoftAssert softAssert = new SoftAssert();
        List<CardComponent> cards = new MenuPage(driver).getCards();

        CardComponent neededCard = getCardByTitle(softAssert, cards, cartTitle);
        CupComponent cupComponent = neededCard.getCupComponent();

        checkAllCupIngredientsFullCompliance(softAssert, cupComponent, false, expectedIngredients);

        softAssert.assertAll();
    }



    private void checkCardWithSuchTitleExists(SoftAssert softAssert, List<CardComponent> cards, String title, boolean exist){
        if(exist)
            softAssert.assertTrue(cards.stream().anyMatch(s -> s.getName().equals(title)), "There is no card with such title " + title);
        else
            softAssert.assertFalse(cards.stream().anyMatch(s -> s.getName().equals(title)), "There is card with such title " + title);
    }

    private CardComponent getCardByTitle(SoftAssert softAssert, List<CardComponent> cards, String title){
        List<CardComponent> foundCards = cards.stream().filter(s -> s.getName().equals(title)).toList();
        softAssert.assertEquals(1, foundCards.size(), "There should be only one card with such title " + title);
        return foundCards.getFirst();
    }

    private void checkAllCupIngredients(SoftAssert softAssert, CupComponent cup, String ... ingredients){
        String indicator = "!";
        for (String ingredient : ingredients) {
            if(!ingredient.contains(indicator))
                softAssert.assertTrue(cup.containsIngredient(ingredient), "This cup component should contain " + ingredient);
            else
                softAssert.assertFalse(cup.containsIngredient(ingredient.split(indicator)[1]), "This cup component shouldn`t contain " + ingredient);
        }
    }

    private void checkCupIngredientsLength(SoftAssert softAssert, CupComponent cup, int expectedCount, boolean isCorrect){
        if(isCorrect)
            softAssert.assertEquals(expectedCount, cup.getIngredientLayersCount(), "Expected " + expectedCount + " but get " + cup.getIngredientLayersCount());
        else
            softAssert.assertNotEquals(expectedCount, cup.getIngredientLayersCount(), "Expected equals actual but mustn`t");
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
