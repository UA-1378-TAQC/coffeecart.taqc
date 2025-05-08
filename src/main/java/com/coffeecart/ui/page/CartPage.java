package com.coffeecart.ui.page;

import com.coffeecart.ui.component.FullItemComponent;
import com.coffeecart.ui.elements.TotalButtonElement;
import com.coffeecart.ui.modal.PaymentDetailModal;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CartPage extends BasePage {
    @Getter
    private final List<FullItemComponent> fullItems = new ArrayList<>();

    @Getter
    private TotalButtonElement totalButton;

    @Getter
    @FindBy(xpath = "//li[@class='list-header']/following::li[@class='list-item']")
    private List<WebElement> rootFullItems;

    @Getter
    @FindBy(xpath = "//div[@class='pay-container']/button[@class='pay']")
    private WebElement rootTotalButton;

    @Getter
    @FindBy(xpath = "//div[@class='list']/p")
    private WebElement emptyCartMessage;

    public CartPage(WebDriver driver) {
        super(driver);
        totalButton = new TotalButtonElement(driver, rootTotalButton);
        for (WebElement fullItem : rootFullItems) {
            fullItems.add(new FullItemComponent(driver, fullItem));
        }
    }

    @Step("Click on the Total button")
    public PaymentDetailModal clickOnTotalButton() {
        return totalButton.clickTotalButton();
    }

    @Step("Clean the cart")
    public CartPage cleanCart() {
        List<FullItemComponent> itemsToRemove = new ArrayList<>(fullItems);
        for (FullItemComponent fullItem : itemsToRemove) {
            fullItem.clickOnDeleteButton();
        }
        return this;
    }

    @Step("Delete the item {itemName} from the cart")
    public CartPage deleteItemFromCart(String itemName) {
        FullItemComponent fullItem = getFullItemByName(itemName);
        return fullItem.clickOnDeleteButton();
    }

    @Step("Increase number of items with name {itemName}")
    public CartPage increaseNumberOfItems(String itemName) {
        FullItemComponent fullItem = getFullItemByName(itemName);
        return fullItem.clickOnAddButton();
    }

    @Step("Increase number of items {times} times with name {itemName}")
    public CartPage increaseNumberOfItems(String itemName, int times) {
        FullItemComponent fullItem = getFullItemByName(itemName);
        return fullItem.clickOnAddButton(times);
    }

    @Step("Remove number of items with name {itemName}")
    public CartPage removeNumberOfItems(String itemName) {
        FullItemComponent fullItem = getFullItemByName(itemName);
        return fullItem.clickOnRemoveButton();
    }

    @Step("Remove number of items {times} times with name {itemName}")
    public CartPage removeNumberOfItems(String itemName, int times) {
        FullItemComponent fullItem = getFullItemByName(itemName);
        return fullItem.clickOnRemoveButton(times);
    }

    public FullItemComponent getFullItemByName(String itemName) {
        Optional<FullItemComponent> component = fullItems.stream().filter(item -> item.getItemLabelString().equals(itemName)).findAny();
        return component.orElse(null);
    }

    public int getTotalNumberOfItemsFromCart() {
        return fullItems.stream().mapToInt(FullItemComponent::getCount).sum();
    }

    public double getSumOfTotalPricesFromCart() {
        return fullItems.stream().mapToDouble(FullItemComponent::getTotalPriceAsNumber).sum();
    }

    @Step("Navigate to the Menu Page")
    public MenuPage goToMenuPage() {
        return header.navigateToMenu();
    }

    @Step("Navigate to the GitHub Page")
    public GitHubPage goToGitHubPage() {
        return header.navigateToGitHub();
    }

    public boolean emptyCartMessageIsDisplayed() {
        try {
            return emptyCartMessage.isDisplayed();
        } catch (
                NoSuchElementException e) {
            return false;

        }
    }
}
