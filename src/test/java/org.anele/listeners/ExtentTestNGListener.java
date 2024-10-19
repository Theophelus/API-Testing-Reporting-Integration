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


}
