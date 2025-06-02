package com.mycompany.app;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Task2 {
    public static void getIP() {
        System.setProperty("webdriver.chrome.driver", "/Users/bigboy/Documents/chromedriver-mac-arm64/chromedriver");

        WebDriver driver = new ChromeDriver();
        try {
            driver.navigate().to("https://api.ipify.org/?format=json");

            WebElement preElement = driver.findElement(By.tagName("pre"));
            String responseText = preElement.getAttribute("innerText");

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(responseText);

            String ipAddress = (String) jsonObject.get("ip");

            System.out.println("Ваш IP: " + ipAddress);

        } catch (Exception ex) {
            System.err.println("Exception occurred: " + ex.getMessage());
        } finally {
            driver.quit();
        }
    }
}
