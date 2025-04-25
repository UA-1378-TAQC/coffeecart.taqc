package com.coffeecart.ui.page.navigation;

import org.openqa.selenium.WebDriver;

public class PageNavigatorImpl implements Navigator{
    private WebDriver driver;

    public PageNavigatorImpl(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public void openPage(String url) {
        driver.get(url);
    }

}
