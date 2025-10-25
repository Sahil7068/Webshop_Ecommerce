package com.webshop.demo.pages;

import com.webshop.demo.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CheckoutPage extends BasePage {

    @FindBy(css = ".step-title h2")
    private List<WebElement> checkoutHeaders;

    @FindBy(css = "input[id=BillingNewAddress_Company]")
    private WebElement companyNameInput;

    @FindBy(css = "select[name='BillingNewAddress.CountryId']")
    private WebElement selectCountry;

    @FindBy(css = "span[id='states-loading-progress']")
    private WebElement waitAfterSelectCountry;

    @FindBy(css = "select[id='BillingNewAddress_StateProvinceId']")
    private WebElement selectState;

    @FindBy(css = "input[id='BillingNewAddress_City']")
    private WebElement cityInput;

    @FindBy(css = "input[id='BillingNewAddress_Address1']")
    private WebElement addressInput;

    @FindBy(css = "input[id='BillingNewAddress_ZipPostalCode']")
    private WebElement zipPostalCodeInput;

    @FindBy(css = "input[id='BillingNewAddress_PhoneNumber']")
    private WebElement phoneNumberInput;

    @FindBy(css = "select[id='billing-address-select']")
    private WebElement selectBillingAddress;

    @FindBy(css = "input[onclick='Billing.save()']")
    private WebElement billingContinue;

    @FindBy(xpath = "//input[@onclick='Billing.save()']/following-sibling::span")
    private WebElement waitAfterBillingContinue;

    @FindBy(css = "input[onclick='Shipping.togglePickUpInStore(this)']")
    private WebElement shippingTogglePickUpInStore;

    @FindBy(xpath = "//input[@onclick='Shipping.save()']/..//a")
    private WebElement shippingBack;

    @FindBy(css = "input[onclick='Shipping.save()']")
    private WebElement shippingContinue;

    @FindBy(xpath = "//input[@onclick='Shipping.save()']/following-sibling::span")
    private WebElement waitAfterShippingContinue;

    @FindBy(xpath = "//input[@onclick='PaymentMethod.save()']/..//a")
    private WebElement paymentBack;

    @FindBy(xpath = "//input[@onclick='PaymentMethod.save()']")
    private WebElement paymentMethodContinue;

    @FindBy(xpath = "//input[@onclick='PaymentMethod.save()']/following-sibling::span")
    private WebElement waitAfterPaymentMethodContinue;

    @FindBy(xpath = "//p[contains(normalize-space(), 'You will pay by COD')]")
    private WebElement paymentMethodCODText;

    @FindBy(xpath = "//input[@onclick='PaymentInfo.save()']/..//a")
    private WebElement paymentInfoBack;

    @FindBy(xpath = "//input[@onclick='PaymentInfo.save()']")
    private WebElement paymentInfoContinue;

    @FindBy(xpath = "//tbody/tr/td/p")
    private WebElement getCODPaymentText;

    @FindBy(xpath = "//input[@onclick='PaymentInfo.save()']/following-sibling::span")
    private WebElement waitAfterPaymentInfoContinue;

    @FindBy(css = "select.dropdownlists")
    private WebElement selectCreditCard;

    @FindBy(xpath = "//input[@name='CardholderName']")
    private WebElement getCardHolderName;

    @FindBy(xpath = "//input[@name='CardNumber']")
    private WebElement getCardNumber;

    @FindBy(xpath = "//select[@id='ExpireMonth']")
    private WebElement getExpireMonth;

    @FindBy(xpath = "//select[@id='ExpireYear']")
    private WebElement getExpireYear;

    @FindBy(xpath = "//input[@id='CardCode']")
    private WebElement getCardCode;



    @FindBy(css = "input[onclick='ConfirmOrder.save()']")
    private WebElement confirmOrder;

    @FindBy(xpath = "//input[@onclick='ConfirmOrder.save()']/following-sibling::span")
    private WebElement waitAfterConfirmOrder;

    @FindBy(xpath = "//strong[contains(normalize-space(), 'Your order has been successfully processed!')]")
    private WebElement orderSuccessText;

    @FindBy(xpath = "//input[@value='Continue']")
    private WebElement orderSuccessContinue;

    private By paymentMethod(String methodName) {
        return By.xpath("//label[contains(normalize-space(), '" + methodName + "')]");
    }



    public CheckoutPage(WebDriver driver) {
        super(driver);
    }


    public String getCheckoutPageTitle(){
        return  getPageTitle();
    }

    public boolean isBillingHeaderPresent(String billingHeaderText) {
        return checkoutHeaders.stream()
                .map(WebElement::getText)
                .anyMatch(headerText -> headerText.trim().equalsIgnoreCase(billingHeaderText));
    }

    public CheckoutPage enterCompanyName(String companyName){
        type(companyNameInput, companyName);
        return this;
    }

    public CheckoutPage selectCountry(String country){
        selectByVisibleText(selectCountry, country);
        return this;
    }

    public CheckoutPage waitAfterSelectCountry(){
        waitForInvisibility(waitAfterSelectCountry);
        return this;
    }

    public CheckoutPage selectState(String state){
        selectByVisibleText(selectState, state);
        return this;
    }

    public CheckoutPage enterCity(String city){
        type(cityInput, city);
        return this;
    }

    public CheckoutPage enterAddress(String address){
        type(addressInput, address);
        return this;
    }

    public CheckoutPage enterZipPostalCode(String zipPostalCode){
        type(zipPostalCodeInput, zipPostalCode);
        return this;
    }

    public CheckoutPage enterPhoneNumber(String phoneNumber){
        type(phoneNumberInput, phoneNumber);
        return this;
    }

    public CheckoutPage clickBillingContinue(){
        click(billingContinue);
        return this;
    }

    public CheckoutPage waitAfterBillingContinue(){
        waitForInvisibility(waitAfterBillingContinue);
        return this;
    }

    public boolean isShippingAddressHeaderPresent(String shippingAddressHeaderText) {
        return checkoutHeaders.stream()
                .map(WebElement::getText)
                .anyMatch(headerText -> headerText.trim().equalsIgnoreCase(shippingAddressHeaderText));
    }

    public CheckoutPage togglePickUpInStore(){
        waitForVisibility(shippingTogglePickUpInStore);
        shippingTogglePickUpInStore.click();
        return this;
    }

    public CheckoutPage clickBackShipping(){
        click(shippingBack);
        return this;
    }

    public CheckoutPage clickShippingContinue(){
        click(shippingContinue);
        return this;
    }

    public CheckoutPage waitAfterShippingContinue(){
        waitForInvisibility(waitAfterShippingContinue);
        return this;
    }

    public boolean isPaymentHeaderPresent(String paymentHeaderText) {
        return checkoutHeaders.stream()
                .map(WebElement::getText)
                .anyMatch(headerText -> headerText.trim().equalsIgnoreCase(paymentHeaderText));
    }

    public CheckoutPage clickPaymentMethod(String methodName){

        click(paymentMethod(methodName));
        return this;
    }

    public CheckoutPage clickPaymentContinue(){
        click(paymentMethodContinue);
        return this;
    }

    public CheckoutPage waitAfterPaymentMethodContinue(){
        waitForInvisibility(waitAfterPaymentMethodContinue);
        return this;
    }

    public CheckoutPage clickBackPayment(){
        click(paymentBack);
        return this;
    }

    public CheckoutPage clickPaymentInfoContinue(){
        click(paymentInfoContinue);
        return this;
    }

    public CheckoutPage waitAfterPaymentInfoContinue(){
        waitForInvisibility(waitAfterPaymentInfoContinue);
        return this;
    }

    public boolean isPaymentInformationHeaderPresent(String paymentInformationHeaderText) {
        return checkoutHeaders.stream()
                .map(WebElement::getText)
                .anyMatch(headerText -> headerText.trim().equalsIgnoreCase(paymentInformationHeaderText));
    }

    public  String getPaymentInfoCOD(String CODPaymentText) {

        if(getCODPaymentText.getText().equalsIgnoreCase(CODPaymentText)){

            return getCODPaymentText.getText();
        }

        return null;
    }

    public void getPaymentInfoCreditCard(String cardType, String cardHolderName, String cardNumber,
                                         String expireMonth, String expireYear, String cardCode) {
        selectByVisibleText(selectCreditCard, cardType);
        type(getCardHolderName, cardHolderName);
        type(getCardNumber, cardNumber);
        selectByVisibleText(getExpireMonth, expireMonth);
        selectByVisibleText(getExpireYear, expireYear);
        type(getCardCode, cardCode);
    }

    public boolean isConfirmOrderHeaderPresent(String confirmHeaderText) {
        return checkoutHeaders.stream()
                .map(WebElement::getText)
                .anyMatch(headerText -> headerText.trim().equalsIgnoreCase(confirmHeaderText));
    }

    public CheckoutPage clickConfirmOrder(){
        scrollToElement(confirmOrder);
        click(confirmOrder);
        return this;
    }

    public CheckoutPage waitAfterConfirmOrder(){
        waitForInvisibility(waitAfterConfirmOrder);
        return this;
    }

    public String getOrderSuccessText(){
        return orderSuccessText.getText();
    }

    public HomePage clickOrderSuccessContinue(){
        click(orderSuccessContinue);
        return new HomePage(driver);
    }


}







    



