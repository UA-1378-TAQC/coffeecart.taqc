package com.coffeecart.ui.component;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import io.qameta.allure.Step;

public class ShortItemComponent extends BaseComponent{
    @Getter
    @FindBy(xpath = "./div[1]/span[1]")
    WebElement spanName;
    @Getter
    @FindBy(xpath = "./div[1]/span[2]")
    WebElement spanCount;

    @Getter
    @FindBy(xpath = "./div[2]/button[2]")
    WebElement buttonMinus;
    @Getter
    @FindBy(xpath = "./div[2]/button[1]")
    WebElement buttonPlus;

    public ShortItemComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
        PageFactory.initElements(new DefaultElementLocatorFactory(rootElement), this);
    }

    public String getName(){
        return spanName.getText();
    }

    @Step("Count")
    public int getCount(){
        return Integer.parseInt(spanCount.getText().replaceAll("\\D",""));
    }

    public ShortItemComponent clickPlus(){
        waitUntilElementClickable(buttonPlus);
        buttonPlus.click();
        return this;
    }

    public ShortItemComponent clickMinus(){
        waitUntilElementClickable(buttonMinus);
        buttonMinus.click();
        return this;
    }

    @Step("'+' button is enabled")
    public boolean plusButtonIsEnabled() {
        return buttonPlus.isEnabled();
    }

    @Step("Click on '+' button")
    public ShortItemComponent clickOnAddButton() {
        if (plusButtonIsEnabled()) {
            buttonPlus.click();
        }
        return this;
    }
}
