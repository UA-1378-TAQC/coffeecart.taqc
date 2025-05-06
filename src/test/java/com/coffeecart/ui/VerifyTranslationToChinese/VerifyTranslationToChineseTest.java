package com.coffeecart.ui.VerifyTranslationToChinese;


import com.coffeecart.data.DrinkEnum;
import com.coffeecart.ui.testrunners.BaseTestRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class VerifyTranslationToChineseTest extends BaseTestRunner {

    @Test
    public void testChineseNamesOfProducts() {
        Actions actions = new Actions(driver);

        List<WebElement> productNameElements = driver.findElements(By.xpath("//*[@id='app']/div[2]/ul/li//h4"));
        SoftAssert softAssert = new SoftAssert();

        for (DrinkEnum drink : DrinkEnum.values()) {
            boolean isCorrectTranslation = productNameElements.stream().anyMatch(element -> {
                actions.doubleClick(element).perform();
                return element.getText().contains(drink.getChineseName(drink));
            });
            softAssert.assertTrue(isCorrectTranslation,
                    String.format("The Chinese name for '%s' is incorrect or missing after double-click.", drink.getName(drink)));
        }

        softAssert.assertAll();
    }
}