package org.anele.tests;

import com.aventstack.extentreports.ExtentReports;
import io.restassured.response.Response;
import org.anele.base.BaseTest;
import org.anele.utils.PropertyFileManager;
import org.apache.http.HttpStatus;
import org.testng.Assert;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ProductsTests {

    protected BaseTest baseCore;

    protected static PropertyFileManager PROPERTY_FILE_MANAGER;

    public ProductsTests() {
        this.baseCore = new BaseTest();
        PROPERTY_FILE_MANAGER = new PropertyFileManager();
    }

    @BeforeSuite
    public void setup() {

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
