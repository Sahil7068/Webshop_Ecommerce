package com.webshop.demo.pages;

import com.webshop.demo.base.BasePage;
import com.webshop.demo.components.HeaderComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RegisterPage extends BasePage {


    // --- Gender radios ---
    @FindBy(id = "gender-male")
    private WebElement genderMaleRadio;

    @FindBy(id = "gender-female")
    private WebElement genderFemaleRadio;

    // --- Personal details ---
    @FindBy(id = "FirstName")
    private WebElement firstNameInput;

    @FindBy(id = "LastName")
    private WebElement lastNameInput;

    @FindBy(id = "Email")
    private WebElement emailInput;

    // --- Passwords ---
    @FindBy(id = "Password")
    private WebElement passwordInput;

    @FindBy(id = "ConfirmPassword")
    private WebElement confirmPasswordInput;

    // --- Buttons / messages ---
    @FindBy(id = "register-button")
    private WebElement registerButton;

    @FindBy(css = ".field-validation-error span[for='Email']")
    private WebElement wrongEmailError;

    @FindBy(css = ".field-validation-error span[for='Password']")
    private WebElement wrongPasswordError;

    @FindBy(css = ".field-validation-error span[for='ConfirmPassword']")
    private WebElement wrongConfirmPasswordError;

    @FindBy(css = ".message-error li")
    private WebElement topErrorMessageContainer;

    @FindBy(css = ".page-title h1")
    private WebElement registerPageTitle;

    @FindBy(xpath = "//div[contains(text(), 'Your registration completed')]")
    private WebElement yourRegistrationCompleted;

    @FindBy(css = ".register-continue-button")
    private WebElement afterRegistrationContinueButton;

    @FindBy(css = "span[for='FirstName']")
    private WebElement firstNameMissingError;

    @FindBy(css = "span[for='LastName']")
    private WebElement lastNameMissingError;

    @FindBy(css = "span[for='Email']")
    private WebElement emailMissingError;

    @FindBy(css = "span[for='Password']")
    private WebElement passwordMissingError;

    @FindBy(css = "span[for='ConfirmPassword']")
    private WebElement confirmPasswordMissingError;

    








    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    public RegisterPage goToRegister() {
        header().goToRegister();
        return this;
    }

    public String getRegisterPageTitle(){
        return getPageTitle();

    }

    public RegisterPage selectGenderMale() {
        click(genderMaleRadio);
        return this;
    }

    public RegisterPage selectGenderFemale() {
        click(genderFemaleRadio);
        return this;
    }

    public RegisterPage enterFirstName(String firstName) {
        type(firstNameInput, firstName);
        return this;
    }

    public RegisterPage enterLastName(String lastName) {
        type(lastNameInput, lastName);
        return this;
    }

    public RegisterPage enterEmail(String email) {
        type(emailInput, email);
        return this;
    }

    public RegisterPage enterPassword(String password) {
        type(passwordInput, password);
        return this;
    }

    public RegisterPage enterConfirmPassword(String confirmPassword) {
        type(confirmPasswordInput, confirmPassword);
        return this;
    }

    public RegisterPage clickRegisterButton() {
        click(registerButton);
        return this;
    }

    public HomePage clickAfterRegistrationContinueButton() {
        click(afterRegistrationContinueButton);
        return new HomePage(driver);
    }

    //Error Messages

    public boolean isFirstNameMissingErrorVisible() {
        return isDisplayed(firstNameMissingError);
    }

    public String getFirstNameMissingErrorText() {
        return getText(firstNameMissingError);
    }

    public boolean isLastNameMissingErrorVisible() {
        return isDisplayed(lastNameMissingError);
    }

    public String getLastNameMissingErrorText() {
        return getText(lastNameMissingError);
    }

    public boolean isEmailMissingErrorVisible() {
        return isDisplayed(emailMissingError);
    }

    public String getEmailMissingErrorText() {
        return getText(emailMissingError);
    }

    public boolean isPasswordMissingErrorVisible() {
        return isDisplayed(passwordMissingError);
    }

    public String getPasswordMissingErrorText() {
        return getText(passwordMissingError);
    }

    public boolean isConfirmPasswordMissingErrorVisible(){
        return isDisplayed(confirmPasswordMissingError);
    }

    public String getConfirmPasswordMissingErrorText(){
        return getText(confirmPasswordMissingError);
    }

    public boolean isWrongEmailErrorVisible() {
        return isDisplayed(wrongEmailError);
    }

    public boolean isWrongPasswordErrorVisible() {
        return isDisplayed(wrongPasswordError);
    }

    public boolean isWrongConfirmPasswordErrorVisible() {
        return isDisplayed(wrongConfirmPasswordError);
    }

    public boolean isRegisterTitleVisibleAfterRegistration(){
        return  isDisplayed(registerPageTitle);
    }

    public String getRegisterTitleText() {
        return getText(registerPageTitle);
    }

    public boolean isRegistrationCompletedVisible() {
        return isDisplayed(yourRegistrationCompleted);
    }

    public String getRegistrationCompletedText() {
        return getText(yourRegistrationCompleted);
    }

    public boolean isAfterRegistrationContinueButtonVisible() {
        return isDisplayed(afterRegistrationContinueButton);
    }





}
