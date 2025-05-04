package com.coffeecart.ui;

import com.coffeecart.data.DrinkEnum;
import com.coffeecart.ui.component.CardComponent;
import com.coffeecart.ui.modal.AddModal;
import com.coffeecart.ui.page.MenuPage;
import com.coffeecart.ui.testrunners.BaseTestRunner;
import io.qameta.allure.Issue;
import io.qameta.allure.Owner;
import jdk.jfr.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class TC12 extends BaseTestRunner {

    private static final String TARGET_DRINK = DrinkEnum.getName(DrinkEnum.ESPRESSO_MACCHIATO);

    @AfterMethod
    public void resetDriver() {
        driver.get(testValueProvider.getBaseUIUrl());
    }

    @Test
    @Description("TC12: Verify 'No' button closes the modal and keeps the cart empty")
    @Issue("14")
    @Owner("Roman Fedko")
    public void verifyNoButtonOnAddModal() {

        MenuPage menu = new MenuPage(driver);

        CardComponent targetCard = menu.getCards().stream()
                .filter(c -> c.getName().equals(TARGET_DRINK))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException("Card with name " + TARGET_DRINK + " not found"));

        AddModal modal = targetCard.getCupComponent().rightClickCupBody();
        Assert.assertTrue(modal.isModalDisplayed(), "Modal window did not open after right‑click");

        Assert.assertTrue(modal.getYesButton().isDisplayed(), "Yes button missing");
        Assert.assertTrue(modal.getNoButton().isDisplayed(),  "No button missing");

        String borderBefore = modal.getNoButtonBorderColor();
        modal.hoverNoButton();

        new WebDriverWait(driver, Duration.ofSeconds(2))
                .until(d -> !modal.getNoButtonBorderColor().equals(borderBefore));

        Assert.assertEquals(
                modal.getNoButtonBorderColor(),
                "#daa520",
                "Border colour of 'No' button did not change on hover"
        );

        modal.clickNo();

        new WebDriverWait(driver, Duration.ofSeconds(2))
                .until(ExpectedConditions.invisibilityOfElementLocated(
                        By.xpath("//dialog[@data-cy='add-to-cart-modal']")));

        Assert.assertEquals(
                menu.getHeader().getTotalNumberItemsFromCartLink(),
                0,
                "Cart should remain empty"
        );

        driver.navigate().refresh();
        MenuPage menuAfterReload = new MenuPage(driver);

        CardComponent secondCard = menuAfterReload.getCards().stream()
                .filter(c -> !c.getName().equals(TARGET_DRINK))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No second card found"));

        AddModal secondModal = secondCard.getCupComponent().rightClickCupBody();
        Assert.assertTrue(secondModal.isModalDisplayed(), "Modal did not open after reload");
        Assert.assertTrue(secondModal.getYesButton().isDisplayed(), "Yes button missing in second modal");
        Assert.assertTrue(secondModal.getNoButton().isDisplayed(),  "No button missing in second modal");
    }
}
