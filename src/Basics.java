import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.testng.Assert;

import files.Payloads;
import files.reusableMethods;

public class Basics {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		// given: all input data
		// when: submit the API
		// then: validate the response

		// Validate Add place API
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String Response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(new String(Files.readAllBytes(Paths.get("/Users/guruneethsavant/Documents/Untitled.json")))).when().post("maps/api/place/add/json").then().assertThat().statusCode(200)
				.body("scope", equalTo("APP")).header("Server", "Apache/2.4.18 (Ubuntu)").extract().response()
				.asString();

		System.out.println(Response);

		JsonPath js = reusableMethods.rawToJson(Response);// for parsing json
		String placeid = js.getString("place_id");
		System.out.println("Place id is " + placeid);

		// Update place

		String newAddress = "203 front st, USA";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body("{\n" + "\"place_id\":\"" + placeid + "\",\n" + "\"address\":\"" + newAddress + "\",\n"
						+ "\"key\":\"qaclick123\"\n" + "}\n" + "")
				.when().put("/maps/api/place/update/json").then().log().all().assertThat().statusCode(200)
				.body("msg", equalTo("Address successfully updated"));

		// get place
		String addressResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeid)
				.when().get("maps/api/place/get/json").then().log().all().assertThat().statusCode(200).extract()
				.response().asString();

		JsonPath jss = reusableMethods.rawToJson(addressResponse);
		String address = jss.getString("address");
		System.out.println(address);

		Assert.assertEquals(address, newAddress);
		
	}

}