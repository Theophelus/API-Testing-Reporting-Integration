package org.anele.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.restassured.response.Response;
import org.anele.base.BaseTest;
import org.anele.listeners.ExtentTestNGListener;
import org.anele.model.Product;
import org.anele.model.Products;
import org.anele.utils.PropertyFileManager;
import org.apache.http.HttpStatus;
import org.testng.Assert;

import org.testng.annotations.*;

import java.util.List;

import static io.restassured.RestAssured.given;

@Listeners(ExtentTestNGListener.class)
public class ProductsTests {

    protected BaseTest baseCore;
    protected static PropertyFileManager PROPERTY_FILE_MANAGER;

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
//        extent.flush();
    }

    //create a method to build the response
    @Test
    public void testGETAllProducts() {
        //get the get all products operation
        Response httpResponse = baseCore.getOperation();
        //check status code of the response, if not 200. fail the test
        if (httpResponse.statusCode() != HttpStatus.SC_OK)
            Assert.assertEquals(httpResponse.statusCode(), HttpStatus.SC_OK, "Request failed: " + httpResponse.statusCode());

        int actual_results = httpResponse.jsonPath().getList("products").size();
        int expected_results = 30;
        Assert.assertEquals(actual_results, expected_results, "provide values do not match");

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
