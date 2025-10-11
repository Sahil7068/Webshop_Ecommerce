package com.webshop.demo.pages;

import com.webshop.demo.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class HomePage extends BasePage {

    @FindBy(css = ".header-links .account")
    private WebElement accountEmail;

    @FindBy(css = ".product-item")
    private List<WebElement> products;

    @FindBy(css = ".ajax-loading-block-window .loading-image")
    private WebElement loadingImage;

    @FindBy(css = ".content")
    private WebElement productAddedToast;

    @FindBy(css = ".product-box-add-to-cart-button")
    private WebElement addToCartElement;

    @FindBy(css = ".ui-menu-item")
    private List<WebElement> searchList;


    By productList = By.cssSelector(".product-item");
    By addToCart = By.cssSelector(".product-box-add-to-cart-button");
    By searchDropdown = By.cssSelector(".ui-autocomplete");

    public HomePage(WebDriver driver) {
        super(driver);
    }


    public String getHomePageTitle() {
        return getPageTitle();

    }

    public HomePage goToLogout() {
        header().goToLogout();
        return this;
    }

    public boolean isLogoutLinkVisible() {
        try {
            return header().isLogoutLinkVisible();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isAccountEmailVisible() {
        return accountEmail.isDisplayed();
    }

    public String getAccountEmail() {
        return getText(accountEmail);
    }

    public List<WebElement> getProductList() {
        waitForElementToApperar(productList);
        return products;
    }

    public WebElement getProductNames(String productName) {
        waitForElementToApperar(productList);


        return getProductList().stream().filter(s -> s.findElement(By.cssSelector("h2.product-title a"))
                        .getText().trim().equalsIgnoreCase(productName)).
                findFirst().orElse(null);
    }

    public void addProductToCart(String productName) {
        WebElement product = getProductNames(productName);
        waitForClickability(addToCartElement);

        product.findElement(addToCart).click();

    }

    public void waitForLoadingImageToDisappear() {
        waitForInvisibility(loadingImage);
    }

    public boolean isProductAddedToastVisible() {
        return productAddedToast.isDisplayed();
    }

    public String getProductAddedToastText() {
        return getText(productAddedToast);
    }

    public boolean isMyAccountVisible() {
        return header().isMyAccountVisible();
    }

    public String getMyAccountText() {
        return header().getMyAccountText();

    }

    public AccountPage goToMyAccount() {
        header().goToAccountPage();
        return new AccountPage(driver);
    }

    public ShoppingCartPage goToShoppingCart() {
        header().openCart();
        return new ShoppingCartPage(driver);
    }

    public WishlistPage goToWishlist() {
        header().goToWishlist();
        return new WishlistPage(driver);
    }



    public void enterSearchText(String searchText) {
        header().enterSearchTerms(searchText);
    }

    public List<WebElement> getSearchList() {
        waitForElementToApperar(searchDropdown);
        return searchList;
    }

    public void selectSearchItem(String searchText) {

        WebElement clickOnList = getSearchList().stream().filter(s -> s.getText().trim().
                equalsIgnoreCase(searchText)).findFirst().orElse(null);


        if (clickOnList == null) {
            throw new NoSuchElementException("Search item not found: " + searchText);
        }
        clickOnList.click();
    }

    public String getSearchItemPageTitle(){
        return getPageTitle();
    }

    public String getSearchItemPageUrl(){
        return getCurrentUrl();
    }

    public void clickSearchButtonWithNoSearchText(){
        header().submitSearch();
    }

    public String getSearchButtonAlertText(){
        return getAlertText();
    }

    public void acceptSearchAlert(){
        acceptAlert();
    }


}
