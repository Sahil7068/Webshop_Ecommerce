package com.webshop.demo.tests;

import com.webshop.demo.annotations.JsonData;
import com.webshop.demo.base.BaseTest;
import com.webshop.demo.pages.HomePage;
import com.webshop.demo.pages.LoginPage;
import com.webshop.demo.pages.RegisterPage;
import com.webshop.demo.pojo.LoginData;
import com.webshop.demo.pojo.RegisterData;
import com.webshop.demo.provider.JsonDataProvider;
import com.webshop.demo.utils.TestDataHolder;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class for login functionality
 * This test depends on RegisterTest.testSuccessfulRegistration
 */
public class LoginTest extends BaseTest {

    private LoginPage navigateToLoginPageAndVerifyTitle() {
        LoginPage loginPage = pages.getLoginPage().goToLogin();
        String title = loginPage.getLoginPageTitle();
        Assert.assertEquals(title,
                "Demo Web Shop. Login",
                "Login page title mismatch");
        return loginPage;
    }
    
    @Test(priority = 1, groups = {"regression", "login", "registeredEmail"}, dependsOnMethods = "testSuccessfulRegistration") // Higher priority to run after registration
    public void testSuccessfulLoginWithRegisteredCredentials() {
        // Get stored credentials from registration test
        String email = TestDataHolder.getUserEmail();
        String password = TestDataHolder.getUserPassword();

        
        // Navigate to login page
        LoginPage loginPage = navigateToLoginPageAndVerifyTitle();
        
        // Perform login
        HomePage homePage = loginPage
                .enterEmail(email)
                .enterPassword(password)
                .clickLoginButton();

        String homePageTitle = homePage.getHomePageTitle();
        Assert.assertEquals(homePageTitle, "Demo Web Shop",
                "Home Page Title doesn't match");

        Assert.assertTrue(homePage.isAccountEmailVisible(),
                "Account email should be visible after login");

        String accountEmailVerification = homePage.getAccountEmail();
        Assert.assertEquals(accountEmailVerification, email,
                "Account email doesn't match");


        
        // Verify user is logged in by checking for logout link
        Assert.assertTrue(homePage.isLogoutLinkVisible(), 
                "Logout link should be visible after login");
        
        // Logout for clean state
        homePage.goToLogout();

    }

    @Test(dataProvider = "jsonProvider", dataProviderClass = JsonDataProvider.class,
            priority = 2, groups = {"regression", "negative", "login", "registeredEmail"}, dependsOnMethods = "testSuccessfulRegistration")
    @JsonData(file = "Login.json", key = "correctEmailInvalidPassword", targetClass = LoginData.class)
    public void testWithRegisteredEmailAndIncorrectPassword(LoginData loginData) {
        String email = TestDataHolder.getUserEmail();

        LoginPage loginPage = navigateToLoginPageAndVerifyTitle();
        loginPage.enterEmail(email);
        loginPage.enterPassword(loginData.getPassword());
        loginPage.clickLoginButton();

        Assert.assertEquals(loginPage.isCorrectEmailInvalidPasswordValidationErrorVisible(),
                true,
                "Error validatiion for correct email and invalid password is not visible");

        String errorValidationMessage = loginPage.getCorrectEmailInvalidPasswordValidationError();
        Assert.assertEquals(errorValidationMessage,
                "Login was unsuccessful. Please correct the errors and try again.\n" +
                        "The credentials provided are incorrect",
                "Error validation message doesn't match");

        Assert.assertTrue(loginPage.isPasswordFieldEmpty(),
                "Password field should be empty");

    }






    @Test(dataProvider = "jsonProvider", dataProviderClass = JsonDataProvider.class, priority = 3,
            groups = {"negative", "login"})
    @JsonData(file = "Login.json", key = "blankEmail", targetClass = LoginData.class)
    public void testLoginWithBlankEmail(LoginData loginData) {

        LoginPage loginPage = navigateToLoginPageAndVerifyTitle();
        loginPage.enterPassword(loginData.getPassword());
        loginPage.clickLoginButton();

        Assert.assertTrue(loginPage.isBlankEmailValidationErrorVisible(),
                "Error validatiion for blank email is not visible");

        String errorValidationMessage = loginPage.getBlankEmailValidationError();
        Assert.assertEquals(errorValidationMessage,
                "Login was unsuccessful. Please correct the errors and try again.\n" +
                        "No customer account found",
                "Error validation message doesn't match");

    }

    @Test(dataProvider = "jsonProvider", dataProviderClass = JsonDataProvider.class,
            priority = 4, groups = {"negative", "login"})
    @JsonData(file = "Login.json", key = "blankPassword", targetClass = LoginData.class)
    public void testLoginWithBlankPassword(LoginData loginData) {



        LoginPage loginPage = navigateToLoginPageAndVerifyTitle();
        loginPage.enterEmail(loginData.getEmail());
        loginPage.clickLoginButton();

        Assert.assertTrue(loginPage.isBlankPasswordValidationErrorVisible(),
                "Error validatiion for blank password is not visible");

        String errorValidationMessage = loginPage.getBlankPasswordValidationError();
        Assert.assertEquals(errorValidationMessage,
                "Login was unsuccessful. Please correct the errors and try again.\n" +
                        "No customer account found",
                "Error validation message doesn't match");
    }

    @Test(dataProvider = "jsonProvider", dataProviderClass = JsonDataProvider.class,
            priority = 5, groups = {"negative", "login"})
    @JsonData(file = "Login.json", key = "invalidEmails", targetClass = LoginData.class)
    public void testLoginWithInvalidEmails(LoginData loginData) {

        LoginPage loginPage = navigateToLoginPageAndVerifyTitle();

        loginPage.enterEmail(loginData.getEmail());
        loginPage.clickLoginButton();

        Assert.assertTrue(loginPage.isEmailValidationErrorVisible(),
                "Error validatiion for invalid email is not visible");

        String errorValidationMessage = loginPage.getEmailValidationError();
        Assert.assertEquals(errorValidationMessage,
                "Please enter a valid email address.",
                "Email Error validation message doesn't match");

    }


}
    


