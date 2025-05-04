package com.coffeecart.ui.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.function.Supplier;

public final class Waiter {

    private Waiter() {}

    public static void waitCssValueChanged(WebDriver driver,
                                           Supplier<String> currentValueSupplier,
                                           String oldValue,
                                           Duration timeout) {

        new WebDriverWait(driver, timeout)
                .until((ExpectedCondition<Boolean>) d ->
                        !currentValueSupplier.get().equals(oldValue));
    }

    public static void waitInvisibility(WebDriver driver,
                                        WebElement element,
                                        Duration timeout) {
        new WebDriverWait(driver, timeout)
                .until(org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOf(element));
    }
}
