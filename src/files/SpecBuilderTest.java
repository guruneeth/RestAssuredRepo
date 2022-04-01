package files;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class SpecBuilderTest {

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

		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addQueryParam("key", "qaclick123").setContentType(ContentType.JSON).build();

		ResponseSpecification res = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON)
				.build();

		//RestAssured.baseURI = "https://rahulshettyacademy.com";

		RequestSpecification request = given().spec(req).body(ap);
		String responseString=request.when().log().all().post("/maps/api/place/add/json").then().spec(res).extract()
				.response().asString();
		System.out.println(responseString);
	}

}
