package com.coffeecart.ui;

import com.coffeecart.ui.component.HeaderComponent;
import com.coffeecart.ui.page.MenuPage;
import io.qameta.allure.*;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class TestAddCupsToCart extends BaseTest {
    @Test
    @Description("Verify adding multiple drinks updates cart counter.")
    @Feature("Cart Counter Update")
    @Issue("2")
    @Owner("Fylyk Viktoriia")
    public void testAddMultipleCupsToCart() {
        SoftAssert softAssert = new SoftAssert();

        MenuPage menuPage = new MenuPage(driver);
        HeaderComponent header = menuPage.getHeader();

        int initialCount = header.getTotalNumberItemsFromCartLink();
        softAssert.assertEquals(initialCount, 0, "Initial counter should be 0");

        for (int i = 0; i < 5; i++) {
            menuPage.getCards()
                    .get(i)
                    .clickCup();
            int expected = initialCount + i + 1;
            int actual = header.getTotalNumberItemsFromCartLink();
            softAssert.assertEquals(actual, expected,
                    "Counter after pressing the cup #" + (i + 1));
        }

        softAssert.assertAll();
    }
}
