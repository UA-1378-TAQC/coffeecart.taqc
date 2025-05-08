package com.coffeecart.ui.page;

import com.coffeecart.ui.Base;
import com.coffeecart.ui.component.HeaderComponent;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Objects;


public abstract class BasePage extends Base {
    @Getter
    public HeaderComponent header;

    @Getter
    @FindBy(xpath = "//div[@id='app']/ul")
    private WebElement headerRoot;


    public BasePage(WebDriver driver) {
        super(driver);
        header = new HeaderComponent(driver, headerRoot);
    }

    @Step("Retrieve the full content height of the page")
    private int getContentHeight() {
        return ((Number) Objects.requireNonNull(threadJs.executeScript("return document.body.scrollHeight;"))).intValue();
    }

    @Step("Wait for the page to load within {0} seconds")
    public void waitForPageToLoad(long timeoutInSeconds) {
        new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds)).until((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
    }
    @Step("Check if element is invisible: {0}")
    public Boolean isElementInvisible(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        return wait.until(ExpectedConditions.invisibilityOf(element));
    }
}
