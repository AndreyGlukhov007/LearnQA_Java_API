package tests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lib.Assertions;
import lib.BaseTestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import lib.ApiCoreRequists;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;

@Epic("Authorisation cases")
/*
Тэг @Epic означает что последующие тесты принадлежат большой единой общей части. Это тесты на авторизационные кейсы.
Allure группирует тест по таким эпикам.
 */
@Feature("Authorization")
/*
Тэг @Epic описывает название фичи которые наши тесты покрывают. Это тоже удобный способ гурппировки тестов, но уже
по фичам.
Можно использовать что-то одно, либо @Epic, либо @Feature. Но порой бывает удобно использовать их вместе.
 */
public class UserAuthTest extends BaseTestCase {
    String cookie;
    String header;
    int userIdOnAuth;
    private final ApiCoreRequists apiCoreRequists = new ApiCoreRequists();

    @BeforeEach
    public void loginUser(){
        Map<String, String> authDate = new HashMap<>();
        authDate.put("email", "vinkotov@example.com");
        authDate.put("password", "1234");

        Response responseGetAuth = apiCoreRequists
                .makePostRequist("https://playground.learnqa.ru/api/user/login", authDate);

        this.cookie = this.getCookie(responseGetAuth, "auth_sid");
        this.header = this.getHeader(responseGetAuth, "x-csrf-token");
        this.userIdOnAuth = this.getIntFromJson(responseGetAuth, "user_id");
    }

    @Test
    @Description("This test successfully authorize user by email and password") //Это тэг от Allure
    /*
    Описание в тэке @Description для каждого отдельного теста нужно для того чтобы в отчёте было понятно что именно
    тест проверяет. В названии теста не всегда получается отобразить подробную информацию.
     */
    @DisplayName("Test positive auth user") //Это тэг от Allure
    /*
    В тэге @DisplayName указываем то, КАК мы хотим видеть название теста в нашем отчёте. Также в нашем Allure-отчёте хотелось
    бы видеть то, какие запросы мы делаем во время прохождения нашего теста. Для этого мы можем пользоваться тэком @Step,
    но вопрос, где именно его поставить. Сейчас мы делаем запросы прямо из тестов, и получается что тэг тоже придётся проставлять
    прямо посреди теста. А это существенно ухудшит читаемость тестов.
    Вместо этого автор предлагает вынести запросы из тестов в отдельные методы, которые будут сгруппированы в отдельном классе.
    Класс назовём ApiCoreRequists. И этим методам проставить тэк @Step.
     */
    public void testAuthUser(){
        Response responseCheckAuth = apiCoreRequists
                .makeGetRequist(
                        "https://playground.learnqa.ru/api/user/auth",
                        this.header,
                        this.cookie
                );

        Assertions.assertJsonByName(responseCheckAuth, "user_id", this.userIdOnAuth);
    }

    @Description("This test checks authorization status w/o sending auth cookie or token") //Это тэк от Allure
    @DisplayName("Test negative auth user") //Это тэг от Allure
    @ParameterizedTest
    @ValueSource(strings = {"cookie", "headers"}) // я не помню от чего этот тэг
    public void testNegativeAuthUser(String condition) throws IllegalAccessException {
        if (condition.equals("cookie")){
            Response responseForCheck = apiCoreRequists.makeGetRequistWithCookie(
                    "https://playground.learnqa.ru/api/user/auth",
                        this.cookie
            );
            Assertions.assertJsonByName(responseForCheck, "user_id", 0);
        } else if (condition.equals("headers")){
            Response responseForCheck = apiCoreRequists.makeGetRequistWithToken(
                    "https://playground.learnqa.ru/api/user/auth",
                    this.header
            );
            Assertions.assertJsonByName(responseForCheck,"user_id", 0);
        } else {
            throw  new IllegalArgumentException("Condition value is not know: " + condition);
        }
    }
}
