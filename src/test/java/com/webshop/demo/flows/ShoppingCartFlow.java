package com.webshop.demo.flows;

import com.webshop.demo.managers.PageObjectManager;
import com.webshop.demo.pages.CheckoutPage;
import com.webshop.demo.pages.ShoppingCartPage;

public class ShoppingCartFlow {
    private final PageObjectManager pages;

    public ShoppingCartFlow(PageObjectManager pages) {
        this.pages = pages;
    }

    public CheckoutPage goToShoppingCart(String selectCountry, String selectState, String selectZipCode) {
        ShoppingCartPage shoppingCartPage = pages.getShoppingCartPage();
        shoppingCartPage.goToShoppingCart();
        shoppingCartPage.setSelectCountry(selectCountry)
                .waitForStateToLoad()
                .setSelectState(selectState)
                .setAddZipCode(selectZipCode)
                .clickEstimateShippingButton()
                .clickTermsOfServiceCheckbox();
        return shoppingCartPage.clickCheckOutButton();
    }

}
