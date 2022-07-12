package RestAssured;

import com.google.gson.Gson;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class PatchMethod {
    public static void main(String[] args) {
        String baseUri = "https://jsonplaceholder.typicode.com";

        RequestSpecification request = given();
        request.baseUri(baseUri);
        request.header(RequestCapability.defaultHeader);

        GsonContain postGson = new GsonContain();
        postGson.setTitle("chi moi minh title");

        Gson gson = new Gson();

        final int TARGET_NUM_POST = 1;
        Response response = request.body(gson.toJson(postGson)).patch("/posts/" + String.valueOf(TARGET_NUM_POST));
        response.then().body("title", equalTo(postGson.getTitle()));
        response.prettyPrint();

    }
}
