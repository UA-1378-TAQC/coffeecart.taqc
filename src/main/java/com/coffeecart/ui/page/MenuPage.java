package com.coffeecart.ui.page;

import com.coffeecart.ui.component.CardComponent;
import com.coffeecart.ui.component.CartComponent;
import com.coffeecart.ui.component.LuckyDayComponent;
import com.coffeecart.ui.data.Colors;
import com.coffeecart.ui.elements.TotalButtonElement;
import com.coffeecart.ui.modal.PaymentDetailModal;
import io.qameta.allure.Step;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class MenuPage extends BasePage {
    @Getter
    private List<CardComponent> cards = new ArrayList<>();

    @Getter
    @FindBy(xpath = "//*[@id='app']/div[2]/ul/li")
    private List<WebElement> rootCards;

    @Getter
    @FindBy(xpath = "//div[@class='modal']/div[@class='modal-content size']")
    private WebElement paymentModalRoot;

    @Getter
    @FindBy(xpath = "//div[@class='promo']")
    private WebElement luckyDayModalRoot;

    @Getter
    @FindBy(xpath = "//button[@class='pay']")
    private WebElement totalButton;

    @Getter
    @FindBy(xpath = "//*[@class=\"pay-container\"]")
    private WebElement payContainer;

    @Getter
    @FindBy(xpath = "//button[@id='submit-payment' and @type='submit']")
    private WebElement submitButton;

    @Getter
    @FindBy(xpath = "//input[@name='email']")
    private WebElement emailField;
    
    @Getter
    @FindBy(xpath = "//ul[@class='cart-preview show']")
    private WebElement cartPreview;

    public MenuPage(WebDriver driver) {
        super(driver);
        for (WebElement card : rootCards) {
            cards.add(new CardComponent(driver, card));
        }
    }
    @Step("Display and preview cart pop-up")
    public CartComponent getCartPreview(WebElement totalButton) {
        moveToElement(totalButton);
        return new CartComponent(driver, cartPreview);
    }

    @Step("Click 'Total' button")
    public PaymentDetailModal clickTotalButton() {
        return getButtonElement().clickTotalButton();
    }

    public TotalButtonElement getButtonElement() {
        return new TotalButtonElement(driver, totalButton);
    }


    public CardComponent getCardByName(String drinkName) {
        return cards.stream()
                .filter(c -> c.getName().equals(drinkName))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException("Card with name " + drinkName + " not found"));
    }

    public LuckyDayComponent getLuckyDayComponent() {
        return new LuckyDayComponent(driver, getLuckyDayModalRoot());
    }

    @Step("Click on cup with title: {drinkName}")
    public MenuPage clickDrink(String drinkName) {
        getCards().stream()
                .filter(card -> card.getName().equals(drinkName))
                .findFirst()
                .ifPresent(component -> component.getCupComponent().clickOnCupBody());
        return this;
    }

    @Step("Navigate to the Cart Page")
    public CartPage goToCartPage() {
        return header.navigateToCart();
    }

    @Step("Navigate to the GitHub Page")
    public GitHubPage goToGitHubPage() {
        return header.navigateToGitHub();
    }

    public boolean isSubmitButtonEnabled() {
        return submitButton.isEnabled();
    }

    public String getEmailValidationMessage() {
        WebElement emailField = driver.findElement(By.id("email"));
        return emailField.getAttribute("validationMessage");
    }

    public boolean isLuckyModalNotDisplayed() {
        try {

            return !getLuckyDayModalRoot().isDisplayed();
        } catch (NoSuchElementException e) {
            return true;
        }
    }

    public boolean isBackgroundColorOfPaymentDetailModalMatch() {
        String color = paymentModalRoot.getCssValue("background-color");

        String turquoise = Colors.TURQUOISE.getColor();
        String terracotta = Colors.TERRACOTTA.getColor();

        int[] actualRgb = parseRgb(color);
        int[] turquoiseRgb = parseRgb(turquoise);
        int[] terracottaRgb = parseRgb(terracotta);

        boolean redInRange = isInRange(actualRgb[0], turquoiseRgb[0], terracottaRgb[0]);
        boolean greenInRange = isInRange(actualRgb[1], turquoiseRgb[1], terracottaRgb[1]);
        boolean blueInRange = isInRange(actualRgb[2], turquoiseRgb[2], terracottaRgb[2]);

        return redInRange && greenInRange && blueInRange;

    }

    private int[] parseRgb(String rgbString) {
        String clean = rgbString.replaceAll("[^0-9,]", "");
        String[] parts = clean.split(",");

        return new int[]{
                Integer.parseInt(parts[0].trim()),
                Integer.parseInt(parts[1].trim()),
                Integer.parseInt(parts[2].trim())
        };
    }

    private boolean isInRange(int value, int bound1, int bound2) {
        int min = Math.min(bound1, bound2);
        int max = Math.max(bound1, bound2);
        return value >= min && value <= max;
    }
    public boolean isLuckyModalDisplayed() {
        return getLuckyDayModalRoot().isDisplayed();
    }
}

