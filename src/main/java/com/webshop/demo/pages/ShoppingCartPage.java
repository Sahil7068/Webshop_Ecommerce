package com.webshop.demo.pages;

import com.webshop.demo.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.stream.Collectors;

public class ShoppingCartPage extends BasePage {


    @FindBy(css = ".cart tbody td a.product-name")
    private List<WebElement> cartProductNames;

    @FindBy(css = ".country-input")
    private WebElement selectCountry;

    @FindBy(css = ".state-input")
    private WebElement selectState;

    @FindBy(css = ".zip-input")
    private WebElement addZipCode;

    @FindBy(css = "span[id='estimate-shipping-loading-progress']")
    private WebElement loadingProgress;

    @FindBy(css = ".estimate-shipping-button")
    private WebElement estimateShippingButton;

    @FindBy(css = ".terms-of-service input")
    private WebElement termsOfServiceCheckbox;

    @FindBy(css = ".checkout-button")
    private WebElement checkOutButton;

    public ShoppingCartPage(WebDriver driver) {
        super(driver);
    }

    public ShoppingCartPage goToShoppingCart() {
        header().openCart();
        return this;
    }

    public String getShoppingCartTitle(){
        return getPageTitle();
    }

    public List<String> getCartProductNames(){
        return cartProductNames.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public boolean isProductInCart(String productName) {
        return getCartProductNames().stream()
                .anyMatch(name -> name.equalsIgnoreCase(productName));
    }

    public ShoppingCartPage setSelectCountry(String country){
        selectByVisibleText(selectCountry, country);
        return this;
    }

    public ShoppingCartPage waitForStateToLoad(){
        waitForInvisibility(loadingProgress);
        return this;

    }

    public ShoppingCartPage setSelectState(String state){
        waitForClickability(selectState);
        Select stateSelect = new Select(selectState);

        // Check if there are options available (more than just the default one)
        if (stateSelect.getOptions().size() >= 2) {
            stateSelect.selectByVisibleText(state);
        }
        return this;

    }

    public ShoppingCartPage setAddZipCode(String zipCode){
        addZipCode.sendKeys(zipCode);
        return this;

    }

    public ShoppingCartPage clickEstimateShippingButton(){
        estimateShippingButton.click();
        return this;

    }

    public ShoppingCartPage clickTermsOfServiceCheckbox(){
        termsOfServiceCheckbox.click();
        return this;

    }

    public CheckoutPage clickCheckOutButton(){
        checkOutButton.click();
        return new CheckoutPage(driver);

    }





}
