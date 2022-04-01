package files;

import io.restassured.path.json.JsonPath;

public class reusableMethods {

	public static JsonPath rawToJson(String Response) {
		
		JsonPath js = new JsonPath(Response);
		return js;
		
	}
}
