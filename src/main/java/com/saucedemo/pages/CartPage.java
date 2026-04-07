package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {
    
    private By cartItems = By.className("cart_item");
    private By checkoutButton = By.id("checkout");
    private By continueShoppingButton = By.id("continue-shopping");
    private By removeFromCartButtons = By.cssSelector("[data-test^='remove']");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public int getCartItemCount() {
        return waitForElements(cartItems).size();
    }

    public void proceedToCheckout() {
        waitAndClick(checkoutButton);
    }
    
    public void continueShopping() {
        waitAndClick(continueShoppingButton);
    }
    
    public void removeProductFromCart(int index) {
        var buttons = waitForElements(removeFromCartButtons);
        if (index < buttons.size()) {
            buttons.get(index).click();
        }
    }
}