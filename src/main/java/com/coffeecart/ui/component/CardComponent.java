package com.coffeecart.ui.component;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


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
    @FindBy(xpath = "//div[contains(@class, 'confirmation-dialog')]")
    private WebElement confirmationDialog;
    @Getter
    @FindBy(xpath = "//button[contains(text(), 'Yes')]")
    private WebElement confirmYesButton;
    @Getter
    @FindBy(xpath = "//button[contains(text(), 'No')]")
    private WebElement confirmNoButton;

    private CupComponent cupComponent;

    private static final Duration TIMEOUT = Duration.ofSeconds(10);

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

    public CupComponent getCupComponent() {
        return cupComponent;
    }

    public void addToCartViaLeftClick() {
        rootElement.click();
    }

    public void addToCartViaContextMenu() {
        Actions actions = new Actions(driver);
        actions.contextClick(rootElement).perform();

        WebElement dialog = waitForElementToBeVisible(confirmationDialog);
        confirmYesButton.click();
    }
    public void rejectAddToCartViaContextMenu() {
        Actions actions = new Actions(driver);
        actions.contextClick(rootElement).perform();

        WebElement dialog = waitForElementToBeVisible(confirmationDialog);
        confirmNoButton.click();
    }
    private WebElement waitForElementToBeVisible(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, TIMEOUT);
        return wait.until(ExpectedConditions.visibilityOf(element));
    }
}
