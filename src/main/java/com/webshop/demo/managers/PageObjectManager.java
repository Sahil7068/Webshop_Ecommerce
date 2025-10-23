package com.webshop.demo.managers;

import com.webshop.demo.pages.*;
import org.openqa.selenium.WebDriver;

public class PageObjectManager {

    private final WebDriver driver;
    private RegisterPage registerPage;
    private HomePage homePage;
    private LoginPage loginPage;
    private ShoppingCartPage shoppingCartPage;
    private CheckoutPage checkoutPage;

    public PageObjectManager(WebDriver driver) {
        this.driver = driver;
    }



    public RegisterPage getRegisterPage() {
        if (registerPage == null) registerPage = new RegisterPage(driver);
        return registerPage;
    }

    public LoginPage getLoginPage() {
        if (loginPage == null) loginPage = new LoginPage(driver);
        return loginPage;
    }

    public HomePage getHomePage() {
        if (homePage == null) homePage = new HomePage(driver);
        return homePage;
    }

    public ShoppingCartPage getShoppingCartPage() {
        if (shoppingCartPage == null) shoppingCartPage = new ShoppingCartPage(driver);
        return shoppingCartPage;
    }

    public CheckoutPage getCheckoutPage() {
        if (checkoutPage == null) checkoutPage = new CheckoutPage(driver);
        return checkoutPage;
    }
}
