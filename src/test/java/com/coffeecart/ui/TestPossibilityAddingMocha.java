package com.coffeecart.ui;

import com.coffeecart.data.DrinkEnum;
import com.coffeecart.data.MenuPageDataProviders;
import com.coffeecart.ui.component.LuckyDayComponent;
import com.coffeecart.ui.page.MenuPage;
import io.qameta.allure.*;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


public class TestPossibilityAddingMocha extends BaseTest {
    private final String expectedTitleLuckyDayComponent = "It's your lucky day! Get an extra cup of Mocha for $4.";

    @Test(dataProvider = "drinkNames", dataProviderClass = MenuPageDataProviders.class)
    @Description("Verification of the possibility of adding Mocha to the cart in the pop-up 'Lucky Day'")
    @Feature("Pop-up 'Lucky Day'")
    @Owner("Mariia Mykhailenko")
    public void possibilityAddingMocha(String drinkName){
        SoftAssert softAssert = new SoftAssert();

        MenuPage menuPage = new MenuPage(driver);
        for (WebElement card: menuPage.getRootCards()){
            softAssert.assertTrue(card.isDisplayed(), String.format("The Card '%s' should be displayed",
                    card.getText()));
        }

        if(drinkName.equals(DrinkEnum.getName(DrinkEnum.ESPRESSO))){
            menuPage = menuPage.clickDrink(drinkName).clickDrink(drinkName).clickDrink(drinkName);
        }else{
            menuPage = menuPage.clickDrink(drinkName).clickDrink(drinkName);
        }

        int numberItemsInCart = menuPage.getHeader().getTotalNumberItemsFromCartLink();

        LuckyDayComponent luckyDayComponent = menuPage.getLuckyDayComponent();
        softAssert.assertTrue(menuPage.getLuckyDayModalRoot().isDisplayed(),
                "The LuckyDayModal should be displayed.");

        softAssert.assertEquals(luckyDayComponent.getTitle().getText(), expectedTitleLuckyDayComponent,
                String.format("The title of LuckyDayComponent should be '%s' but was '%s'",
                        expectedTitleLuckyDayComponent,
                        luckyDayComponent.getTitle().getText()));


        menuPage = luckyDayComponent.clickYes();
        softAssert.assertTrue(menuPage.isLuckyModalNotDisplayed(), "The LuckyDayModal shouldn't be displayed.");

        int expectedNumberItemsInCart = numberItemsInCart+1;
        int actualNumberItemsInCart = menuPage.getHeader().getTotalNumberItemsFromCartLink();
        softAssert.assertEquals(actualNumberItemsInCart, expectedNumberItemsInCart,
                String.format("The number of elements in Cart should be '%s' but was '%s'",
                        expectedNumberItemsInCart,
                        actualNumberItemsInCart));

        softAssert.assertAll();
    }
}
