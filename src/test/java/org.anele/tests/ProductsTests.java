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

import javax.imageio.plugins.tiff.BaselineTIFFTagSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

@Listeners(ExtentTestNGListener.class)
public class ProductsTests extends BaseTest {

    protected BaseTest baseTest;
    protected static PropertyFileManager PROPERTY_FILE_MANAGER;

    public ProductsTests() {
        this.baseTest = new BaseTest();
        PROPERTY_FILE_MANAGER = new PropertyFileManager();
    }


    //create a method to build the response
    @Test
    public void testGETAllProducts() {
        //get the ge all products operation
        Response httpResponse = baseTest.getOperation();
        //check status code of the response, if not 200. fail the test
        if (httpResponse.statusCode() != HttpStatus.SC_OK)
            Assert.assertEquals(httpResponse.statusCode(), HttpStatus.SC_OK, "Request failed: " + httpResponse.statusCode());

        int actual_results = httpResponse.jsonPath().getList("products").size();
        int expected_results = 30;
        Assert.assertEquals(actual_results, expected_results, "provide values do not match");

    }

    @Test
    public void testGETASingleProduct() {
        Map<String, Integer> params = new HashMap<>();
        //Id for product to be retrieved
        int product_id = 1;
        params.put("product_id", product_id);

        //Get Current Product based on provided product Id
        Product product = baseTest.getOperation(params).thenReturn().as(Product.class);
        //asset that product Id matches expected value
        Assert.assertEquals(product.id, 1, "Product Id's do not match");

    }


}
