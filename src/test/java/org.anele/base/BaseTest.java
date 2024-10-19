package org.anele.base;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import org.anele.utils.PropertyFileManager;
import org.testng.ITestResult;
import org.testng.Reporter;

public class BaseTest {

    protected PropertyFileManager propertyFileManager;

    public BaseTest() {
        this.propertyFileManager = new PropertyFileManager();
        this.propertyFileManager.loadProperties();
    }

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
        //set request
        RequestSpecification httpRequest = getRequestSpec().relaxedHTTPSValidation();

        try {
            //get response
            return RestAssured
                    .given()
                    .log().all()
                    .spec(httpRequest)
                    .when()
                    .get();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Fail to get request", e);
        }
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
        result.setAttribute(method_name + " Request_spec ", queryableRequestSpecification);
    }

    public void responseLogDetails(Response http_response) {
        //define ITestResults to get test information
        ITestResult result = Reporter.getCurrentTestResult();
        String method_name = getMethodName(result);

        result.setAttribute(method_name + " Request ", http_response);
    }

    //get current executing method name.
    public String getMethodName(ITestResult result) {
        return result.getMethod().getMethodName();
    }
}
