package com.coffeecart.ui.component;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ShortItemComponent extends BaseComponent{

    @Getter
    @FindBy(xpath = "./div[1]/span[1]")
    WebElement spanName;
    @Getter
    @FindBy(xpath = "./div[1]/span[2]")
    WebElement spanCount;

    @Getter
    @FindBy(xpath = "./div[2]/button[1]")
    WebElement buttonMinus;
    @Getter
    @FindBy(xpath = "./div[2]/button[2]")
    WebElement buttonPlus;

    public ShortItemComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    public String getName(){
        return spanName.getText();
    }

    /**
     * Returns the item's count as an integer by extracting and parsing the text from the count element.
     *
     * @return the numeric count of the item
     */
    public int getCount(){
        return Integer.parseInt(spanCount.getText().replace(" x ",""));
    }

    /**
     * Increments the item count by clicking the plus button.
     *
     * @return this component instance for method chaining
     */
    public ShortItemComponent clickPlus(){
        waitUntilElementClickable(buttonPlus);
        buttonPlus.click();
        return this;
    }

    /**
     * Clicks the minus button to decrease the item count.
     *
     * Waits until the minus button is clickable before performing the action.
     *
     * @return this component instance for method chaining
     */
    public ShortItemComponent clickMinus(){
        waitUntilElementClickable(buttonMinus);
        buttonMinus.click();
        return this;
    }
}
