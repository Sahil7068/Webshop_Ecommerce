package com.webshop.demo.flows;

import com.webshop.demo.managers.PageObjectManager;
import com.webshop.demo.pages.HomePage;
import com.webshop.demo.pages.LoginPage;
import com.webshop.demo.pages.RegisterPage;
import com.webshop.demo.pojo.RegisterData;

public class LoginFlow {
    private final PageObjectManager pages;
    private final RegistrationFlow registrationFlow;

    public LoginFlow(PageObjectManager pages) {
        this.pages = pages;
        this.registrationFlow = new RegistrationFlow(pages);
    }

    public HomePage registerAndLogin() {
        // Register a new user
        RegisterData user = registrationFlow.getCurrentUser();
        
        // Perform registration
        RegisterPage registerPage = pages.getRegisterPage().goToRegister();
        registerPage.enterFirstName(user.getFirstName())
                   .enterLastName(user.getLastName())
                   .enterEmail(user.getEmail())
                   .enterPassword(user.getPassword())
                   .enterConfirmPassword(user.getConfirmPassword())
                   .clickRegisterButton();
        
        // Continue after registration
        registerPage.clickAfterRegistrationContinueButton();
        
        // Log out to test login
        pages.getHomePage().goToLogout();
        
        // Now perform login
        return login(user.getEmail(), user.getPassword());
    }

    public HomePage login(String email, String password) {
        LoginPage loginPage = pages.getLoginPage().goToLogin();
        return loginPage.enterEmail(email)
                      .enterPassword(password)
                      .clickLoginButton();
    }
}
