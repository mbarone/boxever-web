package com.boxever;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BaseTest {
    protected WebDriver driver;

    public void setUp() {
        setUpDriver();
        //driver.manage().window().maximize();
    }

    private void setUpDriver() {
        String chromeDriverPath = System.getProperty("chrome.driver.path");
        String firefoxDriverPath = System.getProperty("firefox.driver.path");

        if(chromeDriverPath != null) {
            driver = getChromeDriver(chromeDriverPath);
        } else if (firefoxDriverPath != null){
            driver = getFirefoxDriver(firefoxDriverPath);
        }
        else{
            driver = getChromeClasspathDriver();
        }
    }

    private WebDriver getFirefoxDriver(String firefoxDriverPath) {
        System.setProperty("webdriver.gecko.driver", firefoxDriverPath);
        return new FirefoxDriver();
    }

    private WebDriver getChromeDriver(String chromeDriverPath) {
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        return new ChromeDriver();
    }

    private WebDriver getChromeClasspathDriver() {
        System.setProperty("webdriver.chrome.driver", getChromeDriverPath());
        return new ChromeDriver();
    }

    private WebDriver getFirefoxClasspathDriver() {
        System.setProperty("webdriver.gecko.driver", getFirefoxDriverPath());
        return new ChromeDriver();
    }

    private String getChromeDriverPath (){
        String osName = System.getProperty("os.name");
        if (osName.contains("Mac")) {//MAC
            return getClass().getClassLoader().getResource("drivers/chromedriver").getPath();
        }else {
            //Linux
            return getClass().getClassLoader().getResource("drivers/Linux/chromedriver").getPath();
        }
    }

    private String getFirefoxDriverPath (){
        String osName = System.getProperty("os.name");
        if (osName.contains("Mac")) {//MAC
            return getClass().getClassLoader().getResource("drivers/chromedriver").getPath();
        }else {
            //Linux
            return getClass().getClassLoader().getResource("drivers/Linux/chromedriver").getPath();
        }
    }

    public void tearDown() {
        driver.quit();
    }
}
