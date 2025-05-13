package com.coffeecart.ui;

import com.coffeecart.data.DrinkEnum;
import com.coffeecart.ui.page.MenuPage;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class TestEmail extends BaseTest {
    @DataProvider(name = "invalidEmailData")
    public Object[][] invalidEmailData() {
        return new Object[][]{
                {"", "Empty email"},
                {"viktoriia11gmail.com", "Missing @ symbol"},
                {"viktoriia11@", "Missing domain"},
                {"viktoriia11 @gmail.com", "Contains space"},
                {"viktoriia11@gmail.c", "With short TLD"}
        };
    }

    @DataProvider(name = "validEmailData")
    public Object[][] validEmailData() {
        return new Object[][]{
                {"v!k#toriia11@gmail.com", "With special characters"},
        };
    }
    @Test(dataProvider = "invalidEmailData")
    public void verifyInvalidEmailValidation(String email, String description) {
        new MenuPage(driver)
                .clickDrink(DrinkEnum.getName(DrinkEnum.CAPPUCCINO))
                .clickTotalButton()
                .enterName("Viktoriia")
                .enterEmail(email)
                .clickSubmitButtonWithInvalidInput()
                .closeModalWindowOnCartPage();
    }
    @Test(dataProvider = "validEmailData")
    public void verifyValidEmailSubmission(String email, String description) {
        SoftAssert softAssert = new SoftAssert();

        MenuPage menuPage = new MenuPage(driver)
                .clickDrink(DrinkEnum.getName(DrinkEnum.CAPPUCCINO))
                .clickTotalButton()
                .enterName("Viktoriia")
                .enterEmail(email)
                .clickSubmitButtonWithValidInput()
                .waitUntilClosed();

        softAssert.assertTrue(menuPage.isSubmitButtonEnabled(), "Submit button should be enabled for valid email");

        softAssert.assertAll();
    }

}

