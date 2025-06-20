package com.coffeecart.ui;

import com.coffeecart.data.DrinkEnum;
import com.coffeecart.ui.page.MenuPage;
import com.coffeecart.ui.testrunners.BaseTestRunner;
import io.qameta.allure.Step;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class LuckyDayDiscountTest extends BaseTestRunner {

    private static final int ITEMS_PER_MODAL = 3;
    @DataProvider(name = "drinkData")
    public Object[][] drinkData() {
        return new Object[][]{
                {DrinkEnum.getName(DrinkEnum.ESPRESSO), 1},
                {DrinkEnum.getName(DrinkEnum.ESPRESSO_MACCHIATO), 2},
                {DrinkEnum.getName(DrinkEnum.CAPPUCCINO), 3},
                {DrinkEnum.getName(DrinkEnum.MOCHA), 4},
                {DrinkEnum.getName(DrinkEnum.FLAT_WHITE), 5},
                {DrinkEnum.getName(DrinkEnum.AMERICANO), 6},
                {DrinkEnum.getName(DrinkEnum.CAFE_LATTE), 7},
                {DrinkEnum.getName(DrinkEnum.ESPRESSO_CON_PANNA), 8},
                {DrinkEnum.getName(DrinkEnum.CAFE_BREVE), 9},
        };
    }
    @Test(dataProvider = "drinkData")
    @Step("Verify LuckyDay modal appears after every 3rd item added")
    public void verifyLuckyDayModalAfterEvery3rdItem(String drinkName, int step) {
        SoftAssert softAssert = new SoftAssert();
        MenuPage menuPage = new MenuPage(driver);

        menuPage.clickDrink(drinkName);

        if (step % ITEMS_PER_MODAL == 0) {
            softAssert.assertTrue(menuPage.getLuckyDayComponent().isDisplayed(),
                    "LuckyDay Modal should appear after adding " + step + " items.");

            if (step / ITEMS_PER_MODAL % 2 == 0) {
                menuPage.getLuckyDayComponent().clickSkip();
            } else {
                menuPage.getLuckyDayComponent().clickYes();
            }
        }

        softAssert.assertTrue(menuPage.getCardByName(drinkName) != null,
                "The " + drinkName + " should be added to the cart.");
        softAssert.assertAll();
    }
}
