package org.anele.tests;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.anele.base.BaseTest;
import org.anele.listeners.ExtentTestNGListener;
import org.anele.model.Product;
import org.anele.utils.ApiPaths;
import org.anele.utils.JsonManagerUtil;
import org.anele.utils.PropertyFileManager;
import org.apache.http.HttpStatus;
import org.testng.Assert;

import org.testng.annotations.*;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

@Listeners(ExtentTestNGListener.class)
public class ProductsTests extends BaseTest {

    protected BaseTest baseTest;
    protected static PropertyFileManager PROPERTY_FILE_MANAGER;
    protected static JsonManagerUtil JSON_MANAGER_UTIL;

    public ProductsTests() {
        this.baseTest = new BaseTest();
        PROPERTY_FILE_MANAGER = new PropertyFileManager();
        JSON_MANAGER_UTIL = new JsonManagerUtil();
    }

    @Test
    public void testValidateJsonResponse() {

        File ready_json = JSON_MANAGER_UTIL.load_json_schema("product_json_schema.json");
        Map<String, Integer> params = new HashMap<>();
        //Id for product to be retrieved
        int product_id = 1;
        params.put("product_id", product_id);

        //Get Current Product based on provided product Id
        baseTest.getOperation(params)
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .body(JsonSchemaValidator.matchesJsonSchema(ready_json))
                .extract().response();

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

    @Test(dependsOnMethods = "testValidateJsonResponse")
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

    @Test
    public void createPOSTProduct() {
        //get list of product
        Product product = JSON_MANAGER_UTIL.ready_products("create_product.json");
        System.out.println("Product title: " + product.getTitle());
        //create a post and store response
        Response http_response = baseTest.postOperation(product, ApiPaths.ADD);
        Assert.assertEquals(http_response.statusCode(), HttpStatus.SC_CREATED, "Expected status 201, but got: " + http_response.statusCode());
        // make a get request to validate product title
//        Map<String, Integer> params = new HashMap<>();
//        //Id for product to be retrieved
//        int product_id = product.getId();
//        params.put("product_id", product_id);
//
//        Response product_name = baseTest.getOperation(params)
//                .then().extract().response();
//
//        Assert.assertEquals(product_name.jsonPath().get("title"), "NIVEA MEN Even Tone Face Creme Tin with Liquorice Extract, 150ml"
//                , "Product titles do not match...!");
    }

    @Test
    public void testGETReturnPolicyForAppleAirPodsMaxSilver() {
        //Query params value
        String phone = "phones";
        //define a map to store query params
        Map<String, String> query_params = new HashMap<>();
        query_params.put("q", phone);
        //GET response, with query params and path params
        Response http_response = baseTest.getOperation(query_params, ApiPaths.SEARCH);
        //deserialize response into products
        List<Product> products =
                http_response.jsonPath().getList("products", Product.class);

        //assertFalse if product is empty
        Assert.assertFalse(products.isEmpty(), "List of products is empty");

        //loop through the list, and filter by product name, then assert correct return policy
        for (Product product : products) {
            if (product.title.equalsIgnoreCase("Apple AirPods Max Silver")) {
                //String get return policy
                String return_policy = product.returnPolicy;
                Assert.assertEquals(return_policy, "90 days return policy",
                        "Return policies do not match");
                return;
            }
        }
    }

}
