package lib;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

//в этом классе будет создаваться дата и время, и эта дата и время будет прикрепляться к переменной.
public class DataGenerator {
    public static String getRandomEmail(){
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
        return "learnqa" + timestamp + "@example.com";
    }

    /*
    Здесь мы вынесли всю логику по созданию данных для регистрации пользователя, но добавили небольшую хитрость. Если вызвать метод без параметров, то нам вернутся дефолтные значения
    включая случайно созданный email. Но нам не всегда удобно будет использовать этот метод. Например, в тесте, где мы уже пытаемся создать пользователя с существующим email нам не
    нужно чтобы этот метод возвращал нам случайный email; нам нужно чтобы он отдал нам конкретный email. Для этого создан второй метод. В него можно передать hashMap.
    В этом hashMap будут те данные, которые мы хотим задать сами, а не получить дефолтные значения.
     */
    public static Map<String, String> getRegistrationData(){
        Map<String, String> data = new HashMap<>();
        data.put("email", DataGenerator.getRandomEmail());
        data.put("password", "123");
        data.put("username", "learnqa");
        data.put("firstName", "learnqa");
        data.put("lastName", "learnqa");

        return data;
    }

    public static Map<String, String> getRegistrationData(Map<String,String> nonDefaultValues){
        Map<String,String> defaultValues = DataGenerator.getRegistrationData();

        Map<String,String> userData = new HashMap<>();
        String[] keys = {"email", "password", "username", "firstName", "lastName"};
        for (String key : keys){
            if (nonDefaultValues.containsKey(key)){
                userData.put(key, nonDefaultValues.get(key));
            } else{
                userData.put(key, defaultValues.get(key));
            }
        }
        return userData;
    }
}
