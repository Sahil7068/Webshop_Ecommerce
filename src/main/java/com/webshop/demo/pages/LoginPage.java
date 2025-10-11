package com.webshop.demo.pages;

import com.webshop.demo.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    @FindBy(css = ".page-title h1")
    private WebElement loginPageTitle;

    @FindBy(id = "Email")
    private WebElement emailField;

    @FindBy(id = "Password")
    private WebElement passwordField;

    @FindBy(id = "RememberMe")
    private WebElement rememberMeCheckbox;

    @FindBy(css = ".forgot-password a")
    private WebElement forgotPasswordLink;

    @FindBy(css = ".login-button")
    private WebElement loginButton;

    @FindBy(css =".validation-summary-errors")
    private WebElement blankEmailValidationError;

    @FindBy(css =".validation-summary-errors")
    private WebElement blankPasswordValidationError;

    @FindBy(css = "span[for='Email']")
    private WebElement emailValidationError;

    @FindBy(css = ".validation-summary-errors")
    private WebElement correctEmailInvalidPasswordValidationError;



    public LoginPage(WebDriver driver) {
        super(driver);
    }


    public LoginPage goToLogin(){
        header().goToLogin();
        return this;
    }

    public String getLoginPageTitle(){
        return getPageTitle();
    }

    public String getLoginPageWelcomeTitle(){
        return getText(loginPageTitle);
    }

    public LoginPage enterEmail(String email){
        type(emailField, email);
        return this;
    }

    public LoginPage enterPassword(String password){
        type(passwordField, password);
        return this;
    }

    public LoginPage clickRememberMeCheckbox(){
        click(rememberMeCheckbox);
        return this;
    }

    public LoginPage clickForgotPasswordLink(){
        click(forgotPasswordLink);
        return this;
    }


    public HomePage clickLoginButton(){
        click(loginButton);
        return new HomePage(driver);
    }

    public void PasswordFieldEmpty(){
        type(passwordField, "");
    }

    //Error Validations

    public boolean isBlankEmailValidationErrorVisible(){
        return isDisplayed(blankEmailValidationError);
    }

    public String getBlankEmailValidationError(){
        return getText(blankEmailValidationError);
    }

    public boolean isBlankPasswordValidationErrorVisible(){
        return isDisplayed(blankPasswordValidationError);
    }

    public String getBlankPasswordValidationError(){
        return getText(blankPasswordValidationError);
    }

    public boolean isEmailValidationErrorVisible(){
        return isDisplayed(emailValidationError);
    }

    public String getEmailValidationError(){
        return getText(emailValidationError);
    }

    public boolean isCorrectEmailInvalidPasswordValidationErrorVisible(){
        return isDisplayed(correctEmailInvalidPasswordValidationError);
    }

    public String getCorrectEmailInvalidPasswordValidationError(){
        return getText(correctEmailInvalidPasswordValidationError);
    }

    public boolean isPasswordFieldEmpty(){
        return isDisplayed(passwordField);
    }








}
