package com.coffeecart.ui;

import com.coffeecart.ui.component.HeaderComponent;
import com.coffeecart.ui.page.MenuPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class TestAddCupsToCart extends BaseTest {
    @Test
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
