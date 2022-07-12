package RestAssured;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class GetMethod {

    public static void main(String[] args) {
        String baseUri = "https://jsonplaceholder.typicode.com";

        // request scope
        RequestSpecification request = given();

        // add baseUri and basePath to request
        request.baseUri(baseUri);
        request.basePath("/todos");

        // response scope
        final String FIST_TODO = "/1";
        Response response = request.get(FIST_TODO);
        response.prettyPrint();
        response.then().body("userId", equalTo(1));
        response.then().body("id", equalTo(1));
        response.then().body("title", equalTo("delectus aut autem"));
        response.then().body("completed", equalTo(false));
    }
}
