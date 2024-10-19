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
        String method_name = get_method_name(result);
        //define extentTest object for current test executed
        ExtentTest test = extent.createTest(method_name, result.getMethod().getDescription());
        //set test as an attribute, to be accessible by other methods in the listener
        result.setAttribute(method_name + "-extent_test", test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        //get current method name
        String method_name = get_method_name(result);
        //define extentTest object for current test executed results
        ExtentTest test = (ExtentTest) result.getAttribute(method_name + "-extent_test");
        //get test attributes
        String log = "The test " + method_name + " was executed successfully";
        test.log(Status.PASS, log);
        //get method to log request and response information
        log_request_specifications_and_response_details(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        //get current method name
        String method_name = get_method_name(result);
        //define extentTest object for current test executed results
        ExtentTest test = (ExtentTest) result.getAttribute(method_name + "-extent_test");
        //get test attributes
        String log = "The test " + method_name + " failed" + result.getThrowable();
        test.log(Status.FAIL, log);

        //get method to log request and response information
        log_request_specifications_and_response_details(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        //get current method name
        String method_name = get_method_name(result);
        //define extentTest object for current test executed results
        ExtentTest test = (ExtentTest) result.getAttribute(method_name + "-extent_test");
        //get test attributes
        String log = "The test " + method_name + " was skipped";
        test.log(Status.SKIP, log);
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentManager.flushExtentReport();
    }

    //String method to get current method name from ITestResults to reuse across overloaded methods
    public String get_method_name(ITestResult result) {
        return result.getMethod().getMethodName();
    }

    void log_request_specifications_and_response_details(ITestResult result) {
        String method_name = result.getMethod().getMethodName();
        ExtentTest test = (ExtentTest) result.getAttribute(method_name + "-extent_test");

        QueryableRequestSpecification http_request = (QueryableRequestSpecification) result.getAttribute(method_name + "-request_spec");

        if (http_request != null) {
            test.log(Status.INFO, "Endpoint is: " + http_request.getURI());
            test.log(Status.INFO, "Request body is: " + http_request.getBody());
        } else {
            test.log(Status.WARNING, "No request information available for this test.");
        }

        Response http_response = (Response) result.getAttribute(method_name + "-response");

        if (http_response != null) {
            test.log(Status.INFO, "Http Status is: " + http_response.statusCode());
        } else {
            test.log(Status.WARNING, "No response information available for this test.");
        }
    }


}
