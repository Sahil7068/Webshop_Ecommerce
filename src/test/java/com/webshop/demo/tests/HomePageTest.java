package com.webshop.demo.tests;

import com.webshop.demo.annotations.JsonData;
import com.webshop.demo.base.BaseTest;
import com.webshop.demo.flows.RegistrationFlow;
import com.webshop.demo.pages.HomePage;
import com.webshop.demo.pages.LoginPage;
import com.webshop.demo.pages.RegisterPage;
import com.webshop.demo.pojo.HomePageData;
import com.webshop.demo.pojo.RegisterData;
import com.webshop.demo.provider.JsonDataProvider;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HomePageTest extends BaseTest {



    @BeforeMethod
    public void setUp(){
        RegistrationFlow registrationFlow = new RegistrationFlow(pages);
        HomePage homePage = registrationFlow.ensureUserIsLoggedIn();

    }

    private HomePage navigateToHomePageAndVerifyTitle() {
        HomePage homePage = pages.getHomePage();
        homePage.getHomePageTitle();
        String title =   homePage.getHomePageTitle();
        Assert.assertEquals(title,
                "Demo Web Shop",
                "Home page title mismatch");
        return homePage;
    }






    @Test(dataProvider = "jsonProvider", dataProviderClass = JsonDataProvider.class, priority = 1,
    enabled = false)
    @JsonData(file = "HomePage.json", key = "addProductToCart", targetClass = HomePageData.class)
    public void testAddProductToCart(HomePageData homePageData) {
        HomePage homePage =navigateToHomePageAndVerifyTitle();
        homePage.addProductToCart(homePageData.getProductName());
        homePage.waitForLoadingImageToDisappear();

        Assert.assertTrue(homePage.isProductAddedToastVisible());
        Assert.assertEquals(homePage.getProductAddedToastText(),
                "The product has been added to your shopping cart");
    }

    @Test(dataProvider = "jsonProvider", dataProviderClass = JsonDataProvider.class, priority = 2,
            enabled = false)
    @JsonData(file = "HomePage.json", key = "searchItem", targetClass = HomePageData.class)
    public void testSearchFunctionality(HomePageData homePageData) {
        HomePage homePage =navigateToHomePageAndVerifyTitle();

        homePage.enterSearchText(homePageData.getTypedProductName());
        homePage.selectSearchItem(homePageData.getSelectedItem());

        String searchItemPageTitle = homePage.getSearchItemPageTitle();
        Assert.assertEquals(searchItemPageTitle,
                "Demo Web Shop. Simple Computer",
                "Search item page title mismatch");

        String searchItemPageUrl = homePage.getSearchItemPageUrl();
        Assert.assertEquals(searchItemPageUrl,
                "https://demowebshop.tricentis.com/simple-computer",
                "Search item page url mismatch");


    }

    @Test(priority = 3, groups = {"negative"})
    public void testSearchButtonWithNoSearchText() {
        HomePage homePage = navigateToHomePageAndVerifyTitle();
        homePage.clickSearchButtonWithNoSearchText();
        String alertText = homePage.getSearchButtonAlertText();
        homePage.acceptSearchAlert();
        Assert.assertEquals(alertText,
                "Please enter some search keyword");
    }






}
