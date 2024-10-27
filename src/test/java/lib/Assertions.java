package lib;

import io.restassured.response.Response;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;

/*
В тестах нам не нужно получать значения. Нам нужно просто убедиться что значение присутствует и это значение равняется ожидаемому значению.
В классе Assertions будут находиться функции которые будут расширять спектр проверок внутри тестов. Например, мы сделаем метод (к примеру, метод assertJsonByName), который на вход
получает ответ от сервера, получает ожидаемое значение, сам из ответа парсил реальное значение и сравнивал их.
 */

public class Assertions {
    //этот метод проверяет значение int в Json
    public static void assertJsonByName(Response response, String name, int expectedValue){
        response.then().assertThat().body("$", hasKey(name));

        int value = response.jsonPath().getInt(name);
        assertEquals(expectedValue, value, "JSON value is not equal to expected value");
    }
    //этот метод проверяет значение String в Json
    public static void assertJsonByName(Response response, String name, String expectedValue){
        response.then().assertThat().body("$", hasKey(name));

        String value = response.jsonPath().getString(name);
        assertEquals(expectedValue, value, "JSON value is not equal to expected value");
    }

    public static void assertResponseTextEquals(Response response, String expectedAnswer){
        assertEquals(
                expectedAnswer,
                response.asString(),
                "Response text is not as expected"
        );
    }

    public static void assertResponseCodeEquals(Response response, int expectedStatusCode){
        assertEquals(
                expectedStatusCode,
                response.statusCode(),
                "Response status code is not as expected"
        );
    }

    public static void assertJsonHasField(Response response, String expectedFieldName){
        response.then().assertThat().body("$", hasKey(expectedFieldName));
    }

    /*
    Метод assertJsonHasFields работает аналогично методу assertJsonHasField, только тут проверяет все поля через цикл for. И на вход этот метод принимает
    именно массив со списком полей. А внутри цикла для каждого поля вызывается метод assertJsonHasField.
     */
    public static void assertJsonHasFields(Response response, String[] expectedFieldNames){
        for(String expectedFieldName : expectedFieldNames){
            Assertions.assertJsonHasField(response, expectedFieldName);
        }
    }

    /*
    Метод, проверяющий в ответе отсутствие определённых поля (это будут поля "username", "firstName", "lastName", "email")
    Конкретно, этот метод нужен для неавторизованного пользователя.
     */
    public static void assertJsonHasNotField(Response response, String unexpectedFieldNamme){
        response.then().assertThat().body("$", not(hasKey(unexpectedFieldNamme)));
    }



}
