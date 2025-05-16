package com.mycompany.app;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileWriter;
import java.io.PrintWriter;

public class Task3 {
    public static void getWeatherForecast() {
        System.setProperty("webdriver.chrome.driver", "/Users/nikitakazunin/Downloads/chromedriver-mac-arm64/chromedriver");

        WebDriver webDriver = new ChromeDriver();
        try {
            webDriver.get("https://api.open-meteo.com/v1/forecast?latitude=56&longitude=44&hourly=temperature_2m,rain&current=cloud_cover&timezone=Europe%2FMoscow&forecast_days=1&wind_speed_unit=ms");

            WebElement elem = webDriver.findElement(By.tagName("pre"));
            String json_str = elem.getText();

            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(json_str);
            JSONObject hourly = (JSONObject) obj.get("hourly");

            JSONArray time = (JSONArray) hourly.get("time");
            JSONArray temp = (JSONArray) hourly.get("temperature_2m");
            JSONArray rain = (JSONArray) hourly.get("rain");

            System.out.printf("%-3s %-20s %-12s %-10s%n", "№", "Дата/время", "Температура", "Осадки (мм)");
            for (int i = 0; i < time.size(); i++) {
                System.out.printf("%-3d %-20s %-12s %-10s%n", i + 1, time.get(i), temp.get(i), rain.get(i));
            }

            try (PrintWriter writer = new PrintWriter(new FileWriter("result/forecast.txt"))) {
                writer.printf("%-3s %-20s %-12s %-10s%n", "№", "Дата/время", "Температура", "Осадки (мм)");
                for (int i = 0; i < time.size(); i++) {
                    writer.printf("%-3d %-20s %-12s %-10s%n", i + 1, time.get(i), temp.get(i), rain.get(i));
                }
            }

        } catch (Exception e) {
            System.out.println("Ошибка: " + e);
        } finally {
            webDriver.quit();
        }
    }
}