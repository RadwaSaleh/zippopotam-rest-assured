import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GermanyZipCodeTest {

    @DataProvider
    public static Object[][] zipCodesAndPlaces(){
        return new Object[][] {
                {"de", "10627", "Berlin", "Berlin"},
                {"de", "40210", "Düsseldorf", "Nordrhein-Westfalen"},
                {"de", "80333", "München", "Bayern"},
        };
    }

    @Test(dataProvider = "zipCodesAndPlaces")
    public void requestBerlinZipCode10627(String countryCode, String zipCode, String expectedCity, String expectedState){
        given().pathParam("countryCode", countryCode).pathParam("zipCode", zipCode)
                .when().get("http://zippopotam.us/{countryCode}/{zipCode}")
                .then().assertThat()
                .body("places[0].'place name'", equalTo(expectedCity))
                .body("places[0].state", equalTo(expectedState));
    }
}
