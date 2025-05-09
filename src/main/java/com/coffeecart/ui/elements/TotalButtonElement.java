package com.coffeecart.ui.elements;

import com.coffeecart.ui.component.CartComponent;
import com.coffeecart.ui.modal.PaymentDetailModal;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class TotalButtonElement extends BaseElement {

    @Getter
    WebElement totalButton;

    @Getter
    @FindBy(xpath = "//ul[@class='cart-preview show']")
    WebElement cartComponentRoot;

    @Getter
    @FindBy(xpath = "//div[@class='modal']")
    WebElement modalElement;

    CartComponent cartComponent;

    Actions actions;

    public TotalButtonElement(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
        totalButton = rootElement;
        actions = new Actions(driver);
    }

    public PaymentDetailModal clickTotalButton(){
        try {
            totalButton.click();
            return new PaymentDetailModal(driver,modalElement);
        } catch (NoSuchElementException exception){
            return null;
        }
    }

    public double getMoneyCounter() {
        return Double.parseDouble(totalButton.getText().replaceAll("[^\\d.]",""));
    }

    public CartComponent hoverTotalButton(){
        try {
            actions.moveToElement(totalButton).perform();
            cartComponentRoot.isDisplayed(); // trick to throw NoSuchElement
            return new CartComponent(driver, cartComponentRoot);
        } catch (NoSuchElementException exception){
            return null;
        }
    }
}
