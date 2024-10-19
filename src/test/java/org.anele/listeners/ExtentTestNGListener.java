package org.anele.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentTestNGListener implements ITestListener {

    protected static ExtentReports extent;

    @Override
    public void onStart(ITestContext context) {
        //get instance of current Extent report
        extent = ExtentManager.getReport();
    }

    @Override
    public void onTestStart(ITestResult result) {
        //get current method name
        String method_name = result.getMethod().getMethodName();
        //define extentTest object for current test executed
        ExtentTest test = extent.createTest(method_name, result.getMethod().getDescription());
        //set test as an attribute, to be accessible by other methods in the listener
        result.setAttribute(method_name + "-extent_test", test);
    }


}
