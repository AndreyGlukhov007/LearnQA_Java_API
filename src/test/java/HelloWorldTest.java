import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HelloWorldTest {

    @Test
    public void testRestAssured() {
        Response response = RestAssured
                .given()
                .get("https://playground.learnqa.ru/api/homework_cookie")
                .andReturn();


        Map<String, String> responseCookies = response.getCookies();
        System.out.println(responseCookies);


        String responseCookie = response.getCookie("HomeWork");
        System.out.println(responseCookie);

        assertEquals("hw_value", responseCookie, "Cookie не совпадают!");

    }

}


