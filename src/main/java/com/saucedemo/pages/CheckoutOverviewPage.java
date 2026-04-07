package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutOverviewPage extends BasePage {
    
    private By pageTitle = By.className("title");
    private By finishButton = By.id("finish");
    private By cancelButton = By.id("cancel");
    private By subtotalLabel = By.className("summary_subtotal_label");
    private By taxLabel = By.className("summary_tax_label");
    private By totalLabel = By.className("summary_total_label");

    public CheckoutOverviewPage(WebDriver driver) {
        super(driver);
    }
    
    public String getPageTitle() {
        return waitAndGetText(pageTitle);
    }
    
    public void clickFinish() {
        waitAndClick(finishButton);
    }
    
    public void clickCancel() {
        waitAndClick(cancelButton);
    }
    
    public String getSubtotal() {
        return waitAndGetText(subtotalLabel);
    }
    
    public String getTax() {
        return waitAndGetText(taxLabel);
    }
    
    public String getTotal() {
        return waitAndGetText(totalLabel);
    }
}
