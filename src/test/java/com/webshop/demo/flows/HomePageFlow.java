package com.webshop.demo.flows;

import com.webshop.demo.managers.PageObjectManager;
import com.webshop.demo.pages.HomePage;
import com.webshop.demo.pages.ShoppingCartPage;

public class HomePageFlow {
    private final PageObjectManager pages;

    public HomePageFlow(PageObjectManager pages) {
        this.pages = pages;
    }

    /**
     * Adds a product to the shopping cart
     * @param productName Name of the product to add
     * @return ShoppingCartPage instance for method chaining
     */
    public HomePage addProductToCart(String productName) {
        HomePage homePage = pages.getHomePage();
        homePage.addProductToCart(productName);
        homePage.waitForLoadingImageToDisappear();
        homePage.closeToast();

        return homePage;
    }
}
