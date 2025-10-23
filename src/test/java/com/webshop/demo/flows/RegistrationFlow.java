package com.webshop.demo.flows;

import com.webshop.demo.annotations.JsonData;
import com.webshop.demo.base.BaseTest;
import com.webshop.demo.managers.PageObjectManager;
import com.webshop.demo.pages.HomePage;
import com.webshop.demo.pages.RegisterPage;
import com.webshop.demo.pojo.RegisterData;
import com.webshop.demo.provider.JsonDataProvider;
import org.testng.annotations.Test;

public class RegistrationFlow {
    private final PageObjectManager pages;
    private RegisterData currentUser;

    public RegistrationFlow(PageObjectManager pages) {
        this.pages = pages;
    }

    public RegisterData getCurrentUser() {
        if (currentUser == null) {
            String timestamp = String.valueOf(System.currentTimeMillis());
            currentUser = new RegisterData(
                "Test",
                "User" + timestamp,
                "testuser" + timestamp + "@example.com",
                "Test@123",
                "Test@123"
            );
        }
        return currentUser;
    }


    @Test
    public HomePage registerNewUser() {
        RegisterData user = getCurrentUser();
        RegisterPage registerPage = pages.getRegisterPage().goToRegister();
        registerPage.enterFirstName(user.getFirstName())
                .enterLastName(user.getLastName())
                .enterEmail(user.getEmail())
                .enterPassword(user.getPassword())
                .enterConfirmPassword(user.getConfirmPassword())
                .clickRegisterButton();

        return registerPage.clickAfterRegistrationContinueButton();

    }

    public HomePage ensureUserIsRegistered() {
        return  registerNewUser();
    }

    public void logout() {
        pages.getHomePage().goToLogout();
    }
}
