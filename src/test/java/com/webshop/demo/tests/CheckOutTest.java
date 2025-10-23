package com.webshop.demo.tests;

import com.webshop.demo.base.BaseTest;
import com.webshop.demo.flows.HomePageFlow;
import com.webshop.demo.flows.LoginFlow;
import com.webshop.demo.pages.CheckoutPage;
import com.webshop.demo.pages.HomePage;
import com.webshop.demo.pages.ShoppingCartPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

public class CheckOutTest extends BaseTest {

    @BeforeMethod
    public void setUp(){
        LoginFlow loginFlow = new LoginFlow(pages);
        HomePage homePage = loginFlow.registerAndLogin();
        HomePageFlow homePageFlow = new HomePageFlow(pages);
        homePageFlow.addProductToCart("14.1-inch Laptop");
    }

    private CheckoutPage navigateToCheckoutPageAndVerifyTitle() {
        CheckoutPage checkoutPage = pages.getCheckoutPage();
        String title = checkoutPage.getCheckoutPageTitle();
        Assert.assertEquals(title,
                "Demo Web Shop. Checkout",
                "Checkout cart page title mismatch");
        return checkoutPage;
    }
}
