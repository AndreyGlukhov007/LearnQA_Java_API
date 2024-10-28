package lib;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiCoreRequists {
    /*
    Пока что будем работать с методами из класса UserAuthTest. В этом классе мы делаем 1 post-запрос и 3 разных get-запроса.
    Один get-запрос получает заголовки и cooke, а вот 2-ва других что-то одно: либо заголовок, либо cookie.
    Создадим 4 таких метода в классе ApiCoreRequists, и сразу пропишем им соответствующее описание - Step.
     */
    @Step("Make a GET-request with token and cookie") //в кавычках описание
    public Response makeGetRequist(String url, String token, String cookie){
        return given()
                .filter(new AllureRestAssured())
                /*
                Chat GPT:
                Метод filter() в библиотеке RestAssured используется для добавления фильтров к запросам или ответам, которые обрабатываются в процессе тестирования HTTP.
                Фильтры позволяют выполнять различные действия, такие как логирование, модификация запросов и ответов, обработка ошибок и т.д.

                Создание объекта new AllureRestAssured() внутри метода filter() интегрирует библиотеку Allure с RestAssured. Allure — это инструмент для генерации отчетов о тестировании,
                который позволяет визуализировать результаты тестов, включая информацию о запросах и ответах.

                Когда вы добавляете AllureRestAssured в качестве фильтра, он автоматически захватывает информацию о каждом запросе и ответе, отправленных через RestAssured,
                и сохраняет эти данные в отчете Allure. Это позволяет вам затем удобно просматривать данные, такие как URL, заголовки, тело запроса и ответа,
                статус-коды и другую информацию, что может быть полезно при анализе результатов выполнения тестов.
                 */
                .header(new Header("x-csrf-token", token))
                .cookie("auth_sid", cookie)
                .get(url)
                .andReturn();
    }

    @Step("Make a GET-request with auth cookie only") //в кавычках описание
    public Response makeGetRequistWithCookie(String url, String cookie){
        return given()
                .filter(new AllureRestAssured())
                .cookie("auth_sid", cookie)
                .get(url)
                .andReturn();
    }

    @Step("Make a GET-request with token only") //в кавычках описание
    public Response makeGetRequistWithToken(String url, String token){
        return given()
                .filter(new AllureRestAssured())
                .header(new Header("x-csrf-token", token))
                .get(url)
                .andReturn();
    }

    @Step("Make a POST-request") //в кавычках описание
    public Response makePostRequist(String url, Map<String, String> authData){
        return given()
                .filter(new AllureRestAssured())
                .body(authData)
                .post(url)
                .andReturn();
    }
    //после того как методы написаны, перепишем вызовы в классе UserAuthTest

    //запросы для задания Ex15: Тесты на метод user
    @Step("Make a POST-requist create user on incorrect email")
    public Response makePostRequistCreateUser(String url, Map<String, String> createData){
        return given()
                .filter(new AllureRestAssured())
                .body(createData)
                .post(url)
                .andReturn();
    }
}
