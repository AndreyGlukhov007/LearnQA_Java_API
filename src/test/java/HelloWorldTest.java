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

        String variableString = "testing1233243243243543543543534"; //строка, в которой будут сравниваться количество символов

        //первый способ
        assertTrue(variableString.length()>=15, "Количество символов меньше 15");

        //второй способ
        assertEquals(true, variableString.length()>=15, "Количество символов меньше 15");


    }

}


