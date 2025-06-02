package com.mycompany.app;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Task3 {
    public static void getWeather() {
        System.setProperty("webdriver.chrome.driver", "/Users/bigboy/Documents/chromedriver-mac-arm64/chromedriver");

        WebDriver driver = new ChromeDriver();
        try {
            driver.get("https://api.open-meteo.com/v1/forecast?latitude=56&longitude=44&hourly=temperature_2m,rain&current=cloud_cover&timezone=Europe%2FMoscow&forecast_days=1&wind_speed_unit=ms");

            WebElement content = driver.findElement(By.tagName("pre"));
            String rawJson = content.getText();

            JSONParser jsonParser = new JSONParser();
            JSONObject root = (JSONObject) jsonParser.parse(rawJson);
            JSONObject hourlyData = (JSONObject) root.get("hourly");

            JSONArray times = (JSONArray) hourlyData.get("time");
            JSONArray temperatures = (JSONArray) hourlyData.get("temperature_2m");
            JSONArray precipitation = (JSONArray) hourlyData.get("rain");

            DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols(Locale.getDefault());
            formatSymbols.setDecimalSeparator(',');
            DecimalFormat formatter = new DecimalFormat("0.00", formatSymbols);

            String header = String.format("%-4s %-20s %-14s %-12s", "№", "Дата/время", "Темп.(°C)", "Дождь(мм)");
            System.out.println(header);

            for (int idx = 0; idx < times.size(); idx++) {
                double tempC = Double.parseDouble(temperatures.get(idx).toString());
                double rainMM = Double.parseDouble(precipitation.get(idx).toString());

                String row = String.format("%-4d %-20s %-14s %-12s",
                        idx + 1,
                        times.get(idx),
                        formatter.format(tempC),
                        formatter.format(rainMM));
                System.out.println(row);
            }

            File outDir = new File("../result");
            if (!outDir.exists()) {
                outDir.mkdirs();
            }

            try (PrintWriter outWriter = new PrintWriter(new FileWriter("../result/forecast.txt"))) {
                outWriter.println(header);
                for (int idx = 0; idx < times.size(); idx++) {
                    double tempC = Double.parseDouble(temperatures.get(idx).toString());
                    double rainMM = Double.parseDouble(precipitation.get(idx).toString());

                    String line = String.format("%-4d %-20s %-14s %-12s",
                            idx + 1,
                            times.get(idx),
                            formatter.format(tempC),
                            formatter.format(rainMM));
                    outWriter.println(line);
                }
            }

        } catch (Exception ex) {
            System.err.println("Ошибка при получении прогноза: " + ex.getMessage());
        } finally {
            driver.quit();
        }
    }
}
