package utils;

import org.testng.Assert;
import com.google.gson.JsonObject;
import com.aventstack.extentreports.ExtentTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Projects_APIHelper {
	
	RequestSpecification request = RestAssured.given();
	Helper helper = new Helper();
	TestData testdata = new TestData();
	ExtentTest test;
	
    public Projects_APIHelper(ExtentTest test) {
    	this.test = test;
	}
    
	public Response postWithAuthorizeRequest(String uri, JsonObject data, String token) {
        
		Response response = null;
		try {
            if(data != null) {
            	
            	request.body(String.valueOf(data));
            	response = request.baseUri(uri)
            			.contentType("application/json")
            			.header("Authorization", "Bearer "+token)
            			.post();
            	
            } else {

            	response = request.baseUri(uri)
            			.contentType("application/json")
            			.header("Authorization", "Bearer "+token)
            			.post();
            }
            
        } catch(Exception e){
        	helper.writeLogs("error", "Can NOT send Post Json & Authorize Request with URI: "+ e.getMessage(), test);
        }
        return  response;
    }
	
    public void createProjectUsingAPI(String baseURI, String projectName, String bearerToken) {
        
    	testdata = testdata.returnTestData();
    	
    	String uri = baseURI + testdata.getProjects_subURI();
        
    	JsonObject data = new JsonObject();
        
    	data.addProperty("name", projectName);
        
    	Response response = postWithAuthorizeRequest(uri, data, bearerToken);
        
    	try {
		
    		Assert.assertEquals(200, response.getStatusCode());
    		helper.writeLogs("pass", "Response code is 200 >>  Project Created Successfully, Project name is >>"+ projectName, test);
		
    	} catch (Exception e) {
			helper.writeLogs("error", "Create Project Response code is not 200 : "+ e.getMessage(), test);
		}
    	
    }
    
    
}
