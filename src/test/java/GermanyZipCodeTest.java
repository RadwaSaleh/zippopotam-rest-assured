import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GermanyZipCodeTest {
    private static RequestSpecification requestSpec;

    @BeforeClass
    public static void createRequestSpecification(){
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://zippopotam.us")
                .build();
    }

    @DataProvider
    public static Object[][] zipCodesAndPlaces(){
        return new Object[][] {
                {"de", "10627", "Berlin", "Berlin"},
                {"de", "40210", "Düsseldorf", "Nordrhein-Westfalen"},
                {"de", "80333", "München", "Bayern"},
        };
    }

    @Test(dataProvider = "zipCodesAndPlaces")
    public void verifyDifferentZipCodesPlaces(String countryCode, String zipCode, String expectedCity, String expectedState){
        given().spec(requestSpec)
                .pathParam("countryCode", countryCode).pathParam("zipCode", zipCode)
                .when().get("/{countryCode}/{zipCode}")
                .then().assertThat()
                .statusCode(200)
                .body("places[0].'place name'", equalTo(expectedCity))
                .body("places[0].state", equalTo(expectedState));
    }
}
