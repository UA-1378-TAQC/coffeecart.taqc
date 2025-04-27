package com.coffeecart.ui.elements;

import com.coffeecart.ui.component.CartComponent;
import com.coffeecart.ui.modal.PaymentDetail;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class TotalButtonElement extends BaseElement {

    @Getter
    @FindBy(xpath = ".//button[@class='pay']")
    WebElement totalButton;

    @Getter
    @FindBy(xpath = ".//ul[@class='cart-preview']")
    WebElement cardComponentElement;

    @Getter
    @FindBy(xpath = "//div[@class='modal']")
    WebElement modalElement;

    CartComponent cartComponent;

    Actions actions;

    /**
     * Initializes a TotalButtonElement with the specified WebDriver and root WebElement.
     *
     * Sets up the cart component and actions for interacting with the total payment button UI.
     *
     * @param driver the WebDriver instance controlling the browser
     * @param rootElement the root WebElement representing the total button section
     */
    public TotalButtonElement(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
        cartComponent = new CartComponent(driver, cardComponentElement);
        actions = new Actions(driver);
    }

    /**
     * Clicks the total payment button after ensuring it is clickable, and returns a PaymentDetail for the resulting modal.
     *
     * @return a PaymentDetail object representing the payment modal dialog
     */
    public PaymentDetail clickTotalButton(){
        waitUntilElementClickable(totalButton);
        totalButton.click();
        return new PaymentDetail(driver,modalElement);
    }

    /**
     * Retrieves the total payment amount displayed on the button.
     *
     * @return the numeric value of the total payment shown on the button
     */
    public double getMoneyCounter() {
        return Double.parseDouble(totalButton.getText().replace("Total: $",""));
    }

    /**
     * Hovers the mouse over the total payment button and waits for the cart preview to become visible.
     *
     * @return the cart component displayed after hovering
     */
    public CartComponent hoverTotalButton(){
        actions.moveToElement(totalButton).perform();
        waitUntilElementVisible(cardComponentElement);
        return cartComponent;
    }
}
