import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
//import static org.hamcrest.CoreMatchers.equalTo;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

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

	@Before
	public void setup() {
		RestUtil.setBaseURI("https://kokiri.i-ready.com");

	}

	@Test
	public void T01_test() {
		res = given().contentType(ContentType.JSON)
				.body("{ \"username\": \"DisAdmin\", \"password\": \"password\", \"state\": \"CO\" }\"}").when()
				.post("/login/oauth/user_token");
		json = RestUtil.getJsonPath(res);
		Assert.assertEquals(res.statusCode(), 200);
		Assert.assertThat(json.getString("token_type"), is("Bearer"));
		tokenType = json.getString("token_type");
		accessToken = json.getString("access_token");
		System.out.println(res.statusCode());
	}

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
	
	@Test
	public void T03_test(){
		RestUtil.setBasePath("");
		
	}

	@After
	public void tearDown() {
		RestUtil.resetBaseURI();
		RestUtil.resetBasePath();
	}


}
