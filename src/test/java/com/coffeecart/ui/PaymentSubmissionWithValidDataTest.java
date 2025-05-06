package com.coffeecart.ui;

import com.coffeecart.data.DrinkEnum;
import com.coffeecart.ui.data.PaymentFormTexts;
import com.coffeecart.ui.modal.PaymentDetailModal;
import com.coffeecart.ui.modal.SuccessfulPopUp;
import com.coffeecart.ui.page.MenuPage;
import com.coffeecart.ui.testrunners.BaseTestRunner;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Owner;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


public class PaymentSubmissionWithValidDataTest extends BaseTestRunner {
    private static final String SUCCESS_MESSAGE = "Thanks for your purchase. Please check your email for payment.";
    private static final String DRINK = DrinkEnum.ESPRESSO.name();

    @Test
    @Description("Verify valid name/email submission shows confirmation message.")
    @Feature("Payment Form Validation")
    @Issue("3")
    @Owner("Nataliia Hrusha")
    public void verifyPaymentSubmissionWithValidData() {
        SoftAssert softAssert = new SoftAssert();

        PaymentDetailModal paymentModal = new MenuPage(driver)
                .clickDrink(DRINK)
                .clickTotalButton();

        softAssert.assertEquals(paymentModal.getHeaderText(), PaymentFormTexts.HEADER.getText(), "Header text mismatch");
        softAssert.assertEquals(paymentModal.getParagraphText(), PaymentFormTexts.PARAGRAPH.getText(), "Paragraph text mismatch");
        softAssert.assertEquals(paymentModal.getLabelNameText(), PaymentFormTexts.NAME_LABEL.getText(), "Name label text mismatch");
        softAssert.assertEquals(paymentModal.getLabelEmailText(), PaymentFormTexts.EMAIL_LABEL.getText(), "Email label text mismatch");
        softAssert.assertEquals(paymentModal.getLabelCheckboxText(),PaymentFormTexts.CHECKBOX_LABEL.getText(), "Checkbox label text mismatch");
        softAssert.assertEquals(paymentModal.getSubmitButtonText(), PaymentFormTexts.SUBMIT_BUTTON.getText(), "Submit button text mismatch");
        softAssert.assertFalse(paymentModal.isCheckboxMarked(), "Checkbox should be unchecked by default");

        boolean isMatch = new MenuPage(driver).isBackgroundColorOfPaymentDetailModalMatch();

        softAssert.assertTrue(isMatch ,
                "Payment modal should have correct background color"
        );

        SuccessfulPopUp successPopup = paymentModal
                .enterName(testValueProvider.getUserName())
                .enterEmail(testValueProvider.getUserEmail())
                .clickSubmitButtonWithValidInput();

        softAssert.assertTrue(successPopup.isDisplayed(), "Successful popup should be visible");
        softAssert.assertEquals(successPopup.getSuccessTitleText(), SUCCESS_MESSAGE, "Success message text mismatch");

        softAssert.assertAll();
    }
}

