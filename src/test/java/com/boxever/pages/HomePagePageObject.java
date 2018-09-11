package com.boxever.pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class HomePagePageObject extends BasePage {
    protected final WebDriver driver;

    //Privacy policy and cookies
    @FindBy(css = "button[class=\"btn btn-outline btn-outline-black strong\"]")
    private WebElement iUnderstandPrivacyPoliciBtn;

    @FindBy(css = "button[class=\"width-full btn btn-outline btn-outline-black\"]")
    private WebElement acceptCookiesBtn;

    //Header
    @FindBy(id = "sign-in")
    private WebElement signInBtn;

    @FindBy(id = "register")
    private WebElement registerBtn;

    //Search
    @FindBy(id = "search-query")
    private WebElement searchItemInput;

    @FindBy(css = "button[class=\"btn btn-primary\"]")
    private WebElement searchButton;

    public HomePagePageObject(WebDriver driver) throws Exception {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * This method wait for the presence of the elements
     */
    public void waitUntilLoaded() {
        waitForElementVisible(iUnderstandPrivacyPoliciBtn);
        waitForElementVisible(signInBtn);
        waitForElementVisible(registerBtn);
    }

    /**
     * This method tap on the button I Understand on the home page in order to dismiss the message
     */
    public void dismissPrivacyTerm() {
        try {
            waitForElementVisible(iUnderstandPrivacyPoliciBtn);
            iUnderstandPrivacyPoliciBtn.click();
        } catch (Exception NoSuchElementException) {
            System.out.print("The Terms and Privacy message is not displayed");
        }
    }

    public void dismissCookieModal() {
        try {
            waitForElementClickable(acceptCookiesBtn);
            acceptCookiesBtn.click();
        } catch (Exception NoSuchElementException) {
            System.out.print("The Terms and Privacy message is not displayed");
        }
    }

    public void tapRegisterButton() {
        waitForElementVisible(registerBtn);
        registerBtn.click();
    }

    public void searchForItem(String item) {
        waitForElementClickable(searchItemInput);
        searchItemInput.sendKeys(item);
        waitForElementVisible(searchButton);
        searchButton.click();
    }

    public void sortBy(String sortType) {
        WebElement sortDropDown = driver.findElement(By.cssSelector("ul[class=\"list-nav\"]"));
        driver.findElement(By.cssSelector("div[class=\"text-smaller\"]")).click();
        switch (sortType) {
            case "Most Recent":
                sortDropDown.findElement(By.cssSelector("#sortby > div > div > div > ul > li:nth-child(1) > a")).click();
                break;
            case "Relevancy":
                sortDropDown.findElement(By.cssSelector("#sortby > div > div > div > ul > li:nth-child(2) > a")).click();
                break;
            case "Highest Price":
                sortDropDown.findElement(By.cssSelector("#sortby > div > div > div > ul > li:nth-child(3) > a")).click();
                break;
            case "Lowest Price":
                sortDropDown.findElement(By.cssSelector("#sortby > div > div > div > ul > li:nth-child(4) > a")).click();
                break;
        }
    }

    public void tapPaginationPage(String page) {
        driver.findElement(By.cssSelector("a[data-page=\"250\"]")).click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Double> getListPrices() {
        int i = 0;
        int count = 0;
        List<WebElement> itemLists = driver.findElements(By.cssSelector("ul[class=\"block-grid-xs-2 block-grid-xl-4 hide-lg organic-row-impression-logging block-grid-no-whitespace float-clear pb-xs-1-5\"]"));
        List<WebElement> prices = driver.findElements(By.cssSelector("span[class=\"currency-value\"]"));
        List<Double> pricesDouble = new ArrayList<>();
        for (i=0; i<prices.size(); i++){
            if (!prices.get(i).getText().equals(""))
                pricesDouble.add(Double.parseDouble(prices.get(i).getText().replace(",",".")));
        }

        //Remove the first empy values and ads
        for (i=0; i<prices.size(); i++){
            if (pricesDouble.get(i) < 1.0) {
                count = i;
                break;
            }
        }
        pricesDouble = pricesDouble.subList(count, pricesDouble.size());
        return pricesDouble;

    }

    public boolean checkPricesSort(List<Double> arrayPrices, int count){
        /*Check the first (count) list of items,
        This method can be improved, removing all the ads articles
         */
        for (int i=0; i< count;i++){
            if (arrayPrices.get(i).floatValue() > arrayPrices.get(i+1).floatValue()) {
                return false;
            }
        }
        return true;
    }

    public void scrollToElementCss(String cssSelector){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement element = driver.findElement(By.cssSelector(cssSelector));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);

    }

    public void selectMostExpensiveArticle(){
        WebElement tableItems = driver.findElement(By.cssSelector("ul[class=\"block-grid-xs-2 block-grid-xl-4 hide-lg organic-row-impression-logging block-grid-no-whitespace float-clear pb-xs-1-5\"]"));
        int size = tableItems.findElements(By.tagName("li")).size();
        WebElement lastElement = tableItems.findElements(By.tagName("li")).get(size-1);
        String parentWindow = driver.getWindowHandle();
        lastElement.click();
        //TO IMPROVE
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Set<String> windowsHandler = driver.getWindowHandles();
        for (String winHandler : windowsHandler)
            if (!winHandler.equals(parentWindow)) {
                driver.switchTo().window(winHandler);
            }
        driver.findElement(By.cssSelector("button[class=\"btn btn-primary width-full btn-buy-box  mt-xs-2 mt-md-0\"]")).click();
        driver.findElement(By.cssSelector("div[class=\"cart-payment-section \"]"));
        driver.findElement(By.id("page-title"));
        Assert.assertEquals(true, "1".equals(driver.findElement(By.cssSelector("span[class=\"count \"]")).getText()));
    }

    public void selectFirstArticle() {
        WebElement tableItems = driver.findElement(By.cssSelector("ul[class=\"block-grid-xs-2 block-grid-xl-4 hide-lg organic-row-impression-logging block-grid-no-whitespace float-clear pb-xs-1-5\"]"));
        //The first 4 elements are ads items
        WebElement firstElement = tableItems.findElements(By.tagName("li")).get(5);
        String parentWindow = driver.getWindowHandle();
        firstElement.click();
        //TO IMPROVE
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Set<String> windowsHandler = driver.getWindowHandles();
        for (String winHandler : windowsHandler)
            if (!winHandler.equals(parentWindow)) {
                driver.switchTo().window(winHandler);
            }
        driver.findElement(By.cssSelector("button[class=\"btn btn-primary width-full btn-buy-box  mt-xs-2 mt-md-0\"]")).click();
        driver.findElement(By.cssSelector("div[class=\"cart-payment-section \"]"));
        driver.findElement(By.id("page-title"));
    }

    public void validateCartItems(int items){
        Assert.assertEquals(items, Integer.parseInt(driver.findElement(By.cssSelector("span[class=\"count \"]")).getText().toString()));
    }
}
