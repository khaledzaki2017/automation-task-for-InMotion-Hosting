package com.get.inmotion.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CartPage {

    private WebDriver driver;

    private By cartItemElements = By.cssSelector(".cart-item, .product-listing"); // accepts multiple patterns
    private By removeItemBtn = By.xpath("//button[contains(text(),'Remove') or contains(text(),'Delete')]");

    private By totalPriceLabel = By.xpath("//*[contains(text(),'Total') and contains(text(),'$')]");

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public List<WebElement> getCartItems() {
        return driver.findElements(cartItemElements);
    }

    public void removeItem() {
        driver.findElement(removeItemBtn).click();
    }

    public String getTotalPrice() {
        return driver.findElement(totalPriceLabel).getText();
    }

    public void refresh() {
        driver.navigate().refresh();
    }
}