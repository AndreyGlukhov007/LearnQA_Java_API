import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import java.lang.Thread;
import static org.hamcrest.Matchers.*;

public class HelloWorldTest {

    @Test
    public void testRestAssured() throws InterruptedException {
            JsonPath responseOne = RestAssured
                    .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                    .jsonPath();

            //responseOne.prettyPrint(); - этот метод можно удалить. Он нужен был для отлаживания.

            String token = responseOne.get("token");
            int time = responseOne.get("seconds");

            JsonPath responseTwo = RestAssured
                    .given()
                    .queryParam("token",token)
                    .when()
                    .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                    .jsonPath();

            //responseTwo.prettyPrint(); - этот метод можно удалить. Он нужен был для отлаживания.

            String statusResponseOne = responseTwo.get("status");
            if (statusResponseOne.equals("Job is NOT ready")){
                System.out.println("Отлично. Метод API продолжает работать. Ждём когда завершится метод.");
            } else {
                System.out.println("Что-то пошло не так. Метод будет завершён");
                return;
            }

            Thread.sleep(time*1000); //Мы получаем из метода ответ в секундах. А функция sleep() отрабатывает в миллисекундах.
            //таким образом через умножение на 1000 мы переводим секунды в миллисекунды.

            JsonPath responseThree = RestAssured
                .given()
                .queryParam("token",token)
                .when()
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();

            //responseThree.prettyPrint(); - этот метод можно удалить. Он нужен был для отлаживания.

            String statusResponseTwo = responseThree.get("status");
            String result = responseThree.get("result");
            if (statusResponseTwo.equals("Job is ready") & result != null){
                System.out.println("Отлично. Тестирование метода завершено.");
            } else {
                System.out.println("Что-то пошло не так. Метод будет завершён");
                return;
            }

            //responseThree.prettyPrint(); - этот метод можно удалить. Он нужен был для отлаживания.

    }

}


