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
import io.qameta.allure.Step;

public class TotalButtonElement extends BaseElement {

    @Getter
    WebElement totalButton;

    @Getter
    @FindBy(xpath = "//ul[contains(@class, 'cart-preview')]")
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

    public PaymentDetailModal clickTotalButton() {
        waitUntilElementClickable(totalButton);
        totalButton.click();
        return new PaymentDetailModal(driver, modalElement);
    }

    @Step("Count cart")
    public double getMoneyCounter() {
        return Double.parseDouble(totalButton.getText().replaceAll("[^\\d.]", ""));
    }

    public CartComponent hoverTotalButton() {
        actions.moveToElement(totalButton).perform();
        waitUntilElementVisible(cartComponentRoot);
        return new CartComponent(driver, cartComponentRoot);
    }

    public boolean isDisplayed() {
        return totalButton.isDisplayed();
    }
    public void hover() {
        actions.moveToElement(totalButton).perform();
    }

    public void unhover() {
        Actions actions = new Actions(driver);
        WebElement body = driver.findElement(By.tagName("body"));
        actions.moveToElement(body, 0, 0).perform();    }
}
