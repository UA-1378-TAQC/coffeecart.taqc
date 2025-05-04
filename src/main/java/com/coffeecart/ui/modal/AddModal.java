package com.coffeecart.ui.modal;

import com.coffeecart.ui.page.MenuPage;
import com.coffeecart.ui.utils.Waiter;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;
import java.time.Duration;

public class AddModal extends BaseModal {

    @Getter
    @FindBy(xpath = ".//p[contains(text(), 'Add')]")
    private WebElement modalTitle;
    @Getter
    @FindBy(xpath = ".//button[contains(text(), 'Yes')]")
    private WebElement yesButton;
    @Getter
    @FindBy(xpath = ".//button[contains(text(), 'No')]")
    private WebElement noButton;

    public AddModal(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    public String getFullModalText() {
        waitUntilElementVisible(modalTitle);
        return modalTitle.getText().trim();
    }

    public String getDrinkName() {
        String fullText = getFullModalText();
        if (fullText.startsWith("Add ") && fullText.endsWith(" to the cart?")) {
            return fullText
                    .replaceFirst("Add ", "")
                    .replaceFirst(" to the cart\\?", "")
                    .trim();
        }
        return "";
    }

    public MenuPage clickYes() {
        clickDynamicElement(yesButton);
        return new MenuPage(driver);
    }

    public MenuPage clickNo() {
        clickDynamicElement(noButton);
        Waiter.waitInvisibility(driver, rootElement, Duration.ofSeconds(2));
        return new MenuPage(driver);
    }

    public boolean isModalDisplayed() {
        return modalTitle.isDisplayed();
    }

    public AddModal waitNoButtonBorderChanged(String beforeHex, Duration timeout) {
        Waiter.waitCssValueChanged(driver,
                this::getNoButtonBorderColor,
                beforeHex,
                timeout);
        return this;
    }

    public String getNoButtonTextColor() {
        return Color.fromString(noButton.getCssValue("color")).asHex();
    }

    public String getNoButtonBorderColor() {
        return Color.fromString(noButton.getCssValue("border-top-color")).asHex();
    }

    @Step("Hover over the 'No' button")
    public AddModal hoverNoButton(){
        waitUntilElementVisible(noButton);
        actions.moveToElement(noButton).perform();
        return this;
    }
}

