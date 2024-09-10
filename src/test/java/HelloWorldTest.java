import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class HelloWorldTest {

    @Test
    public void testRestAssured(){
        /*
        Map<String, String> params = new HashMap<>();
        params.put("message", "And this is a second message");
        params.put("timestamp", "2021-06-04 16:41:51");
         */

        JsonPath response = RestAssured
                .given()
                //.queryParams(params)
                .get("https://playground.learnqa.ru/api/get_json_homework")
                .jsonPath();
        response.prettyPrint();

        String answer = response.get("messages[1].\"message\"");
        System.out.println(answer);
    }

}
