package com.webshop.demo.tests;

import com.webshop.demo.annotations.JsonData;
import com.webshop.demo.base.BaseTest;
import com.webshop.demo.pages.HomePage;
import com.webshop.demo.pages.RegisterPage;
import com.webshop.demo.pojo.RegisterData;
import com.webshop.demo.provider.JsonDataProvider;
import com.webshop.demo.utils.TestDataHolder;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RegisterTest extends BaseTest {

    private RegisterPage navigateToRegisterAndVerifyTitle() {
        RegisterPage registerPage = pages.getRegisterPage().goToRegister();
        String title = registerPage.getRegisterPageTitle();
        Assert.assertEquals(title,
                "Demo Web Shop. Register",
                "Register page title mismatch");
        return registerPage;
    }


    @Test(dataProvider = "jsonProvider", dataProviderClass = JsonDataProvider.class, priority = 1)
    @JsonData(file = "Register.json", key = "successful", targetClass = RegisterData.class)
    public void testSuccessfulRegistration(RegisterData registerData) {
        // Store registration data for use in other tests
        TestDataHolder.setUserEmail(registerData.getEmail());
        TestDataHolder.setUserPassword(registerData.getPassword());
        TestDataHolder.setFirstName(registerData.getFirstName());
        TestDataHolder.setLastName(registerData.getLastName());

        RegisterPage registerPage = navigateToRegisterAndVerifyTitle();

        registerPage
                .selectGenderMale()
                .enterFirstName(registerData.getFirstName())
                .enterLastName(registerData.getLastName())
                .enterEmail(registerData.getEmail())
                .enterPassword(registerData.getPassword())
                .enterConfirmPassword(registerData.getConfirmPassword())
                .clickRegisterButton();

        Assert.assertTrue(pages.getRegisterPage().isRegisterTitleVisibleAfterRegistration(),
                "Register title should be visible after registration");

        Assert.assertEquals(pages.getRegisterPage().getRegisterTitleText(), "Register",
                "Register page title text should match 'Register'");

        Assert.assertTrue(pages.getRegisterPage().isRegistrationCompletedVisible(),
                "Your registration completed should be visible after registration");

        Assert.assertEquals(pages.getRegisterPage().getRegistrationCompletedText(),
                "Your registration completed",
                "Your registration completed text should match 'Your registration completed'");

        HomePage homePage = pages.getRegisterPage().clickAfterRegistrationContinueButton();
        String homePageTitle = homePage.getHomePageTitle();
        Assert.assertEquals(homePageTitle, "Demo Web Shop",
                "Home Page Title doesn't match");
    }



    @Test(dataProvider = "jsonProvider", dataProviderClass = JsonDataProvider.class, priority = 2)
    @JsonData(file = "Register.json", key = "firstNameIsMissing", targetClass = RegisterData.class)
    public void verifyRegistrationFails_WhenFirstNameIsMissing(RegisterData registerData) {

        RegisterPage registerPage = navigateToRegisterAndVerifyTitle();


        registerPage
                .selectGenderFemale()
                .enterLastName(registerData.getLastName())
                .enterEmail(registerData.getEmail())
                .enterPassword(registerData.getPassword())
                .enterConfirmPassword(registerData.getConfirmPassword())
                .clickRegisterButton();

        Assert.assertTrue(pages.getRegisterPage().isFirstNameMissingErrorVisible(),
                "First name missing error should be visible");

        Assert.assertEquals(pages.getRegisterPage().getFirstNameMissingErrorText(),
                "First name is required.",
                "First name missing error text should match 'First name is required.'");

    }

    @Test(dataProvider = "jsonProvider", dataProviderClass = JsonDataProvider.class, priority = 3)
    @JsonData(file = "Register.json", key = "lastNameIsMissing", targetClass = RegisterData.class)
    public void verifyRegistrationFails_WhenLastNameIsMissing(RegisterData registerData) {

        RegisterPage registerPage = navigateToRegisterAndVerifyTitle();


        registerPage
                .selectGenderFemale()
                .enterFirstName(registerData.getFirstName())
                .enterEmail(registerData.getEmail())
                .enterPassword(registerData.getPassword())
                .enterConfirmPassword(registerData.getConfirmPassword())
                .clickRegisterButton();

        Assert.assertTrue(pages.getRegisterPage().isLastNameMissingErrorVisible(),
                "Last name missing error should be visible");

        Assert.assertEquals(pages.getRegisterPage().getLastNameMissingErrorText(),
                "Last name is required.",
                "Last name missing error text should match 'Last name is required.'");

    }

    @Test(dataProvider = "jsonProvider", dataProviderClass = JsonDataProvider.class, priority = 4)
    @JsonData(file = "Register.json", key = "emailIsMissing", targetClass = RegisterData.class)
    public void verifyRegistrationFails_WhenEmailIsMissing(RegisterData registerData) {

        RegisterPage registerPage = navigateToRegisterAndVerifyTitle();


        registerPage
                .selectGenderFemale()
                .enterFirstName(registerData.getFirstName())
                .enterLastName(registerData.getLastName())
                .enterPassword(registerData.getPassword())
                .enterConfirmPassword(registerData.getConfirmPassword())
                .clickRegisterButton();

        Assert.assertTrue(pages.getRegisterPage(). isEmailMissingErrorVisible(),
                "Email missing error should be visible");

        Assert.assertEquals(pages.getRegisterPage(). getEmailMissingErrorText(),
                "Email is required.",
                "Email missing error text should match 'Email is required.'");

    }

    @Test(dataProvider = "jsonProvider", dataProviderClass = JsonDataProvider.class, priority = 5)
    @JsonData(file = "Register.json", key = "passwordIsMissing", targetClass = RegisterData.class)
    public void verifyRegistrationFails_WhenPasswordIsMissing(RegisterData registerData) {

        RegisterPage registerPage = navigateToRegisterAndVerifyTitle();


        registerPage
                .selectGenderFemale()
                .enterFirstName(registerData.getFirstName())
                .enterLastName(registerData.getLastName())
                .enterEmail(registerData.getEmail())
                .clickRegisterButton();

        Assert.assertTrue(pages.getRegisterPage(). isPasswordMissingErrorVisible(),
                "Password missing error should be visible");

        Assert.assertEquals(pages.getRegisterPage(). getPasswordMissingErrorText(),
                "Password is required.",
                "Password missing error text should match 'Password is required.'");

    }

    @Test(dataProvider = "jsonProvider", dataProviderClass = JsonDataProvider.class, priority = 6)
    @JsonData(file = "Register.json", key = "passwordTooShort", targetClass = RegisterData.class)
    public void verifyRegistrationFails_WhenPasswordTooShort(RegisterData registerData) {

        RegisterPage registerPage = navigateToRegisterAndVerifyTitle();


        registerPage
                .selectGenderFemale()
                .enterFirstName(registerData.getFirstName())
                .enterLastName(registerData.getLastName())
                .enterEmail(registerData.getEmail())
                .enterPassword(registerData.getPassword())
                .clickRegisterButton();

        Assert.assertTrue(pages.getRegisterPage(). isPasswordMissingErrorVisible(),
                "The password should have at least 6 characters. error should be visible");

        Assert.assertEquals(pages.getRegisterPage(). getPasswordMissingErrorText(),
                "The password should have at least 6 characters.",
                "Password error text should match 'The password should have at least 6 characters.'");

    }

    @Test(dataProvider = "jsonProvider", dataProviderClass = JsonDataProvider.class, priority = 7)
    @JsonData(file = "Register.json", key = "confirmPasswordIsMissing", targetClass = RegisterData.class)
    public void verifyRegistrationFails_WhenConfirmPasswordIsMissing(RegisterData registerData) {

        RegisterPage registerPage = navigateToRegisterAndVerifyTitle();


        registerPage
                .selectGenderFemale()
                .enterFirstName(registerData.getFirstName())
                .enterLastName(registerData.getLastName())
                .enterEmail(registerData.getEmail())
                .enterPassword(registerData.getPassword())
                .clickRegisterButton();

        Assert.assertTrue(pages.getRegisterPage(). isConfirmPasswordMissingErrorVisible(),
                "Confirm Password missing error should be visible");

        Assert.assertEquals(pages.getRegisterPage(). getConfirmPasswordMissingErrorText(),
                "Password is required.",
                "Confirm Password missing error text should match 'Password is required.'");

    }

    @Test(dataProvider = "jsonProvider", dataProviderClass = JsonDataProvider.class, priority = 8)
    @JsonData(file = "Register.json", key = "confirmPasswordNotMatching", targetClass = RegisterData.class)
    public void verifyRegistrationFails_WhenConfirmPasswordNotMatching(RegisterData registerData) {

        RegisterPage registerPage = navigateToRegisterAndVerifyTitle();


        registerPage
                .selectGenderFemale()
                .enterFirstName(registerData.getFirstName())
                .enterLastName(registerData.getLastName())
                .enterEmail(registerData.getEmail())
                .enterPassword(registerData.getPassword())
                .enterConfirmPassword(registerData.getConfirmPassword())
                .clickRegisterButton();

        Assert.assertTrue(pages.getRegisterPage(). isConfirmPasswordMissingErrorVisible(),
                "Confirm Password not matching error should be visible");

        Assert.assertEquals(pages.getRegisterPage(). getConfirmPasswordMissingErrorText(),
                "The password and confirmation password do not match.",
                "Error message mismatch: expected 'The password and confirmation password do not match.");

    }




}

