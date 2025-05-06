package com.coffeecart.ui.modal;

import com.coffeecart.ui.Base;
import com.coffeecart.ui.elements.BaseElement;
import com.coffeecart.ui.page.MenuPage;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SuccessfulPopUp extends Base {
    @Getter
    @FindBy(xpath = "//div[contains(@class,'snackbar success')]")
    private WebElement successTitle;

    public SuccessfulPopUp(WebDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {
        waitUntilElementVisible(successTitle);
        return successTitle.isDisplayed();
    }
    public String getSuccessTitleText() {
        waitUntilElementVisible(successTitle);
        return successTitle.getText();

    }


    public void waitUntilOpened() {
        waitUntilElementVisible(successTitle);
    }

    public MenuPage waitUntilClosed() {
        waitUntilElementInvisible(successTitle);
        return new MenuPage(driver);
    }
}
