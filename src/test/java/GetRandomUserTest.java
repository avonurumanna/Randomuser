import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.response.ValidatableResponse;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pojo.RandomUser;

import static org.apache.http.HttpStatus.SC_OK;

public class GetRandomUserTest {

    private RandomUser randomUser;
    private Client client;

    @BeforeEach
    public void setUp() {
        client = new Client();
    }

    @Test
    public void getRandomUserWithoutParametersTest() throws JsonProcessingException {
        ValidatableResponse response = client.getRandomUser();
        randomUser = client.deserialize(response);
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(response.statusCode(SC_OK));
        softly.assertThat(randomUser.getResults().size()).isOne();
        softly.assertThat(randomUser.getResults().get(0).getGender()).isNotNull();
        softly.assertThat(randomUser.getResults().get(0).getName()).isNotNull();
        softly.assertThat(randomUser.getResults().get(0).getLocation()).isNotNull();
        softly.assertThat(randomUser.getResults().get(0).getEmail()).isNotNull();
        softly.assertThat(randomUser.getResults().get(0).getLogin()).isNotNull();
        softly.assertThat(randomUser.getResults().get(0).getDob()).isNotNull();
        softly.assertThat(randomUser.getInfo().getSeed()).isNotNull();
        softly.assertThat(randomUser.getInfo().getResults()).isNotZero();
        softly.assertThat(randomUser.getInfo().getPage()).isNotZero();
        softly.assertThat(randomUser.getInfo().getVersion()).isNotNull();
        softly.assertAll();
    }

    @ParameterizedTest
    @ValueSource(strings = {"female", "male"})
    public void getRandomUserWithGenderParameterTest(String gender) throws JsonProcessingException {
        ValidatableResponse response = client.getRandomUser("gender", gender);
        randomUser = client.deserialize(response);
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(response.statusCode(SC_OK));
        softly.assertThat(randomUser.getResults().size()).isOne();
        softly.assertThat(randomUser.getResults().get(0).getGender()).isEqualTo(gender);
        softly.assertAll();
    }

    @ParameterizedTest
    @ValueSource(strings = {"AU", "BR", "CA"})
    public void getRandomUserWithNationalitiesParameterTest(String nationality) throws JsonProcessingException {
        ValidatableResponse response = client.getRandomUser("nat", nationality);
        randomUser = client.deserialize(response);
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(response.statusCode(SC_OK));
        softly.assertThat(randomUser.getResults().size()).isOne();
        softly.assertThat(randomUser.getResults().get(0).getNat()).isEqualTo(nationality);
        softly.assertAll();
    }

    @Test
    public void getRandomUserWithoutInfoTest() throws JsonProcessingException {
        ValidatableResponse response = client.getRandomUser("noinfo", "");
        randomUser = client.deserialize(response);
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(response.statusCode(SC_OK));
        softly.assertThat(randomUser.getResults().size()).isOne();
        softly.assertThat(randomUser.getInfo()).isNull();
        softly.assertAll();
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 4999, 5000})
    public void getRandomUserWithResultsParameterTest(int results) throws JsonProcessingException {
        ValidatableResponse response = client.getRandomUser("results", String.valueOf(results));
        randomUser = client.deserialize(response);
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(response.statusCode(SC_OK));
        softly.assertThat(randomUser.getResults().size()).isEqualTo(results);
        softly.assertThat(randomUser.getInfo().getResults()).isEqualTo(results);
        softly.assertAll();
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc", "foobar", "56d27f4a53bd5441"})
    public void getRandomUserWithSeedsParameterTest(String seed) throws JsonProcessingException {
        ValidatableResponse response = client.getRandomUser("seed", seed);
        randomUser = client.deserialize(response);
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(response.statusCode(SC_OK));
        softly.assertThat(randomUser.getResults().size()).isOne();
        softly.assertThat(randomUser.getInfo().getSeed()).isEqualTo(seed);
        softly.assertAll();
    }

    @ParameterizedTest
    @ValueSource(strings = {"1.3", "1.4"})
    public void getRandomUserWithVersionTest(String version) throws JsonProcessingException {
        ValidatableResponse response = client.getRandomUser(version);
        randomUser = client.deserialize(response);
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(response.statusCode(SC_OK));
        softly.assertThat(randomUser.getResults().size()).isOne();
        softly.assertThat(randomUser.getInfo().getVersion()).isEqualTo(version);
        softly.assertAll();
    }
}
