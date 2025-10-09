package com.webshop.demo.pages;

import com.webshop.demo.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    @FindBy(css = ".page-title h1")
    private WebElement loginPageTitle;

    @FindBy(id = "email")
    private WebElement emailField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "RememberMe")
    private WebElement rememberMeCheckbox;

    @FindBy(css = ".forgot-password a")
    private WebElement forgotPasswordLink;

    @FindBy(css = ".login-button")
    private WebElement loginButton;

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








}
