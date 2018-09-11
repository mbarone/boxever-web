package com.boxever.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import static org.apache.commons.lang3.RandomStringUtils.*;

public class RegisterPageObject extends BasePage {
    protected final WebDriver driver;

    @FindBy (id = "email")
    private WebElement emailAddressInput;

    @FindBy (id = "first-name")
    private WebElement firstNameInput;

    @FindBy (id = "password")
    private WebElement passwordInput;

    @FindBy (id = "password-repeat")
    private WebElement repeatPasswordInput;

    @FindBy (id = "register_button")
    private WebElement registerButton;

    public RegisterPageObject(WebDriver driver) throws Exception {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void introduceAccountData(){
        String generatedString = randomAlphanumeric(8);
        waitForElementClickable(emailAddressInput);
        firstNameInput.sendKeys("Test");
        emailAddressInput.sendKeys(generatedString+"@mintemail.com");
        passwordInput.sendKeys(generatedString);
        repeatPasswordInput.sendKeys(generatedString);
    }

    public void tapRegisterButton(){
        waitForElementClickable(registerButton);
        registerButton.click();
    }

    public void verifyRegistration(){
        WebElement likedArticlesIcon = driver.findElement(By.cssSelector("span[class=\"etsy-icon nav-icon etsy-icon-relative\"]"));
        WebElement userIcon = driver.findElement(By.cssSelector("span[class=\"nav-icon nav-icon-image nav-icon-circle\"]"));
        WebElement userCountItems = driver.findElement(By.id("total-user-count"));
        waitForElementVisible(likedArticlesIcon);
        waitForElementVisible(userIcon);
        waitForElementVisible(userCountItems);
    }
}
