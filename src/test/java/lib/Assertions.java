package lib;

import io.restassured.response.Response;

import static org.hamcrest.Matchers.hasKey;
import static org.junit.jupiter.api.Assertions.assertEquals;

/*
В тестах нам не нужно получать значения. Нам нужно просто убедиться что значение присутствует и это значение равняется ожидаемому значению.
В классе Assertions будут находиться функции которые будут расширять спектр проверок внутри тестов. Например, мы сделаем метод, который на вход
получает ответ от сервера, получает ожидаемое значение, сам из ответа парсил реальное значение и сравнивал их.
 */

public class Assertions {
    public static void assertJsonByName(Response response, String name, int expectedValue){
        response.then().assertThat().body("$", hasKey(name));

        int value = response.jsonPath().getInt(name);
        assertEquals(expectedValue, value, "JSON value is not equal to expected value");
    }
}
