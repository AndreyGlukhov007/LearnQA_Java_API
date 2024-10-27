package lib;

import io.restassured.http.Headers;
import io.restassured.response.Response;

import java.util.Map;

import static org.hamcrest.Matchers.hasKey;
import static org.junit.jupiter.api.Assertions.assertTrue;

//в этот базовый класс выносятся тот код, который будет многократно использоваться в тестах
public class BaseTestCase {
    //метод для получения значения header по имени
    protected String getHeader(Response Response, String name){
        Headers headers = Response.getHeaders();

        assertTrue(headers.hasHeaderWithName(name), "Response doesn't have header with name " + name);
        return headers.getValue(name);
    }

    //метод для получения значения cookie по имени
    protected String getCookie (Response Response, String name){
        Map<String, String> cookies = Response.getCookies();

        assertTrue(cookies.containsKey(name), "Response doesn't have cookie with name "+ name);
        return cookies.get(name);
    }

    //получение json и получение данных из него
    protected int getIntFromJson(Response response, String name){
        /*необходимо убедиться что в нашем json есть нужное поле. Для этого напишем строку в котором используем функцию hasKey().
        Через знак $ указываем что ищем ключ в корне json. Если бы ключ был спрятан за вложенностями, путь нужно указать до нужного поля*/
        response.then().assertThat().body("$", hasKey(name));
        //эта строка будет выполнена только в том случае если нужное поле есть.
        return response.jsonPath().getInt(name);
    }

    /*
    1. then(): Этот метод используется для начала этапа проверки (assertion) в цепочке тестов. Он позволяет объявить, что мы собираемся выполнять проверки на ответ (response),
    который был получен после отправки HTTP-запроса.
    2. assertThat(): Этот метод обозначает переход к следующему этапу проверки. Он создает утверждение, используя Hamcrest матчер (matchers), что позволяет сравнивать фактические
    значения с ожидаемыми. Это ключевой элемент для построения выразительных и читаемых утверждений.
    3. body(): Этот метод используется для указания, что мы хотим проверять содержимое тела ответа. Он может быть дополнен различными матчер-методами для проверки конкретных значений,
    существования элементов и других характеристик содержимого.
    Таким образом, комбинация then().assertThat().body() обычно используется для начала процесса проверки, чтобы проверить содержание тела HTTP-ответа на соответствие определённым критериям.
     */

}
