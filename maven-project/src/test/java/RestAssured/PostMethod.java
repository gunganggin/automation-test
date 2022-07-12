package RestAssured;

import com.google.gson.Gson;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.containsStringIgnoringCase;

public class PostMethod {
    public static void main(String[] args) {

        String baseUri = "https://jsonplaceholder.typicode.com";

        RequestSpecification request = given();
        request.baseUri(baseUri);

        // thêm content-type vào header
        request.header(RequestCapability.defaultHeader);


        // Gson
        Gson gSon = new Gson();
        GsonContain postGson = new GsonContain();
        postGson.setUserId(1);
        postGson.setId(1);
        postGson.setTitle("post title to ...");
        postGson.setBody("post body to");

        // send Post request
        Response response = request.body(gSon.toJson(postGson)).post("/posts");

        response.prettyPrint();
        response.then().statusCode(equalTo(201));
        response.then().statusLine(containsStringIgnoringCase("201 Created"));
        response.then().body("userId", equalTo(1));
        response.then().body("id", equalTo(101));
        response.then().body("title", equalTo("post title to ..."));
        response.then().body("body", equalTo("post body to"));

    }
}
