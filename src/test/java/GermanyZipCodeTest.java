import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GermanyZipCodeTest {

    @Test
    public void requestBerlinZipCode10627(){
        given().when().get("http://zippopotam.us/de/10627")
                .then().assertThat()
                .body("places[0].'place name'", equalTo("Berlin"))
                .body("places[0].state", equalTo("Berlin"));
    }
}
