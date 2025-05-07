package com.coffeecart.ui;

import com.coffeecart.data.DrinkEnum;
import com.coffeecart.ui.page.MenuPage;
import com.coffeecart.ui.testrunners.BaseTestRunner;
import io.qameta.allure.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Arrays;

public class LuckyDayPopUpAppears  extends BaseTestRunner {
    SoftAssert softAssert = new SoftAssert();

    @DataProvider(name = "threeDrinksProvider")
    public Object[][] threeDrinksProvider() {
        return new Object[][] {
                { new DrinkEnum[] { DrinkEnum.ESPRESSO, DrinkEnum.MOCHA, DrinkEnum.AMERICANO } },
                { new DrinkEnum[] { DrinkEnum.CAPPUCCINO, DrinkEnum.FLAT_WHITE, DrinkEnum.CAFE_BREVE } },
                { new DrinkEnum[] { DrinkEnum.CAPPUCCINO, DrinkEnum.CAPPUCCINO, DrinkEnum.CAPPUCCINO } },
                { new DrinkEnum[] { DrinkEnum.FLAT_WHITE, DrinkEnum.FLAT_WHITE, DrinkEnum.CAFE_BREVE } },
                { new DrinkEnum[] { DrinkEnum.ESPRESSO_MACCHIATO, DrinkEnum.ESPRESSO_CON_PANNA, DrinkEnum.CAFE_BREVE } }
        };
    }
    @Link(name = "Coffee Cart", url = "https://coffee-cart.app/")
    @Owner("Khrystyna Martynova")
    @Issue("2")
    @Epic("Promotions after choosing 3 drinks")
    @Feature("Lucky Day pop-up")
    @Story("User can sees Lucky Day popup after choosing 3 drinks")
    @Severity(SeverityLevel.NORMAL)
    @Description("This test verifies that after selecting three drinks, the Lucky Day  pop-up is displayed.")
    @Test(dataProvider = "threeDrinksProvider")
    public void  luckyDayPopUpAppearsTest(DrinkEnum[] drinks) {
        MenuPage menuPage = new MenuPage(driver);

        Arrays.stream(drinks)
                .map(drink -> drink.getRecipe().getName())
                .forEach(menuPage::clickDrink);

        boolean isPopupVisible = menuPage.isLuckyModalDisplayed();
        softAssert.assertTrue(isPopupVisible, "Lucky Day pop-up should appear after 3 clicks");

        if (isPopupVisible) {
            menuPage.getLuckyDayComponent().clickSkip();
        }
        softAssert.assertAll();
    }
}