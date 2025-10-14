package com.webshop.demo.components;

import com.webshop.demo.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HeaderComponent extends BasePage {

    @FindBy(css = ".header-logo a img")
    private WebElement logoImage;

    @FindBy(css = "a.ico-register")
    private WebElement registerLink;

    @FindBy(css = "a.ico-login")
    private WebElement loginLink;

    @FindBy(css = "a.ico-logout")
    private WebElement logoutLink;

    @FindBy(xpath = "//input[@value='Search']//..//..//..//a//span[text()='Shopping cart']")
    private WebElement topCartLink; // anchor wrapper for cart

    @FindBy(css = "a.ico-cart .cart-qty")
    private WebElement cartQtySpan;

    @FindBy(css = "a.ico-cart .cart-label")
    private WebElement cartLabelSpan;

    @FindBy(css = "a.ico-wishlist .wishlist-qty")
    private WebElement wishlistQtySpan;

    @FindBy(css = "a.ico-wishlist .cart-label")
    private WebElement wishlistLabelSpan;

    @FindBy(id = "flyout-cart")
    private WebElement flyoutCart;

    @FindBy(id = "small-searchterms")
    private WebElement searchInput;

    @FindBy(css = "input.search-box-button")
    private WebElement searchButton;

    @FindBy(xpath = "//a[@class='ico-logout']//..//..//a[@class='account']")
    private WebElement myAccountLink;


    public HeaderComponent(WebDriver driver) {
        super(driver);
    }


    public void clickLogo() {
        click(logoImage);
    }

    public void goToRegister() {
        click(registerLink);
    }

    public void goToLogin() {
        click(loginLink);
    }

    public void goToLogout() {
        click(logoutLink);
    }

    public boolean isLogoutLinkVisible() {
        return logoutLink.isDisplayed();
    }

    public boolean isMyAccountVisible() {
        return myAccountLink.isDisplayed();
    }

    public String getMyAccountText() {
        return getText(myAccountLink);
    }

    public void goToAccountPage() {
        click(myAccountLink);
    }

    public void openCart() {
        // click the cart anchor to open cart page
        click(topCartLink);
    }

    public void goToWishlist() {
        // click the wishlist anchor
        click(wishlistLabelSpan);
    }

    // --------- flyout cart interactions ----------
    public void hoverOverCart() {
        actions.moveToElement(topCartLink).perform();
    }

    public boolean isFlyoutCartActive() {
        // checks if flyout-cart has 'active' class
        String cls = flyoutCart.getAttribute("class");
        return cls != null && cls.contains("active");
    }

    // --------- counts / labels ----------
    public int getCartQuantity() {
        try {
            String text = cartQtySpan.getText(); // expects something like "(0)"
            return extractDigitsAsInt(text);
        } catch (Exception e) {
            return 0;
        }
    }

    public int getWishlistQuantity() {
        try {
            String text = wishlistQtySpan.getText(); // expects "(0)"
            return extractDigitsAsInt(text);
        } catch (Exception e) {
            return 0;
        }
    }

    // --------- search ----------
    public void enterSearchTerms(String query) {
        clearField(searchInput);
        type(searchInput, query);
    }

    public void submitSearch() {
        click(searchButton);
    }

    public void searchFor(String query) {
        type(searchInput, query);
        submitSearch();
    }







}
