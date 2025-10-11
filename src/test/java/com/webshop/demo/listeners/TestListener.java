package com.webshop.demo.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
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
            // Get the WebDriver instance using the getter method from BaseTest
            if (testInstance instanceof BaseTest) {
                driver = ((BaseTest) testInstance).getDriver();
            }

            if (driver != null) {
                try {
                    // Take screenshot
                    screenshotUtils = new ScreenshotUtils(driver);
                    String base64Screenshot = screenshotUtils.getScreenshotAsBase64();

                    if (base64Screenshot != null && !base64Screenshot.isEmpty()) {
                        // Add the screenshot to the report
                        test.fail(MediaEntityBuilder
                                .createScreenCaptureFromBase64String(base64Screenshot, "Screenshot")
                                .build());
                    } else {
                        test.warning("Screenshot is empty or null");
                    }
                } catch (Exception e) {
                    test.warning("Failed to capture screenshot: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                test.warning("WebDriver is null. Cannot capture screenshot.");
            }
        } catch (Exception e) {
            test.warning("Error processing test failure: " + e.getMessage());
            e.printStackTrace();
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
