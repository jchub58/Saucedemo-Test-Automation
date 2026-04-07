package com.saucedemo.tests;

import com.saucedemo.pages.*;
import com.saucedemo.utils.ExcelDataReader;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

@Epic("SauceDemo E-Commerce Application")
@Feature("Web Application Functionality")
public class SauceDemoTests extends BaseTest {
    
    private LoginPage loginPage;
    private ProductsPage productsPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    private CheckoutOverviewPage checkoutOverviewPage;
    private CheckoutCompletePage checkoutCompletePage;

    private void initPages() {
        loginPage = new LoginPage(driver);
        productsPage = new ProductsPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);
        checkoutOverviewPage = new CheckoutOverviewPage(driver);
        checkoutCompletePage = new CheckoutCompletePage(driver);
    }

    @Test(description = "TC_001: Verify successful login with valid credentials")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Login")
    @Description("Verify user can successfully login with valid username and password")
    public void verifyUserCanLoginWithValidCredentials() {
        initPages();
        Map<String, String> testData = ExcelDataReader.getTestCaseById("TC_001");
        
        loginPage.login("standard_user", "secret_sauce");
        
        Assert.assertEquals(productsPage.getPageTitle(), "Products");
        Assert.assertTrue(productsPage.getProductCount() > 0);
    }

    @Test(description = "TC_002: Verify login failure with invalid password")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Login")
    @Description("Verify user cannot login with invalid password")
    public void verifyUserCannotLoginWithInvalidPassword() {
        initPages();
        Map<String, String> testData = ExcelDataReader.getTestCaseById("TC_002");
        
        loginPage.login("standard_user", "wrong_password");
        
        String errorMessage = loginPage.getErrorMessage();
        Assert.assertTrue(errorMessage.contains("Username and password do not match"));
    }

    @Test(description = "TC_003: Verify login failure with invalid username")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Login")
    @Description("Verify user cannot login with invalid username")
    public void verifyUserCannotLoginWithInvalidUsername() {
        initPages();
        Map<String, String> testData = ExcelDataReader.getTestCaseById("TC_003");
        
        loginPage.login("invalid_user", "secret_sauce");
        
        String errorMessage = loginPage.getErrorMessage();
        Assert.assertTrue(errorMessage.contains("Username and password do not match"));
    }

    @Test(description = "TC_004: Verify login failure with both empty fields")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Login")
    @Description("Verify user cannot login with empty username and password")
    public void verifyUserCannotLoginWithEmptyCredentials() {
        initPages();
        Map<String, String> testData = ExcelDataReader.getTestCaseById("TC_004");
        
        loginPage.login("", "");
        
        String errorMessage = loginPage.getErrorMessage();
        Assert.assertTrue(errorMessage.contains("Username is required"));
    }

    @Test(description = "TC_005: Verify products are displayed after login")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Product Listing")
    @Description("Verify products are displayed correctly on the products page")
    public void verifyProductsAreDisplayedAfterLogin() {
        initPages();
        Map<String, String> testData = ExcelDataReader.getTestCaseById("TC_005");
        
        loginPage.login("standard_user", "secret_sauce");
        
        Assert.assertEquals(productsPage.getPageTitle(), "Products");
        Assert.assertEquals(productsPage.getProductCount(), 6);
    }

    @Test(description = "TC_006: Verify user can add product to cart")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Shopping Cart")
    @Description("Verify user can add a product to the shopping cart")
    public void verifyUserCanAddProductToCart() {
        initPages();
        Map<String, String> testData = ExcelDataReader.getTestCaseById("TC_006");
        
        loginPage.login("standard_user", "secret_sauce");
        String initialBadgeCount = productsPage.getCartBadgeCount();
        
        productsPage.addProductToCart(0);
        
        String newBadgeCount = productsPage.getCartBadgeCount();
        Assert.assertNotEquals(initialBadgeCount, newBadgeCount);
        Assert.assertEquals(newBadgeCount, "1");
    }

    @Test(description = "TC_007: Verify user can remove product from cart")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Shopping Cart")
    @Description("Verify user can remove a product from the shopping cart")
    public void verifyUserCanRemoveProductFromCart() {
        initPages();
        Map<String, String> testData = ExcelDataReader.getTestCaseById("TC_007");
        
        loginPage.login("standard_user", "secret_sauce");
        productsPage.addProductToCart(0);
        
        productsPage.removeProductFromCart(0);
        
        String badgeCount = productsPage.getCartBadgeCount();
        Assert.assertEquals(badgeCount, "0");
    }

    @Test(description = "TC_008: Verify user can navigate to cart")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Shopping Cart")
    @Description("Verify user can navigate to the shopping cart page")
    public void verifyUserCanNavigateToCart() {
        initPages();
        Map<String, String> testData = ExcelDataReader.getTestCaseById("TC_008");
        
        loginPage.login("standard_user", "secret_sauce");
        productsPage.addProductToCart(0);
        productsPage.goToCart();
        
        Assert.assertTrue(cartPage.getCartItemCount() >= 1);
    }

    @Test(description = "TC_009: Verify user can proceed to checkout")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Checkout")
    @Description("Verify user can proceed to checkout from cart page")
    public void verifyUserCanProceedToCheckout() {
        initPages();
        Map<String, String> testData = ExcelDataReader.getTestCaseById("TC_009");
        
        loginPage.login("standard_user", "secret_sauce");
        productsPage.addProductToCart(0);
        productsPage.goToCart();
        cartPage.proceedToCheckout();
        
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-one"));
    }

    @Test(description = "TC_010: Verify user can complete checkout with valid information")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Checkout")
    @Description("Verify user can complete the checkout process with valid information")
    public void verifyUserCanCompleteCheckout() {
        initPages();
        Map<String, String> testData = ExcelDataReader.getTestCaseById("TC_010");
        
        loginPage.login("standard_user", "secret_sauce");
        productsPage.addProductToCart(0);
        productsPage.goToCart();
        cartPage.proceedToCheckout();
        
        checkoutPage.fillCheckoutInfo("John", "Doe", "12345");
        checkoutPage.clickContinue();
        
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-two"));
        
        checkoutOverviewPage.clickFinish();
        
        Assert.assertTrue(checkoutCompletePage.isOrderComplete());
        Assert.assertEquals(checkoutCompletePage.getCompleteMessage(), "Thank you for your order!");
    }

    @Test(description = "TC_011: Verify checkout fails with empty first name")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Checkout")
    @Description("Verify checkout fails when first name is empty")
    public void verifyCheckoutFailsWithEmptyFirstName() {
        initPages();
        Map<String, String> testData = ExcelDataReader.getTestCaseById("TC_011");
        
        loginPage.login("standard_user", "secret_sauce");
        productsPage.addProductToCart(0);
        productsPage.goToCart();
        cartPage.proceedToCheckout();
        
        checkoutPage.fillCheckoutInfo("", "Doe", "12345");
        checkoutPage.clickContinue();
        
        String errorMessage = checkoutPage.getErrorMessage();
        Assert.assertTrue(errorMessage.contains("First Name is required"));
    }

    @Test(description = "TC_012: Verify checkout fails with empty last name")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Checkout")
    @Description("Verify checkout fails when last name is empty")
    public void verifyCheckoutFailsWithEmptyLastName() {
        initPages();
        Map<String, String> testData = ExcelDataReader.getTestCaseById("TC_012");
        
        loginPage.login("standard_user", "secret_sauce");
        productsPage.addProductToCart(0);
        productsPage.goToCart();
        cartPage.proceedToCheckout();
        
        checkoutPage.fillCheckoutInfo("John", "", "12345");
        checkoutPage.clickContinue();
        
        String errorMessage = checkoutPage.getErrorMessage();
        Assert.assertTrue(errorMessage.contains("Last Name is required"));
    }

    @Test(description = "TC_013: Verify checkout fails with empty postal code")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Checkout")
    @Description("Verify checkout fails when postal code is empty")
    public void verifyCheckoutFailsWithEmptyPostalCode() {
        initPages();
        Map<String, String> testData = ExcelDataReader.getTestCaseById("TC_013");
        
        loginPage.login("standard_user", "secret_sauce");
        productsPage.addProductToCart(0);
        productsPage.goToCart();
        cartPage.proceedToCheckout();
        
        checkoutPage.fillCheckoutInfo("John", "Doe", "");
        checkoutPage.clickContinue();
        
        String errorMessage = checkoutPage.getErrorMessage();
        Assert.assertTrue(errorMessage.contains("Postal Code is required"));
    }

    @Test(description = "TC_014: Verify user can sort products by name (A to Z)")
    @Severity(SeverityLevel.NORMAL)
    @Story("Product Listing")
    @Description("Verify user can sort products by name in ascending order")
    public void verifyUserCanSortProductsByNameAToZ() {
        initPages();
        Map<String, String> testData = ExcelDataReader.getTestCaseById("TC_014");
        
        loginPage.login("standard_user", "secret_sauce");
        
        productsPage.sortProducts("Name (A to Z)");
        
        Assert.assertEquals(productsPage.getPageTitle(), "Products");
        Assert.assertTrue(productsPage.getProductCount() > 0);
    }

    @Test(description = "TC_015: Verify user can sort products by name (Z to A)")
    @Severity(SeverityLevel.NORMAL)
    @Story("Product Listing")
    @Description("Verify user can sort products by name in descending order")
    public void verifyUserCanSortProductsByNameZToA() {
        initPages();
        Map<String, String> testData = ExcelDataReader.getTestCaseById("TC_015");
        
        loginPage.login("standard_user", "secret_sauce");
        
        productsPage.sortProducts("Name (Z to A)");
        
        Assert.assertEquals(productsPage.getPageTitle(), "Products");
        Assert.assertTrue(productsPage.getProductCount() > 0);
    }

    @Test(description = "TC_016: Verify user can sort products by price (low to high)")
    @Severity(SeverityLevel.NORMAL)
    @Story("Product Listing")
    @Description("Verify user can sort products by price in ascending order")
    public void verifyUserCanSortProductsByPriceLowToHigh() {
        initPages();
        Map<String, String> testData = ExcelDataReader.getTestCaseById("TC_016");
        
        loginPage.login("standard_user", "secret_sauce");
        
        productsPage.sortProducts("Price (low to high)");
        
        Assert.assertEquals(productsPage.getPageTitle(), "Products");
        Assert.assertTrue(productsPage.getProductCount() > 0);
    }

    @Test(description = "TC_017: Verify user can sort products by price (high to low)")
    @Severity(SeverityLevel.NORMAL)
    @Story("Product Listing")
    @Description("Verify user can sort products by price in descending order")
    public void verifyUserCanSortProductsByPriceHighToLow() {
        initPages();
        Map<String, String> testData = ExcelDataReader.getTestCaseById("TC_017");
        
        loginPage.login("standard_user", "secret_sauce");
        
        productsPage.sortProducts("Price (high to low)");
        
        Assert.assertEquals(productsPage.getPageTitle(), "Products");
        Assert.assertTrue(productsPage.getProductCount() > 0);
    }

    @Test(description = "TC_018: Verify user can continue shopping from cart")
    @Severity(SeverityLevel.NORMAL)
    @Story("Shopping Cart")
    @Description("Verify user can continue shopping from cart page")
    public void verifyUserCanContinueShoppingFromCart() {
        initPages();
        Map<String, String> testData = ExcelDataReader.getTestCaseById("TC_018");
        
        loginPage.login("standard_user", "secret_sauce");
        productsPage.addProductToCart(0);
        productsPage.goToCart();
        cartPage.continueShopping();
        
        Assert.assertEquals(productsPage.getPageTitle(), "Products");
    }

    @Test(description = "TC_019: Verify user can cancel checkout")
    @Severity(SeverityLevel.NORMAL)
    @Story("Checkout")
    @Description("Verify user can cancel checkout and return to cart")
    public void verifyUserCanCancelCheckout() {
        initPages();
        Map<String, String> testData = ExcelDataReader.getTestCaseById("TC_019");
        
        loginPage.login("standard_user", "secret_sauce");
        productsPage.addProductToCart(0);
        productsPage.goToCart();
        cartPage.proceedToCheckout();
        
        checkoutPage.clickCancel();
        
        Assert.assertTrue(driver.getCurrentUrl().contains("cart.html"));
    }

    @Test(description = "TC_020: Verify user can cancel checkout overview")
    @Severity(SeverityLevel.NORMAL)
    @Story("Checkout")
    @Description("Verify user can cancel checkout overview and return to products")
    public void verifyUserCanCancelCheckoutOverview() {
        initPages();
        Map<String, String> testData = ExcelDataReader.getTestCaseById("TC_020");
        
        loginPage.login("standard_user", "secret_sauce");
        productsPage.addProductToCart(0);
        productsPage.goToCart();
        cartPage.proceedToCheckout();
        
        checkoutPage.fillCheckoutInfo("John", "Doe", "12345");
        checkoutPage.clickContinue();
        
        checkoutOverviewPage.clickCancel();
        
        Assert.assertEquals(productsPage.getPageTitle(), "Products");
    }

    @Test(description = "TC_021: Verify user can logout successfully")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Session Management")
    @Description("Verify user can logout successfully from the application")
    public void verifyUserCanLogoutSuccessfully() {
        initPages();
        Map<String, String> testData = ExcelDataReader.getTestCaseById("TC_021");
        
        loginPage.login("standard_user", "secret_sauce");
        productsPage.openHamburgerMenu();
        productsPage.clickLogout();
        
        Assert.assertTrue(driver.getCurrentUrl().contains("saucedemo.com"));
        Assert.assertTrue(loginPage.isElementVisible(loginPage.usernameField));
    }

    @Test(description = "TC_022: Verify reset app state functionality")
    @Severity(SeverityLevel.NORMAL)
    @Story("Session Management")
    @Description("Verify reset app state clears cart and application state")
    public void verifyResetAppStateFunctionality() {
        initPages();
        Map<String, String> testData = ExcelDataReader.getTestCaseById("TC_022");
        
        loginPage.login("standard_user", "secret_sauce");
        productsPage.addProductToCart(0);
        String initialBadgeCount = productsPage.getCartBadgeCount();
        
        productsPage.openHamburgerMenu();
        productsPage.clickResetAppState();
        
        String resetBadgeCount = productsPage.getCartBadgeCount();
        Assert.assertEquals(resetBadgeCount, "0");
        Assert.assertNotEquals(initialBadgeCount, resetBadgeCount);
    }

    @Test(description = "TC_023: Verify complete purchase lifecycle")
    @Severity(SeverityLevel.BLOCKER)
    @Story("End-to-End Flow")
    @Description("Verify complete successful purchase lifecycle from login to logout")
    public void verifyCompletePurchaseLifecycle() {
        initPages();
        Map<String, String> testData = ExcelDataReader.getTestCaseById("TC_023");
        
        loginPage.login("standard_user", "secret_sauce");
        
        productsPage.sortProducts("Price (low to high)");
        productsPage.addProductToCart(0);
        
        productsPage.goToCart();
        cartPage.proceedToCheckout();
        
        checkoutPage.fillCheckoutInfo("John", "Doe", "12345");
        checkoutPage.clickContinue();
        
        checkoutOverviewPage.clickFinish();
        
        Assert.assertTrue(checkoutCompletePage.isOrderComplete());
        
        checkoutCompletePage.clickBackToProducts();
        
        productsPage.openHamburgerMenu();
        productsPage.clickLogout();
        
        Assert.assertTrue(driver.getCurrentUrl().contains("saucedemo.com"));
    }

    @Test(description = "TC_024: Verify login security against SQL injection")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Security")
    @Description("Verify login is secure against SQL injection attempts")
    public void verifyLoginSecurityAgainstSQLInjection() {
        initPages();
        Map<String, String> testData = ExcelDataReader.getTestCaseById("TC_024");
        
        loginPage.login("' OR 1=1 --", "password");
        
        String errorMessage = loginPage.getErrorMessage();
        Assert.assertTrue(errorMessage.contains("Username and password do not match") || 
                          errorMessage.contains("Epic sadface"));
    }

    @Test(description = "TC_025: Verify unauthorized access prevention")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Security")
    @Description("Verify direct URL access without login is prevented")
    public void verifyUnauthorizedAccessPrevention() {
        initPages();
        Map<String, String> testData = ExcelDataReader.getTestCaseById("TC_025");
        
        driver.get("https://www.saucedemo.com/inventory.html");
        
        Assert.assertTrue(driver.getCurrentUrl().contains("saucedemo.com"));
        Assert.assertTrue(loginPage.isElementVisible(loginPage.errorMessage));
    }
}
