import static com.jayway.restassured.RestAssured.given;
//import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

import net.minidev.json.JSONObject;

public class Example1Test {

	private static Response res = null;
	private static JsonPath json;
	private static String tokenType;
	private static String accessToken;
	private static String schoolAId;
	private static String schoolBId;
	private static String schoolALink;
	private static String schoolBLink;
	private static String onboardId;
	private static String elaProductId;
	private static String mathProductId;
	JSONObject jsonObj = new JSONObject();
	private static String jsonString;

	@Before
	public void setup() {
		RestUtil.setBaseURI("https://kokiri.i-ready.com");

	}

	
	// get authentication token
	@Test
	public void T01_test() {
		jsonObj.put("username", "DisAdmin");
		jsonObj.put("password", "password");
		jsonObj.put("state", "CO");
		
		jsonString = jsonObj.toString();
		res = given().contentType(ContentType.JSON)
				.body(jsonString).when()
				.post("/login/oauth/user_token");
		json = RestUtil.getJsonPath(res);
		Assert.assertEquals(res.statusCode(), 200);
		Assert.assertThat(json.getString("token_type"), is("Bearer"));
		tokenType = json.getString("token_type");
		accessToken = json.getString("access_token");
		System.out.println(res.statusCode());
	}

	
	//get all schools in the district
	@Test
	public void T02_test() {
		RestUtil.setBasePath("/api/v1/schools");
		res = given().header("Content_Type", "Application/json").and()
				.header("Authorization", tokenType + " " + accessToken).when().get();
		json = RestUtil.getJsonPath(res);
		Assert.assertEquals(res.statusCode(), 200);
		json = new JsonPath(res.asString());
		schoolAId = json.getString("data.items[0].id");
		schoolBId = json.getString("data.items[1].id");
		schoolALink = json.getString("data.items[0]._links.self");
		schoolBLink = json.getString("data.items[1]._links.self");
		onboardId = json.getString("status.internalAccountId");
		System.out.println(onboardId);
	}
	
	//get all licenses(products)for the district
	@Test
	public void T03_test(){
		RestUtil.setBasePath("/api/v1/products");
		res = given().header("Content_Type", "Application/json").and()
				.header("Authorization", tokenType + " " + accessToken).when().get();
		json = RestUtil.getJsonPath(res);
		Assert.assertEquals(res.statusCode(), 200);
		json = new JsonPath(res.asString());
		elaProductId = json.getString("data.items[0].id");		
	}

	@After
	public void tearDown() {
		RestUtil.resetBaseURI();
		RestUtil.resetBasePath();
	}


}
