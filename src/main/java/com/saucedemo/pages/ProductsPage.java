package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import java.util.List;

/**
 * Products listing page object
 */
public class ProductsPage extends BasePage {
    
    private By pageTitle = By.className("title");
    private By productItems = By.className("inventory_item");
    private By addToCartButtons = By.cssSelector("[data-test^='add-to-cart']");
    private By removeFromCartButtons = By.cssSelector("[data-test^='remove']");
    private By cartBadge = By.className("shopping_cart_badge");
    private By cartLink = By.className("shopping_cart_link");
    private By sortDropdown = By.className("product_sort_container");
    private By hamburgerMenu = By.id("react-burger-menu-btn");
    private By logoutLink = By.id("logout_sidebar_link");
    private By resetAppStateLink = By.id("reset_sidebar_link");

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    public String getPageTitle() {
        return waitAndGetText(pageTitle);
    }

    public int getProductCount() {
        return waitForElements(productItems).size();
    }

    public void addProductToCart(int index) {
        List<WebElement> buttons = waitForElements(addToCartButtons);
        if (index < buttons.size()) {
            buttons.get(index).click();
        }
    }
    
    public void removeProductFromCart(int index) {
        List<WebElement> buttons = waitForElements(removeFromCartButtons);
        if (index < buttons.size()) {
            buttons.get(index).click();
        }
    }

    public void goToCart() {
        waitAndClick(cartLink);
    }

    public String getCartBadgeCount() {
        if (isElementVisible(cartBadge)) {
            return waitAndGetText(cartBadge);
        }
        return "0";
    }
    
    public void sortProducts(String sortOption) {
        Select sort = new Select(waitForElement(sortDropdown));
        sort.selectByVisibleText(sortOption);
    }
    
    public void openHamburgerMenu() {
        waitAndClick(hamburgerMenu);
    }
    
    public void clickLogout() {
        waitAndClick(logoutLink);
    }
    
    public void clickResetAppState() {
        waitAndClick(resetAppStateLink);
    }
}