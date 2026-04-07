package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutCompletePage extends BasePage {
    
    private By completeHeader = By.className("complete-header");
    private By completeText = By.className("complete-text");
    private By backToProductsButton = By.id("back-to-products");

    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
    }
    
    public String getCompleteMessage() {
        return waitAndGetText(completeHeader);
    }
    
    public String getCompleteText() {
        return waitAndGetText(completeText);
    }
    
    public void clickBackToProducts() {
        waitAndClick(backToProductsButton);
    }
    
    public boolean isOrderComplete() {
        return isElementVisible(completeHeader);
    }
}
