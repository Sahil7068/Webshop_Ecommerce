package com.webshop.demo.tests;

import com.webshop.demo.annotations.JsonData;
import com.webshop.demo.base.BaseTest;
import com.webshop.demo.flows.HomePageFlow;
import com.webshop.demo.flows.LoginFlow;
import com.webshop.demo.pages.CheckoutPage;
import com.webshop.demo.pages.HomePage;
import com.webshop.demo.pages.RegisterPage;
import com.webshop.demo.pages.ShoppingCartPage;
import com.webshop.demo.pojo.HomePageData;
import com.webshop.demo.pojo.ShoppingCartData;
import com.webshop.demo.provider.JsonDataProvider;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Properties;

public class ShoppingCartTest extends BaseTest {

    @BeforeMethod
    public void setUp() throws IOException {
        Properties prop = loadProperties();
        LoginFlow loginFlow = new LoginFlow(pages);
        HomePage homePage = loginFlow.registerAndLogin();
        HomePageFlow homePageFlow = new HomePageFlow(pages);
        homePageFlow.addProductToCart(prop.getProperty("productName"));
    }

    private ShoppingCartPage navigateToShoppingCartAndVerifyTitle() {
        ShoppingCartPage shoppingCartPage = pages.getShoppingCartPage().goToShoppingCart();
        String title = shoppingCartPage.getShoppingCartTitle();
        Assert.assertEquals(title,
                "Demo Web Shop. Shopping Cart",
                "Shopping cart page title mismatch");
        return shoppingCartPage;
    }


    @Test(dataProvider = "jsonProvider", dataProviderClass = JsonDataProvider.class, priority = 1,
    groups = {"positive"})
    @JsonData(file = "ShoppingCart.json", key = "successfulCheckout", targetClass = ShoppingCartData.class)
    public void verifyProductAndCheckout(ShoppingCartData shoppingCartData){
        ShoppingCartPage shoppingCartPage = navigateToShoppingCartAndVerifyTitle();
        boolean productInCart = shoppingCartPage.isProductInCart(shoppingCartData.getProductName());
        Assert.assertTrue(productInCart, "Product not found in cart");

        shoppingCartPage.setSelectCountry(shoppingCartData.getCountry())
                .waitForStateToLoad()
                .setSelectState(shoppingCartData.getState())
                .setAddZipCode(shoppingCartData.getZipCode())
                .clickEstimateShippingButton()
                .clickTermsOfServiceCheckbox();

        CheckoutPage checkoutPage = shoppingCartPage.clickCheckOutButton();
    }
}
