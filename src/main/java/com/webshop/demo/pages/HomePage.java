package com.webshop.demo.pages;

import com.webshop.demo.base.BasePage;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
    public HomePage(WebDriver driver) {
        super(driver);
    }


    public String getHomePageTitle(){
        return getPageTitle();

    }

    public HomePage goToLogout(){
        header().goToLogout();
        return this;
    }

    public boolean isLogoutLinkVisible(){
        return header().isLogoutLinkVisible();
    }
}
