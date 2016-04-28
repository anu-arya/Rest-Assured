import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import static com.jayway.restassured.RestAssured.*;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

public class RestUtil {
	
	public static String path;

	public static void setBaseURI(String baseURI){
		RestAssured.baseURI = baseURI;
	}

	public static void setBasePath(String basePathTerm){
		RestAssured.basePath = basePathTerm;
	}

	public static void resetBaseURI(){
		RestAssured.baseURI = null;
	}

	public static void resetBasePath(){
		RestAssured.basePath = null;
	}

	public static void setContentType(ContentType Type){
		given().contentType(Type);
	}
	

	public static Response postResponse(String postUrl){
		return post(postUrl);
	}
	
	 
	public static Response getResponse(){
		return get(path);
	}

	public static JsonPath getJsonPath(Response res){
		String json =  res.asString();
		System.out.println(json);
		return new JsonPath(json);
	}
	
	
	
}
