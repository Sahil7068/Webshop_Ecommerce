package com.webshop.demo.base;

import com.webshop.demo.components.HeaderComponent;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Actions actions;
    protected JavascriptExecutor js;

    // Default timeout for waits
    private static final Duration DEFAULT_WAIT = Duration.ofSeconds(15);

    // Constructor
    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, DEFAULT_WAIT);
        actions = new Actions(driver);
        js = (JavascriptExecutor) driver;
    }

    private HeaderComponent headerComponent;

    protected HeaderComponent header() {
        if (headerComponent == null) {
            headerComponent = new HeaderComponent(driver);
        }
        return headerComponent;
    }


    // ------------------------------------------------------------
    // WAIT METHODS
    // ------------------------------------------------------------
    protected void waitForVisibility(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void waitForInvisibility(WebElement element) {
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    protected void waitForClickability(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void waitForPresence(By locator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected boolean waitForUrlToContain(String partialUrl) {
        return wait.until(ExpectedConditions.urlContains(partialUrl));
    }

    protected boolean waitForTextToBePresent(WebElement element, String text) {
        return wait.until(ExpectedConditions.textToBePresentInElement(element, text));
    }

    // ------------------------------------------------------------
    // BASIC ELEMENT ACTIONS
    // ------------------------------------------------------------
    protected void click(WebElement element) {
        waitForClickability(element);
        element.click();
    }

    protected void jsClick(WebElement element) {
        js.executeScript("arguments[0].click();", element);
    }

    protected void type(WebElement element, String text) {
        waitForVisibility(element);
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(WebElement element) {
        waitForVisibility(element);
        return element.getText().trim();
    }

    protected String getAttribute(WebElement element, String attribute) {
        waitForVisibility(element);
        return element.getAttribute(attribute);
    }

    protected boolean isDisplayed(WebElement element) {
        try {
            waitForVisibility(element);

            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    protected boolean isEnabled(WebElement element) {
        try {
            waitForVisibility(element);

            return element.isEnabled();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    // ------------------------------------------------------------
    // PAGE NAVIGATION
    // ------------------------------------------------------------
    protected void navigateTo(String url) {
        driver.get(url);
    }

    protected String getPageTitle() {
        return driver.getTitle();
    }

    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    // ------------------------------------------------------------
    // DROPDOWN / SELECT METHODS
    // ------------------------------------------------------------
    protected void selectByVisibleText(WebElement dropdown, String text) {
        new Select(dropdown).selectByVisibleText(text);
    }

    protected void selectByValue(WebElement dropdown, String value) {
        new Select(dropdown).selectByValue(value);
    }

    protected void selectByIndex(WebElement dropdown, int index) {
        new Select(dropdown).selectByIndex(index);
    }

    protected String getSelectedOption(WebElement dropdown) {
        return new Select(dropdown).getFirstSelectedOption().getText();
    }

    protected List<String> getAllDropdownOptions(WebElement dropdown) {
        List<WebElement> options = new Select(dropdown).getOptions();
        List<String> texts = new ArrayList<>();
        for (WebElement opt : options) {
            texts.add(opt.getText());
        }
        return texts;
    }

    // ------------------------------------------------------------
    // ACTIONS CLASS UTILITIES
    // ------------------------------------------------------------
    protected void hoverOverElement(WebElement element) {
        actions.moveToElement(element).perform();
    }

    protected void doubleClick(WebElement element) {
        actions.doubleClick(element).perform();
    }

    protected void rightClick(WebElement element) {
        actions.contextClick(element).perform();
    }

    protected void dragAndDrop(WebElement source, WebElement target) {
        actions.dragAndDrop(source, target).perform();
    }

    protected void sendKeysUsingActions(WebElement element, String text) {
        actions.moveToElement(element).click().sendKeys(text).perform();
    }

    // ------------------------------------------------------------
    // JAVASCRIPT EXECUTOR UTILITIES
    // ------------------------------------------------------------
    protected void scrollToElement(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    protected void scrollBy(int x, int y) {
        js.executeScript("window.scrollBy(arguments[0], arguments[1]);", x, y);
    }

    protected void highlightElement(WebElement element) {
        js.executeScript("arguments[0].style.border='2px solid red';", element);
    }

    protected void refreshPage() {
        js.executeScript("location.reload();");
    }

    protected Object executeJS(String script, Object... args) {
        return js.executeScript(script, args);
    }

    protected int extractDigitsAsInt(String txt) {
        if (txt == null || txt.isEmpty()) return 0;
        // remove all non-digit characters
        String digits = txt.replaceAll("\\D+", "");
        if (digits.isEmpty()) return 0;
        try {
            return Integer.parseInt(digits);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    // ------------------------------------------------------------
    // ALERT HANDLING
    // ------------------------------------------------------------
    protected void acceptAlert() {
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }

    protected void dismissAlert() {
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().dismiss();
    }

    protected String getAlertText() {
        wait.until(ExpectedConditions.alertIsPresent());
        return driver.switchTo().alert().getText();
    }

    // ------------------------------------------------------------
    // FRAME HANDLING
    // ------------------------------------------------------------
    protected void switchToFrameByIndex(int index) {
        driver.switchTo().frame(index);
    }

    protected void switchToFrameByNameOrId(String nameOrId) {
        driver.switchTo().frame(nameOrId);
    }

    protected void switchToFrameByElement(WebElement frameElement) {
        driver.switchTo().frame(frameElement);
    }

    protected void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    // ------------------------------------------------------------
    // WINDOW HANDLING
    // ------------------------------------------------------------
    protected void switchToWindowByTitle(String title) {
        Set<String> allWindows = driver.getWindowHandles();
        for (String window : allWindows) {
            driver.switchTo().window(window);
            if (driver.getTitle().equals(title)) {
                break;
            }
        }
    }

    protected void switchToWindowByIndex(int index) {
        List<String> windows = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(windows.get(index));
    }

    protected void closeCurrentWindow() {
        driver.close();
    }



    // ------------------------------------------------------------
    // MISC UTILS
    // ------------------------------------------------------------
    protected void clearField(WebElement element) {
        waitForVisibility(element);
        element.clear();
    }

    protected void pressEnter(WebElement element) {
        element.sendKeys(Keys.ENTER);
    }

    protected void pressTab(WebElement element) {
        element.sendKeys(Keys.TAB);
    }

    protected void pressEscape() {
        actions.sendKeys(Keys.ESCAPE).perform();
    }

    protected boolean isElementPresent(By locator) {
        return !driver.findElements(locator).isEmpty();
    }

    protected void pause(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
