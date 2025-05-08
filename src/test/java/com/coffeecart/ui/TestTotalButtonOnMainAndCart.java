package com.coffeecart.ui;

import com.coffeecart.data.DrinkEnum;
import com.coffeecart.ui.data.Colors;
import com.coffeecart.ui.elements.TotalButtonElement;
import com.coffeecart.ui.modal.PaymentDetailModal;
import com.coffeecart.ui.testrunners.BaseTestRunner;
import io.qameta.allure.*;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;
import com.coffeecart.ui.page.MenuPage;
import com.coffeecart.ui.page.CartPage;
import org.testng.asserts.SoftAssert;


public class TestTotalButtonOnMainAndCart extends BaseTestRunner {
    @Test
    @Description("Verify the functioning of the \"Total\" button on \"Menu\" and \"Cart\" pages.")
    @Feature("Total Button Functionality on Menu and Cart Pages")
    @Issue("15")
    @Owner("Fylyk Viktoriia")
    public void testTotalButton() {
        SoftAssert softAssert = new SoftAssert();

        MenuPage menuPage = new MenuPage(driver);
        menuPage = menuPage.clickDrink(DrinkEnum.getName(DrinkEnum.ESPRESSO));

        TotalButtonElement totalButton = menuPage.getButtonElement();
        totalButton.hoverTotalButton();

        String hoverColor = totalButton.getTotalButton().getCssValue("color");
        softAssert.assertEquals(hoverColor, Colors.GOLDEN.getColor(),
                "Text color should change to RGB(218, 165, 32) on hover");

        softAssert.assertTrue(totalButton.getCartComponentRoot().isDisplayed(),
                "Pop-up cart should appear on hover");


        PaymentDetailModal paymentModal = totalButton.clickTotalButton();
        softAssert.assertTrue(paymentModal.getPaymentModal().isDisplayed(), "Payment modal should appear on click");

        paymentModal.closeModalWindowOnMenuPage();

        CartPage cartPage = menuPage.goToCartPage();
        TotalButtonElement cartTotalButton = cartPage.getTotalButton();

        cartTotalButton.hoverTotalButton();

        String cartHoverColor = cartTotalButton.getTotalButton().getCssValue("color");

        softAssert.assertEquals(cartHoverColor, Colors.GOLDEN.getColor(),
                "Text color should change to RGB(218, 165, 32) on hover in cart page");


        PaymentDetailModal cartPaymentModal = cartTotalButton.clickTotalButton();
        softAssert.assertTrue(cartPaymentModal.getPaymentModal().isDisplayed(),
                "Payment modal should appear on click in cart page");

        cartPaymentModal.closeModalWindowOnCartPage();
        softAssert.assertAll();
    }
}

