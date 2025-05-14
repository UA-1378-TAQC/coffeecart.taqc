package com.coffeecart.ui;

import com.coffeecart.ui.page.MenuPage;
import com.coffeecart.ui.testrunners.BaseTestRunner;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestTotalButtonRestriction extends BaseTestRunner {

    @Test
    @Description("Verify order button restriction with empty cart")
    @Feature("Total Button Validation")
    @Issue("25")
    @Owner("Nazar Tarasiuk")
    public void TestTotalButtonRestrictionWithEmptyCart(){
        var hoverMenu = new MenuPage(driver).getButtonElement().tryHover();
        Assert.assertNull(hoverMenu, "Total button is hover able when cart is empty");

        var clickMenu = new MenuPage(driver).goToCartPage().getTotalButton().tryClick();
        Assert.assertNull(clickMenu, "Total button is visible when cart is empty");

        var emptyText = new MenuPage(driver).goToCartPage().getEmptyCartMessage().getText();
        Assert.assertEquals(emptyText,"No coffee, go add some.", "Empty cart text don't appears");
    }
}
