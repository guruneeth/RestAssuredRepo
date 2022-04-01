import files.Payloads;
import io.restassured.path.json.JsonPath;

public class ComplexJsonPars {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		JsonPath js = new JsonPath(Payloads.CoursePrice());

		// 1. Print No of courses returned by API

		int count = js.getInt("courses.size()");
		System.out.println(count);

//2.Print Purchase Amount
		int totalamount = js.getInt("dashboard.purchaseAmount");
		System.out.println(totalamount);

//3. Print Title of the first course

		String title = js.getString("courses[0].title");
		System.out.println(title);

//4. Print All course titles and their respective Prices

		for (int i = 0; i < count; i++) {
			String allTitle = js.getString("courses[" + i + "].title");
			String price = js.getString("courses[" + i + "].price");
			System.out.println("Price of " + allTitle + " is " + price);
		}

//5. Print no of copies sold by RPA Course
		for (int i = 0; i < count; i++) {
			String allTitle = js.getString("courses[" + i + "].title");
			if (allTitle.equalsIgnoreCase("RPA")) {
				System.out.println(js.getInt("courses[" + i + "].copies"));
				break;
			}
		}

//6. Verify if Sum of all Course prices matches with Purchase Amount		
	}

}
