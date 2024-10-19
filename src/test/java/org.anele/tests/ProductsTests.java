package org.anele.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
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
        //calling the base setup method, that initialize common functionality
        this.baseCore.setup();
    }

    @AfterSuite
    public void tearDown() {
        //this will ensure the report is created
        extent.flush();
    }

    //create a method to build the response
    @Test
    public void getAllProducts() {
        //test object to log out details about this test
        ExtentTest test = extent.createTest("GET_all_product", "This test is to get all products in the api");
        //get the get all products operation
        Response httpResponse = baseCore.getOperation();
        //check status code of the response, if not 200. fail the test
        if (httpResponse.statusCode() != HttpStatus.SC_OK)
            throw new RuntimeException("Request failed: " + httpResponse.statusCode());

        int actual_results = httpResponse.jsonPath().getList("products").size();
        int expected_results = 30;
        Assert.assertEquals(actual_results, expected_results, "provide values do not match");

        //log basic test info
        test.log(Status.INFO, "GET request is executed successfully");
    }

    @Test
    @Ignore
    public void getASingleTest() {
        int current_id = 1;
        Products productList =
                given()
                        .header("Content-Type", "application/json")
                        .pathParam("id", current_id)
                        .when()
                        .get("/{id}")
                        .then().statusCode(HttpStatus.SC_OK)
                        .assertThat().extract().response().as(Products.class);

        List<Product> products = productList.products;
        Product product = products.stream()
                .filter(p -> p.id == current_id)
                .findFirst().orElse(null);

        System.out.println("filtered product: " + product);
    }


}
