package org.anele.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.restassured.response.Response;
import org.anele.base.BaseTest;
import org.anele.model.Product;
import org.anele.model.Products;
import org.anele.utils.PropertyFileManager;
import org.apache.http.HttpStatus;
import org.testng.Assert;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.io.File;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static io.restassured.RestAssured.given;

public class ProductsTests {

    protected BaseTest baseCore;
    protected static PropertyFileManager PROPERTY_FILE_MANAGER;
    //define ExtentReports object to be used across test suites
    protected ExtentReports extent;

    public ProductsTests() {
        this.baseCore = new BaseTest();
        PROPERTY_FILE_MANAGER = new PropertyFileManager();
    }

    @BeforeSuite
    public synchronized void setup() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String file_date = format.format(new Date());

        //create report name using current date
        String file_name = "products_report_'" + file_date + "'" + ".html";
        File dir = new File("reports/");
        if (!dir.exists()) dir.mkdirs();

        //create a full path of the report file
        String get_path = Paths.get(dir.getAbsolutePath(), file_name).toString();
        //create ExtentSparkReport object to generate html report
        ExtentSparkReporter reporter = new ExtentSparkReporter(get_path);
        //initialize extent report object
        extent = new ExtentReports();

        //attach report
        extent.attachReporter(reporter);
        //calling the base setup method, that initialize common functionality
        this.baseCore.setup();
    }


    //a method to build the response
    @Test
    public void getAllProducts() {
        //get the get all products operation
        Response httpResponse = baseCore.getOperation();
        //check status code of the response, if not 200. fail the test
        if (httpResponse.statusCode() != HttpStatus.SC_OK)
            throw new RuntimeException("Request failed: " + httpResponse.statusCode());

        int actual_results = httpResponse.jsonPath().getList("products").size();
        int expected_results = 30;
        Assert.assertEquals(actual_results, expected_results, "provide values do not match");

    }


}