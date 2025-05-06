package com.coffeecart.ui;

import com.coffeecart.ui.page.MenuPage;
import com.coffeecart.ui.testrunners.BaseTestRunner;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class TestSynchronizationBetweenCarts extends BaseTestRunner {

    @Test
    public void testSynchronizationBetweenCarts() {
        SoftAssert softAssert = new SoftAssert();
        MenuPage menuPage = new MenuPage(driver);

        softAssert.assertAll();
    }
}
