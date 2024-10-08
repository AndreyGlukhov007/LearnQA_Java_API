import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.opentest4j.AssertionFailedError;

import java.lang.Thread;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HelloWorldTest {

    @ParameterizedTest
    @CsvSource(value =
            {
             "\'Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30\', Mobile, No, Android",
             "\'Mozilla/5.0 (iPad; CPU OS 13_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) CriOS/91.0.4472.77 Mobile/15E148 Safari/604.1\', Mobile, Chrome, iOS",
             "\'Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)\', Googlebot, Unknown, Unknown",
             "\'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36 Edg/91.0.100.0\', Web, Chrome, No",
             "\'Mozilla/5.0 (iPad; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1\', Mobile, No, iPhone"
            }
    )
    public void testRestAssured (String name, String platform, String browser, String device) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("User-Agent", name);

        JsonPath response = RestAssured
                .given()
                .headers(queryParams)
                .get("https://playground.learnqa.ru/ajax/api/user_agent_check")
                .jsonPath();

        String actualPlatform = response.getString("platform");
        String actualBrowser = response.getString("browser");
        String actualDevice = response.getString("device");


        assertEquals(platform, actualPlatform, "Платформа не соответствует ожидаемой платформе " + platform);
        assertEquals(browser, actualBrowser, "Браузер не соответствует ожидаемому браузеру " + browser);
        assertEquals(device, actualDevice, "Девайс не соответствует ожидаемому девайсу " + device);

    }

}


