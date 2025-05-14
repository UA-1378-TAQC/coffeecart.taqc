package com.coffeecart.ui.component;

import com.coffeecart.ui.page.CartPage;
import io.qameta.allure.Step;
import lombok.Getter;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.NoSuchElementException;


import java.util.Arrays;

public class FullItemComponent extends BaseComponent{
    @Getter
    @FindBy(xpath = ".//div[position()=1 and not(@class)]")
    private WebElement itemLabel;

    @Getter
    @FindBy(xpath = ".//span[@class='unit-desc']")
    private WebElement unitDescription;

    @Getter
    @FindBy(xpath = ".//div[@class='unit-controller']/button[contains(text(), '+')]")
    private WebElement addButton;

    @Getter
    @FindBy(xpath = ".//div[@class='unit-controller']/button[contains(text(), '-')]")
    private WebElement removeButton;

    @Getter
    @FindBy(xpath = ".//div/button[@class='delete']")
    private WebElement deleteButton;

    @Getter
    @FindBy(xpath = ".//div[@class='unit-controller']/../following-sibling::*[1]")
    private WebElement totalPrice;

    public FullItemComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    public String getItemLabelString(){
        return itemLabel.getText();
    }

    public String getUnitDescString(){
        return unitDescription.getText();
    }

    public String getTotalPriceString(){
        return totalPrice.getText();
    }

    public double getPriceOfOneUnit(){
        int charsToSkip = 1;
        return Double.parseDouble(Arrays.stream(getUnitDescString().split("x")).toList().getFirst().trim().substring(charsToSkip));
    }

    public int getCount(){
        return Integer.parseInt(Arrays.stream(getUnitDescString().split("x")).toList().getLast().trim());
    }

    public double getTotalPriceAsNumber(){
        int charsToSkip = 1;
        return Double.parseDouble(getTotalPriceString().substring(charsToSkip).trim());
    }

    @Step("Click delete button of cart list item ")
    public CartPage clickOnDeleteButton(){
        waitUntilElementClickable(deleteButton);
        deleteButton.click();
        return new CartPage(driver);
    }

    @Step("Click add button of cart list item once")
    public CartPage clickOnAddButton(){
        return clickOnAddButton(1);
    }

    @Step("Click add button of cart list item n times")
    public CartPage clickOnAddButton(int times){
        if(times <= 0){
            throw new InvalidArgumentException("Number of times should be more than 0.");
        }
        for(int i = 0; i < times; i++){
            waitUntilElementClickable(addButton);
            addButton.click();
        }
        return new CartPage(driver);
    }

    @Step("Click remove button of cart list item once")
    public CartPage clickOnRemoveButton(){
        return clickOnRemoveButton(1);
    }

    @Step("Click remove button of cart list item n times")
    public CartPage clickOnRemoveButton(int times){
        if(times <= 0 || getCount() < times){
            throw new InvalidArgumentException("Number of times should be more than 0 and less or equal item count.");
        }
        for(int i = 0; i < times; i++){
            waitUntilElementClickable(removeButton);
            removeButton.click();
        }
        return new CartPage(driver);
    }

    @Step("Checks it '+' is enabled")
    public boolean plusButtonIsEnabled() {
    try {
        WebElement plusButton = rootElement.findElement(By.className("plus")); 
        return plusButton.isEnabled();
    } catch (NoSuchElementException e) {
        return false;
    }
}
}
