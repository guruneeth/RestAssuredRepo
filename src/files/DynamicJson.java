package files;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class DynamicJson {

	@Test(dataProvider = "BooksData")
	public void addBook(String isbn, String aisle) {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().header("Content-Type", "application/json")
				.body(Payloads.AddBook(isbn, aisle)).when().post("/Library/Addbook.php").then().log().all()
				.assertThat().statusCode(200).extract().response().asString();

		JsonPath js = reusableMethods.rawToJson(response);
		String id = js.getString("ID");
		System.out.println(id);
	}

	@DataProvider(name = "BooksData")
	public Object[][] getData() {

		return new Object[][] { { "asda", "32434" }, { "sdfsf", "234234" }, { "rwteb", "423452" } };
	}

}
