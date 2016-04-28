import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
//import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;


public class Example1Test{

	private Response res = null;
	private JsonPath json;
	private String tokenType;
	private String accessToken;

	@Before
	public void setup() {
		RestUtil.setBaseURI("https://kokiri.i-ready.com");
		res = given().contentType(ContentType.JSON)
				.body("{ \"username\": \"DisAdmin\", \"password\": \"password\", \"state\": \"CO\" }\"}").when()
				.post("/login/oauth/user_token");
		json = RestUtil.getJsonPath(res);
		Assert.assertThat(json.getString("token_type"), is("Bearer"));
		tokenType = json.getString("token_type");
		accessToken = json.getString("access_token");
		System.out.println(res.statusCode());
	}

	@Test
	public void T01_test() {
		res = given()
				.header("Content_Type", "Application/json").and().header("Authorization", tokenType + " " + accessToken)
				.when()
				.get("/api/v1/schools");
		
		json = RestUtil.getJsonPath(res);
		
	}

	@After
	public void tearDown() {
		RestUtil.resetBaseURI();
		RestUtil.resetBasePath();
	}

	public JsonPath getJson() {
		return json;
	}

	public void setJson(JsonPath json) {
		this.json = json;
	}

}
