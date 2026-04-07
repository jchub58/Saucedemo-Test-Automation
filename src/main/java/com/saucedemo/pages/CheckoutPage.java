package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {

    private By firstNameField = By.id("first-name");
    private By lastNameField = By.id("last-name");
    private By postalCodeField = By.id("postal-code");
    private By continueButton = By.id("continue");
    private By finishButton = By.id("finish");
    private By cancelButton = By.id("cancel");
    private By completeHeader = By.className("complete-header");
    private By errorMessage = By.cssSelector("[data-test='error']");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public void fillCheckoutInfo(String firstName, String lastName,
                                 String postalCode) {
        waitAndSendKeys(firstNameField, firstName);
        waitAndSendKeys(lastNameField, lastName);
        waitAndSendKeys(postalCodeField, postalCode);
    }

    public void clickContinue() {
        waitAndClick(continueButton);
    }

    public void clickFinish() {
        waitAndClick(finishButton);
    }

    public String getErrorMessage() {
        return waitAndGetText(errorMessage);
    }

    public void clickCancel() {
        waitAndClick(cancelButton);
    }

    public String getCompleteMessage() {
        return waitAndGetText(completeHeader);
    }

    public boolean isOrderComplete() {
        return isElementVisible(completeHeader);
    }
}