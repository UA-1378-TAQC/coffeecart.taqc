package com.coffeecart.ui;

import com.coffeecart.data.DrinkEnum;
import com.coffeecart.ui.modal.SuccessfulPopUp;
import com.coffeecart.ui.page.MenuPage;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class TestName extends BaseTest {
    @DataProvider(name = "invalidNameData")
    public Object[][] invalidNameData() {
        return new Object[][]{
                {"","Empty name"}
        };
    }

    @DataProvider(name = "validNameData")
    public Object[][] validNameData() {
        return new Object[][]{
                {"123Viktoriia", "Name with numbers"},
                {"@#viktoriia!","Name with special characters"}
        };
    }
    @Test(dataProvider = "invalidNameData")
    public void verifyInvalidNameValidation(String name, String description) {
        new MenuPage(driver)
                .clickDrink(DrinkEnum.getName(DrinkEnum.CAPPUCCINO))
                .clickTotalButton()
                .enterName(name)
                .enterEmail(testValueProvider.getUserEmail())
                .clickSubmitButtonWithInvalidInput()
                .closeModalWindowOnCartPage();

    }
    @Test(dataProvider = "validNameData")
    public void verifyValidNameSubmission(String name, String description) {
        SoftAssert softAssert = new SoftAssert();

        new MenuPage(driver)
                .clickDrink(DrinkEnum.getName(DrinkEnum.CAPPUCCINO))
                .clickTotalButton()
                .enterName(name)
                .enterEmail(testValueProvider.getUserEmail())
                .clickSubmitButtonWithValidInput();

        SuccessfulPopUp popUp = new SuccessfulPopUp(driver);
        popUp.waitUntilOpened();

        softAssert.assertTrue(popUp.isDisplayed(), "Success popup should be displayed");
        softAssert.assertTrue(popUp.getSuccessTitleText().contains("Thanks for your purchase"),
                "Popup text should confirm the purchase");

        MenuPage menuPage = popUp.waitUntilClosed();

        softAssert.assertTrue(menuPage.isSubmitButtonEnabled(), "Submit button should be enabled after valid submission");
        softAssert.assertAll();
    }
}
