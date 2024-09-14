import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class HelloWorldTest {

    @Test
    public void testRestAssured(){

        int numberIteration = 1;
        String locationHeader = "https://playground.learnqa.ru/api/long_redirect";
        int statusCode;

        for (;;) {
            Response response = RestAssured
                    .given()
                    .redirects().follow(false)
                    .when()
                    .get(locationHeader)
                    .andReturn();


            statusCode = response.getStatusCode();
            if (statusCode != 200){
                locationHeader = response.getHeader("Location");
                numberIteration++;
            } else {
                break;
            }
        }
        System.out.println("Redirection " + numberIteration);
        System.out.println("Endpoint " + locationHeader);
    }

}
