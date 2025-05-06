package com.coffeecart.ui.component;

import com.coffeecart.ui.page.MenuPage;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;


public class CardComponent extends BaseComponent {
    @Getter
    @FindBy(xpath = ".//h4")
    private WebElement nameElement;
    @Getter
    @FindBy(xpath = ".//h4/small")
    private WebElement priceElement;
    @Getter
    @FindBy(xpath = ".//div[contains(@class, 'cup')]")
    private WebElement cupRootElement;
    @Getter
    @FindBy(xpath = ".//div[contains(@class, 'cup-body')]")
    private WebElement cupBodyElement;

    private CupComponent cupComponent;

    public CardComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
        this.cupComponent = new CupComponent(driver, cupRootElement);
    }

    public String getName() {
        return nameElement.getText().split("\n")[0].trim();
    }

    public double getPrice() {
        String priceText = priceElement.getText().replace("$", "").trim();
        return Double.parseDouble(priceText);
    }
    public String getNameColorOnHover() {
        Actions actions = new Actions(driver);
        actions.moveToElement(nameElement).perform();
        return nameElement.getCssValue("color");
    }

    public CardComponent clickOnCup() {
        waitUntilElementClickable(cupRootElement);
        cupRootElement.click();
        return this;
    }

    public CardComponent clickOnName() {
        waitUntilElementClickable(nameElement);
        nameElement.click();
        return this;
    }
    public String getCupAriaLabel() {
        return cupBodyElement.getAttribute("aria-label");
    }

    public String getCupTestAttribute() {
        return cupBodyElement.getAttribute("data-test");
    }

    public boolean isCupDisplayed() {
        return cupRootElement.isDisplayed();
    }

    public boolean validateCard(String expectedName, double expectedPrice) {
        return getName().equals(expectedName) && getPrice() == expectedPrice;
    }

    public String getCardColor() {
        return rootElement.getCssValue("background-color");
    }

    public CupComponent getCupComponent() {
        return cupComponent;
    }

    @Step("Click on cup")
    public MenuPage clickCup() {
        return cupComponent.clickOnCupBody();
    }
}

