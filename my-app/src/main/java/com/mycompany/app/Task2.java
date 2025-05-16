package com.mycompany.app;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Task2 {
    public static void getIP() {
        System.setProperty("webdriver.chrome.driver", "/Users/nikitakazunin/Downloads/chromedriver-mac-arm64/chromedriver");
        WebDriver driver = new ChromeDriver();

        try {
            driver.get("https://api.ipify.org/?format=json");
            WebElement elem = driver.findElement(By.tagName("pre"));
            String jsonStr = elem.getText();

            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(jsonStr);
            String ip = (String) obj.get("ip");

            System.out.println("IP-адрес: " + ip);
        } catch (Exception e) {
            System.out.println("Ошибка при получении IP: " + e.getMessage());
        } finally {
            driver.quit();
        }
    }
}