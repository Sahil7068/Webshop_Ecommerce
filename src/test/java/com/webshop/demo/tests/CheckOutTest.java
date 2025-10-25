package com.webshop.demo.tests;

import com.webshop.demo.annotations.JsonData;
import com.webshop.demo.base.BaseTest;
import com.webshop.demo.flows.HomePageFlow;
import com.webshop.demo.flows.LoginFlow;
import com.webshop.demo.flows.ShoppingCartFlow;
import com.webshop.demo.pages.CheckoutPage;
import com.webshop.demo.pages.HomePage;
import com.webshop.demo.pages.ShoppingCartPage;
import com.webshop.demo.pojo.CheckoutData;
import com.webshop.demo.pojo.ShoppingCartData;
import com.webshop.demo.provider.JsonDataProvider;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Properties;

public class CheckOutTest extends BaseTest {
    CheckoutPage checkoutPage;

    @BeforeMethod
    public void setUp() throws IOException {
        Properties prop = loadProperties();
        LoginFlow loginFlow = new LoginFlow(pages);
        HomePage homePage = loginFlow.registerAndLogin();
        HomePageFlow homePageFlow = new HomePageFlow(pages);
        homePageFlow.addProductToCart(prop.getProperty("productName"));
        ShoppingCartFlow shoppingCartFlow = new ShoppingCartFlow(pages);
        checkoutPage = shoppingCartFlow.goToShoppingCart(prop.getProperty("country"), prop.getProperty("state"), prop.getProperty("zipCode"));
    }

    private CheckoutPage navigateToCheckoutPageAndVerifyTitle() {
        CheckoutPage checkoutPage = pages.getCheckoutPage();
        String title = checkoutPage.getCheckoutPageTitle();
        Assert.assertEquals(title,
                "Demo Web Shop. Checkout",
                "Checkout cart page title mismatch");
        return checkoutPage;
    }

    @Test(dataProvider = "jsonProvider", dataProviderClass = JsonDataProvider.class, priority = 1
    ,groups = {"positive"})
    @JsonData(file = "CheckoutPage.json", key = "CODCheckout", targetClass = CheckoutData.class)
    public void verifyProductAndCheckoutUsingCOD(CheckoutData checkoutData){
        checkoutPage = navigateToCheckoutPageAndVerifyTitle();

        boolean billingHeader = checkoutPage.isBillingHeaderPresent(checkoutData.getBillingHeader());
        Assert.assertTrue(billingHeader, "Billing header is not present");

        checkoutPage.enterCompanyName(checkoutData.getCompanyName());
        checkoutPage.selectCountry(checkoutData.getCountry());
        checkoutPage.waitAfterSelectCountry();
        checkoutPage.selectState(checkoutData.getState());
        checkoutPage.enterCity(checkoutData.getCity());
        checkoutPage.enterAddress(checkoutData.getAddress1());
        checkoutPage.enterZipPostalCode(checkoutData.getZipPostalCode());
        checkoutPage.enterPhoneNumber(checkoutData.getPhoneNumber());

        checkoutPage.clickBillingContinue();
        checkoutPage.waitAfterBillingContinue();
        boolean shippingAddressHeader = checkoutPage.isShippingAddressHeaderPresent(checkoutData.getShippingAddressHeader());
        Assert.assertTrue(shippingAddressHeader, "Shipping address header is not present");

        checkoutPage.togglePickUpInStore();
        checkoutPage.clickShippingContinue();
        checkoutPage.waitAfterShippingContinue();

        boolean paymentMethodHeader = checkoutPage.isPaymentHeaderPresent(checkoutData.getPaymentMethodHeader());
        Assert.assertTrue(paymentMethodHeader, "Payment method header is not present");

        checkoutPage.clickPaymentMethod(checkoutData.getPaymentMethod());
        checkoutPage.clickPaymentContinue();
        checkoutPage.waitAfterPaymentMethodContinue();

        boolean paymentInformationHeader = checkoutPage.isPaymentInformationHeaderPresent(checkoutData.getPaymentInformationHeader());
        Assert.assertTrue(paymentInformationHeader, "Payment information header is not present");

        String paymentInfoText = checkoutPage.getPaymentInfoCOD(checkoutData.getcodInformationText());
        Assert.assertEquals(paymentInfoText, checkoutData.getcodInformationText(), "Payment information text mismatch");

        checkoutPage.clickPaymentInfoContinue();
        checkoutPage.waitAfterPaymentInfoContinue();

        boolean confirmOrderHeader = checkoutPage.isConfirmOrderHeaderPresent(checkoutData.getConfirmOrderHeader());
        Assert.assertTrue(confirmOrderHeader, "Confirm order header is not present");

        checkoutPage.clickConfirmOrder();
        checkoutPage.waitAfterConfirmOrder();

        String confirmOrderText = checkoutPage.getOrderSuccessText();
        Assert.assertEquals(confirmOrderText, checkoutData.getConfirmOrderText(), "Confirm order text mismatch");

    }
}
