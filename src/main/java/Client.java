import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import pojo.RandomUser;

import static io.restassured.RestAssured.given;

public class Client {
    public static final String RANDOM_USER_GENERATE_PATH = "api/";
    private static final String BASE_URL = "https://randomuser.me/";

    protected RequestSpecification getSpec() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(BASE_URL)
                .build();
    }

    public ValidatableResponse getRandomUser() {
        return given()
                .spec(getSpec())
                .when()
                .get(RANDOM_USER_GENERATE_PATH)
                .then();
    }

    public ValidatableResponse getRandomUser(String key, String value) {
        return given()
                .spec(getSpec())
                .queryParams(key, value)
                .when()
                .get(RANDOM_USER_GENERATE_PATH)
                .then();
    }

    public ValidatableResponse getRandomUser(String version) {
        return given()
                .spec(getSpec())
                .when()
                .get(RANDOM_USER_GENERATE_PATH + version + "/")
                .then();
    }

    public ValidatableResponse getRandomUser(String firstKey, String firstValue, String secondKey, String secondValue) {
        return given()
                .spec(getSpec())
                .queryParams(firstKey, firstValue, secondKey, secondValue)
                .when()
                .get(RANDOM_USER_GENERATE_PATH)
                .then();
    }

    public ValidatableResponse getRandomUser(String firstKey, String firstValue, String secondKey, String secondValue, String thirdKey, String thirdValue) {
        return given()
                .spec(getSpec())
                .queryParams(firstKey, firstValue, secondKey, secondValue, thirdKey, thirdValue)
                .when()
                .get(RANDOM_USER_GENERATE_PATH)
                .then();
    }

    public RandomUser deserialize(ValidatableResponse response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response.extract().body().asString(), RandomUser.class);
    }


}
