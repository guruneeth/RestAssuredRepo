package files;

import io.restassured.RestAssured;
import pojo.AddPlace;
import pojo.Location;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class serializeTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		AddPlace ap = new AddPlace();
		ap.setAccuracy(50);
		ap.setAddress("203 front st");
		ap.setLanguage("English");

		Location l = new Location();
		l.setLat(-38.39);
		l.setLng(33.44);
		ap.setLocation(l);

		ap.setPhone_number("(+91)82928373");
		ap.setName("Frontline House");
		ap.setWebsite("https://www.gg.com");
		List<String> myList = new ArrayList<String>();
		myList.add("show park");
		myList.add("shop");
		ap.setTypes(myList);

		RestAssured.baseURI = "https://rahulshettyacademy.com";

		String responseString = given().log().all().queryParam("key", "qaclick123").body(ap).when().log().all().post("/maps/api/place/add/json")
				.then().assertThat().statusCode(200).extract().response().asString();
		System.out.println(responseString);
	}

}
