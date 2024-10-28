package tests;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.ApiCoreRequists;
import lib.Assertions;
import lib.BaseTestCase;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class UserGetTest extends BaseTestCase {

    private final ApiCoreRequists apiCoreRequists = new ApiCoreRequists();

    //первый тест когда пользователь ещё не авторизован. Если пользователь не авторизован, в ответ должен вернуться только логин пользователя.
    @Test
    public void testGetUserDataNotAuth(){
        Response responseUserData = RestAssured
                .get("https://playground.learnqa.ru/api/user/2") //в качестве id пользователя используем id с номером 2
                .andReturn();

        Assertions.assertJsonHasField(responseUserData, "username");
        Assertions.assertJsonHasNotField(responseUserData, "firstName");
        Assertions.assertJsonHasNotField(responseUserData, "lastName");
        Assertions.assertJsonHasNotField(responseUserData, "email");
        /*
        В этом тесте есть важный момент. Если пользователь не залогинился, то при обращении к id пользователя в ответ приходит только имя пользователя
        в таком виде - {"username":"Vitaliy"}
        Поэтому в этом тесте мы должны отразить не только что в ответ на запрос пришло соответствующее поле, но и то, что других полей в ответе нет.
        Для этого будет сделан новый метод assertJsonHasNotKey() в классе Assertions.
        При этом, если залогиниться, а затем обратиться к id пользователя, то в ответ должны все поля, а именно, поле с именем, фамилией, почтой и т.д.
         */
    }

    /*
    теперь напишем тест для авторизованного пользователя. Чтобы авторизоваться из-под пользователя с id 2, необходимо сделать запрос с существующими
    данными на email "vinkotov@example.com" и паролем "1234" (другими словами, нужно сначала авторизоваться). Затем в последующие запросы подставлять
    полученные куки (cookie) и хэдер (header).
     */
    @Test
    public void testGetUserDetailsAuthAsSameUser(){
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuth = RestAssured
                .given()
                .body(authData)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();

        String header = this.getHeader(responseGetAuth, "x-csrf-token");
        String cookie = this.getCookie(responseGetAuth, "auth_sid");

        Response responseUserData = RestAssured
                .given()
                .header("x-csrf-token", header)
                .cookie("auth_sid", cookie)
                .get("https://playground.learnqa.ru/api/user/2")
                .andReturn();

        String[] expectedFields = {"username", "firstName", "lastName", "email"};

        Assertions.assertJsonHasFields(responseUserData, expectedFields);
    }
        /*
        Мы хотим улучшить тест и сделать его чуть читаемым. В нашем классе напишем функция assertJsonHasKey().
        Она будет работать аналогичным образом assertJsonHasKey(), только на вход будет получать сразу все ключи
        в наличии которых должна будет убедиться.
         */

    // Ex16: Запрос данных другого пользователя
    @Test
    @Step("Another user's request")
    public void testEx16() {
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuth = apiCoreRequists.makePostRequistCreateUser("https://playground.learnqa.ru/api/user/login", authData);

        String header = this.getHeader(responseGetAuth, "x-csrf-token");
        String cookie = this.getCookie(responseGetAuth, "auth_sid");

        Response responseUserData = apiCoreRequists.makeGetRequist("https://playground.learnqa.ru/api/user/3", header, cookie);

        System.out.println(responseUserData.prettyPrint());

        Assertions.assertJsonHasField(responseUserData, "username");
        Assertions.assertJsonHasNotField(responseUserData, "firstName");
        Assertions.assertJsonHasNotField(responseUserData, "lastName");
        Assertions.assertJsonHasNotField(responseUserData, "email");

    }
}

