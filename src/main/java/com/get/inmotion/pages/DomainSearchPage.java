package com.get.inmotion.pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DomainSearchPage {

    private WebDriver driver;

    // On the /domains page, the search input and button are likely present
    private By searchInput = By.xpath("//input[@type='search' or contains(@placeholder,'Search')]");
    private By searchButton = By.xpath("//button[contains(text(),'Search') or contains(@class,'search')]");

    // Results area
    private By availableLabel = By.xpath("//*[contains(text(),'available')]");
    private By priceLabel = By.xpath("//span[contains(text(),'$')]");
    private By addToCartBtn = By.xpath("//button[contains(text(),'Add to Cart') or contains(text(),'Register')]");

    public DomainSearchPage(WebDriver driver) {
        this.driver = driver;
    }

    public void searchDomain(String domain) {
        driver.findElement(searchInput).clear();
        driver.findElement(searchInput).sendKeys(domain);
        driver.findElement(searchButton).click();
    }

    public boolean isDomainAvailable() {
        return !driver.findElements(availableLabel).isEmpty();
    }

    public String getDomainPrice() {
        WebElement price = driver.findElement(priceLabel);
        return price.getText();
    }

    public void addToCart() {
        driver.findElement(addToCartBtn).click();
    }
}