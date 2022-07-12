package RestAssured;

import com.google.gson.Gson;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
public class PutMethod {

    public static void main(String[] args) {
        String baseUri = "https://jsonplaceholder.typicode.com";

        RequestSpecification request = given();
        request.baseUri(baseUri);

//        GsonContain postGson = new GsonContain();
//        postGson.setId(1);
//        postGson.setUserId(1);
//        postGson.setTitle("day la title ne");
//        postGson.setBody("day la body ne");

        // constructor body
        GsonContain postGson1 = new GsonContain(1, 1, "title1", "body1");
        GsonContain postGson2 = new GsonContain(1, 2, "title2", "body2");
        GsonContain postGson3 = new GsonContain(1, 3, "title3", "body3");
        GsonContain postGson4 = new GsonContain(1, 4, "title4", "body4");

        List<GsonContain> postGsons = Arrays.asList(postGson1, postGson2, postGson3, postGson4);

        for (GsonContain postGson : postGsons) {

            Gson gson = new Gson();
            request.header(RequestCapability.defaultHeader);

            String postGsonStr = gson.toJson(postGson);
            final int TARGET_NUM_POST = 1;
            Response response = request.body(postGsonStr).put("/posts/" + String.valueOf(postGson.getId()));
            response.prettyPrint();
            response.then().body("userId", equalTo(postGson.getUserId()));
            response.then().body("id", equalTo(postGson.getId()));
            response.then().body("title", equalTo(postGson.getTitle()));
            response.then().body("body", equalTo(postGson.getBody()));
        }

    }
}
