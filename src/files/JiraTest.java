package files;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

import org.testng.Assert;

public class JiraTest {

	public static void main(String[] args) {

		RestAssured.baseURI = "http://localhost:8080";

		// login

		SessionFilter sf = new SessionFilter();

		given().relaxedHTTPSValidation().header("Content-Type", "application/json")
				.body("{\n" + "    \"username\": \"guruneet.savant\",\n" + "    \"password\": \"08051991niK!\"\n" + "}")
				.log().all().filter(sf).when().post("rest/auth/1/session").then().log().all().extract().response()
				.asString();

		// add comment
		
		String expectedComment="Hi, How are you?";
		String addCommentResponese = given().log().all().pathParam("key", "10003")
				.header("Content-Type", "application/json")
				.body("{\n" + "    \"body\": \""+expectedComment+"\",\n" + "    \"visibility\": {\n"
						+ "        \"type\": \"role\",\n" + "        \"value\": \"Administrators\"\n" + "    }\n" + "}")
				.filter(sf).when().post("/rest/api/2/issue/{key}/comment").then().log().all().assertThat()
				.statusCode(201).extract().response().asString();
		JsonPath js = new JsonPath(addCommentResponese);
		String commentID = js.getString("id");

		// add attachment
		given().log().all().pathParam("key", "10003").header("X-Atlassian-Token", "no-check")
				.header("Content-Type", "multipart/form-data").filter(sf)
				.multiPart("file", new File("/Users/guruneethsavant/eclipse-workspace/Demo Project/jira.txt")).when()
				.post("/rest/api/2/issue/{key}/attachments").then().assertThat().statusCode(200);

		// get issue
		String issueDetails = given().log().all().pathParam("key", "10003").queryParam("fields", "comment").filter(sf)
				.when().get("/rest/api/2/issue/{key}").then().log().all().assertThat().statusCode(200).extract()
				.response().asString();
		System.out.println(issueDetails);

		JsonPath js1 = new JsonPath(issueDetails);
		int size = js1.getInt("fields.comment.comments.size()");
		
		for(int i=0;i<size;i++)
		{
			String commentIDIssue=js1.get("fields.comment.comments["+i+"].id").toString();
			
		
			if(commentID.equalsIgnoreCase(commentIDIssue)) {
				String actualComment=js1.getString("fields.comment.comments["+i+"].body").toString();
				System.out.println(actualComment);
				Assert.assertEquals(actualComment, expectedComment);
			}
		}
		
	}
}