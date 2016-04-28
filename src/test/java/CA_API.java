

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not; 
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import com.jayway.restassured.path.json.JsonPath;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import static com.jayway.restassured.RestAssured.given;

import java.util.List;

import org.testng.annotations.Test;


public class CA_API {

	String tokenType;
	String accessToken;
	Response r;
	JsonPath jp;
	
	
	@Test(priority=1)
	public void simpleExample(){

		RestAssured.baseURI  = "https://lakitu.i-ready.com";	
		r = given()
				.contentType("application/json")
				.body("{ \"username\": \"DisAdmin\", \"password\": \"password\", \"state\": \"CO\" }\"}")
				.when()
				.post("/login/oauth/user_token");

		String jsonbody = r.getBody().asString();
		System.out.println(jsonbody);		    	
		

		String json = r.asString();
		jp = new JsonPath(json);
		accessToken = jp.get("access_token");
		tokenType = jp.get("token_type");
		System.out.println(accessToken);
		System.out.println(tokenType);
		
	}
	
	@Test(priority=2)
	public void getAccounts(){
		RestAssured.baseURI  = "https://lakitu.i-ready.com";	

		r = given().contentType("application/json").with().header("Authorization", tokenType +  " " +accessToken)
				.header("Accept", "Application/json")
				.log().all().when().get("/api/v1/accounts");
		String json = r.asString();
		jp = new JsonPath(json);
		System.out.println(json);
		
 //   	given().contentType()expect().statusCode(200).when().get("/api/v1/accounts");

//	expect().body("token_type",  equalTo("Bearer")).when().get("https://lakitu.i-ready.com/login/oauth/user_token");

	}


}
