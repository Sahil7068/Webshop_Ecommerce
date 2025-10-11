package com.webshop.demo.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtils {
    private final WebDriver driver;
    private static final String SCREENSHOT_DIR = "screenshots";

    public ScreenshotUtils(WebDriver driver) {
        this.driver = driver;
        createDirectoryIfNotExists(SCREENSHOT_DIR);
    }

    public String takeScreenshot(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            fileName = "screenshot";
        }
        
        // Clean up the filename to remove any invalid characters
        String safeFileName = fileName.replaceAll("[^a-zA-Z0-9-_.]", "_");
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());
        String path = System.getProperty("user.dir") + File.separator + SCREENSHOT_DIR + 
                     File.separator + safeFileName + "_" + timestamp + ".png";
        
        if (this.driver == null) {
            System.err.println("WebDriver is not initialized. Cannot take screenshot.");
            return "";
        }

        try {
            // Ensure the directory exists
            createDirectoryIfNotExists(SCREENSHOT_DIR);
            
            // Take the screenshot
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destFile = new File(path);
            
            // Ensure the parent directory exists
            File parentDir = destFile.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }
            
            // Copy the screenshot to the destination
            FileUtils.copyFile(src, destFile);
            
            // Verify the file was created
            if (destFile.exists() && destFile.length() > 0) {
                System.out.println("Screenshot saved to: " + destFile.getAbsolutePath());
                return destFile.getAbsolutePath();
            } else {
                System.err.println("Screenshot file was not created or is empty");
                return "";
            }
        } catch (Exception e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
            e.printStackTrace();
            return "";
        }
    }

    private void createDirectoryIfNotExists(String directoryName) {
        File directory = new File(System.getProperty("user.dir") + File.separator + directoryName);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Created directory: " + directory.getAbsolutePath());
            } else {
                System.err.println("Failed to create directory: " + directory.getAbsolutePath());
            }
        }
    }

    public String getScreenshotAsBase64() {
        if (this.driver == null) {
            System.err.println("WebDriver is not initialized. Cannot take screenshot.");
            return "";
        }
        try {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        } catch (Exception e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
            return "";
        }
    }
}
