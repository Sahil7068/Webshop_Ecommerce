package com.webshop.demo.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.webshop.demo.base.BaseTest;
import com.webshop.demo.utils.ExtentReportManager;
import com.webshop.demo.utils.ScreenshotUtils;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;

public class TestListener implements ITestListener {
    private WebDriver driver;
    private ScreenshotUtils screenshotUtils;
    private ExtentReports extent;
    private ExtentTest test;

    private static final String REPORTS_DIR = "reports";
    
    @Override
    public void onStart(ITestContext context) {
        String suiteName = context.getSuite().getName();
        String timestamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
        
        // Create reports directory if it doesn't exist
        File reportsDir = new File(System.getProperty("user.dir") + File.separator + REPORTS_DIR);
        if (!reportsDir.exists()) {
            if (reportsDir.mkdirs()) {
                System.out.println("Created reports directory: " + reportsDir.getAbsolutePath());
            } else {
                System.err.println("Failed to create reports directory: " + reportsDir.getAbsolutePath());
            }
        }
        
        String reportPath = reportsDir.getAbsolutePath() + File.separator + 
                          suiteName + "_" + timestamp + ".html";

        extent = ExtentReportManager.getReportObject(reportPath);
        System.out.println("Extent report will be generated at: " + reportPath);
    }

    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());


    }

    @Override
    public void onTestSuccess(ITestResult result) {

    }

    @Override
    public void onTestFailure(ITestResult result) {
        // Log the failure
        test.fail(result.getThrowable());
        
        // Get the test instance
        Object testInstance = result.getInstance();
        
        try {
            // Try to get the WebDriver instance from the test class
            driver = (WebDriver) result.getTestClass()
                    .getRealClass()
                    .getSuperclass()
                    .getDeclaredField("driver")
                    .get(testInstance);
                    
            if (driver != null) {
                try {
                    // Take screenshot
                    screenshotUtils = new ScreenshotUtils(driver);
                    String screenshotPath = screenshotUtils.takeScreenshot(result.getMethod().getMethodName());
                    
                    // Add screenshot to report
                    if (screenshotPath != null && !screenshotPath.isEmpty()) {
                        System.out.println("Screenshot captured at: " + screenshotPath);
                        try {
                            test.addScreenCaptureFromPath(screenshotPath, result.getMethod().getMethodName());
                            test.info("Screenshot captured for failure");
                        } catch (Exception e) {
                            test.warning("Failed to attach screenshot to report: " + e.getMessage());
                        }
                    } else {
                        test.warning("Screenshot path is empty or null");
                    }
                } catch (Exception e) {
                    test.warning("Failed to capture screenshot: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                test.warning("WebDriver is null. Cannot capture screenshot.");
            }
        } catch (Exception e) {
            test.warning("Error while processing test failure: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {

    }



    @Override
    public void onFinish(ITestContext context) {
        System.out.println("Test Context Finished: " + context.getName());
        extent.flush();
    }


}
