package org.anele.base;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.anele.utils.PropertyFileManager;

import static io.restassured.RestAssured.given;

public class BaseTest {

    protected PropertyFileManager propertyFileManager;

    public BaseTest() {
        this.propertyFileManager = new PropertyFileManager();
        this.propertyFileManager.loadProperties();
    }

    public void setup() {

        String base_url = propertyFileManager.getBaseUrl();
        String base_path = propertyFileManager.getPath();
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
}
