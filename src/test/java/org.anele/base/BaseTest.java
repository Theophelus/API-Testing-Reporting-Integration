package org.anele.base;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import org.anele.model.Product;
import org.anele.utils.ApiPaths;
import org.anele.utils.PropertyFileManager;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.BeforeSuite;

import java.util.Map;

public class BaseTest {

    protected PropertyFileManager propertyFileManager;

    public BaseTest() {
        this.propertyFileManager = new PropertyFileManager();
        this.propertyFileManager.loadProperties();
    }

    @BeforeSuite
    public void setup() {

        String base_url = propertyFileManager.getBaseUrl();
        String base_path = propertyFileManager.getPath();

        System.out.println("Base URL: " + base_url);
        System.out.println("Base PATH: " + base_path);

        RestAssured.baseURI = base_url;
        RestAssured.basePath = base_path;

    }


    public RequestSpecification getRequestSpec() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build();
    }

    public Response getOperation() {
        // Set request
        getRequestSpec().relaxedHTTPSValidation();

        RequestSpecification http_request = RestAssured.given();
        //GET request information
        specificationRequestLogDetails(http_request);
        try {
            // Get response
            Response http_response = http_request.when().get();
            //GET response information
            responseLogDetails(http_response);
            return http_response;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to execute GET request to endpoint: ", e);
        }
    }
    /*
    Method overload that take three arguments RequestSpecifications, path_params, and current_id
    to filter specific product
     */

    public Response getOperation(Map<String, Integer> path_params) {

        //set request
        RequestSpecification http_request = getRequestSpec().relaxedHTTPSValidation();
        RequestSpecification specification = null;
        //check if path params is not empty and contains a specific key
        if (!path_params.isEmpty() && path_params.containsKey("product_id")) {
            //make a request, after conditions met
            specification = RestAssured
                    .given()
                    .pathParams(path_params)
                    .spec(http_request);

            //get http request specification information
            specificationRequestLogDetails(specification);
        }

        assert specification != null;

        //perform GET request and store response
        Response http_response =
                specification
                        .when()
                        .get("/{product_id}");
        //get response information
        responseLogDetails(http_response);
        //GET response
        return http_response;
    }

    public Response getOperation(Map<String, String> params, ApiPaths path) {
        //relax ssl certificate if exists
        var specification = getRequestSpec().relaxedHTTPSValidation();
        RequestSpecification http_request = null;

        if (!params.isEmpty()) {
            http_request = RestAssured.given()
                    .queryParams(params)
                    .spec(specification);
            //attach request information on specificationRequestLogDetails method
            specificationRequestLogDetails(http_request);
        }

        //store response
        assert http_request != null;
        Response http_response = http_request
                .when()
                .get(path.toString());

        //attach request information
        responseLogDetails(http_response);
        //return response
        return http_response;
    }

    /*
     * Define Http response  method that takes three parameters
     * @param path to api endpoint for creating resources
     * @param return http response from the server
     */

    public Response postOperation(Product product, ApiPaths path) {
        RequestSpecification http_specifications = getRequestSpec().relaxedHTTPSValidation();
        RequestSpecification http_request = null;

        if (product != null && !product.toString().isEmpty()) {
            http_request = RestAssured
                    .given()
                    .and()
                    .body(product)
                    .spec(http_specifications);
            //attach request information on specificationRequestLogDetails method
            specificationRequestLogDetails(http_request);
        }

        //post product, and store response
        assert http_request != null;
        Response http_response = http_request
                .when()
                .post(path.toString());
        //attach response information using responseLogDetails method
        responseLogDetails(http_response);

        return http_response;

    }

    /*
    specificationRequestLogDetails method take RequestSpecification object as an argument,
    and retrieve details information about http request executed during a specific test and details are store in the TestNG reporter.
     */

    public void specificationRequestLogDetails(RequestSpecification request_spec) {
        //check if null request spec
        if (request_spec == null)
            throw new IllegalArgumentException("RequestSpecification needs an argument/ can not be null");

        //QueryableRequestSpecification, hold details about executed http request
        QueryableRequestSpecification queryableRequestSpecification =
                SpecificationQuerier.query(request_spec);
        //get current test results from listeners
        ITestResult result = Reporter.getCurrentTestResult();
        //get method name
        String method_name = getMethodName(result);
        //set results for other method
        result.setAttribute(method_name + "-request_spec", queryableRequestSpecification);
    }

    public void responseLogDetails(Response http_response) {
        //check if null request spec
        if (http_response == null)
            throw new IllegalArgumentException("Response needs an argument/ can not be null");

        //define ITestResults to get test information
        ITestResult result = Reporter.getCurrentTestResult();
        String method_name = getMethodName(result);

        result.setAttribute(method_name + "-response", http_response);
    }

    //get current executing method name.
    public String getMethodName(ITestResult result) {
        return result.getMethod().getMethodName();
    }
}
