package utils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import com.google.gson.JsonObject;
import com.aventstack.extentreports.ExtentTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Tasks_APIHelper {
	
	RequestSpecification request = RestAssured.given();
	TestData testdata = new TestData();
	Helper helper = new Helper();
	ExtentTest test;
	
    public Tasks_APIHelper(ExtentTest test) {
    	this.test = test;
	}
    
    public Response getWithAuthorizeRequest(String uri, String token) {
    	
    	Response response = null;
    	token = "Bearer "+ token;
    	try {
    		response = request.baseUri(uri)
    				.header("User-Agent", "PostmanRuntime/7.26.8")
    				.header("Authorization", token)
        			.header("Content-Type","application/json")
        			.get();
                    
        } catch(Exception e){
        	helper.writeLogs("error", "Can NOT send Get Authorize Request with URI: "+ e.getMessage(), test);
        }
    	return  response;
    }

    
    public Response getActiveTasks(String baseURI, String bearerToken) {
    	
    	testdata = testdata.returnTestData();
    	
    	String uri = baseURI + testdata.getTasks_subURI();
        
    	return getWithAuthorizeRequest(uri, bearerToken);
    }

    
    public void verifyTaskCreatedUsingAPI(String baseURI, String bearerToken, String taskName) {
        
    	Response response = getActiveTasks(baseURI, bearerToken);
        
    	try {
            JSONArray array = new JSONArray(response.body().asString());
            for (int i = 0; i < array.length(); i++) {
                JSONObject jObject = array.getJSONObject(i);
                String keyValue = jObject.get("content").toString();
                
                if (keyValue.equals(taskName)) {
                	helper.writeLogs("pass", "Task Created Successfully and Verified via API, Task name is >>"+ keyValue, test);
                	break;
                }
            }
        } catch (Exception e) {
        	helper.writeLogs("fail", "Task Creation Error >> "+ e.getMessage(), test);
		}
    	        
    }

    public String getIdBaseOnTaskName(String baseURI, String bearerToken, String taskName) {
        
    	Response response = getActiveTasks(baseURI, bearerToken);
        
    	String taskId = null;
        
    	try {
            JSONArray array = new JSONArray(response.body().asString());
            for (int i = 0; i < array.length(); i++) {
                JSONObject jObject = array.getJSONObject(i);
                String keyValue = jObject.get("content").toString();
                if (keyValue.equals(taskName)) {
                    taskId = jObject.get("id").toString();
                    helper.writeLogs("pass", "Get Id Base On Task Name, Task name is >>"+ taskName +" and taskId is >>"+ taskId, test);
                    break;
                
                }
            }
            
        } catch (Exception e) {
            helper.writeLogs("error", "Get Id Base On Task Name Error>> "+ e.getMessage(), test);
        }
        return taskId;    
    }

    
    public void reopenATaskUsingAPI(String baseURI, String bearerToken, String taskName, String taskId) {
        
    	testdata = testdata.returnTestData();
    	
        String updatedURI = baseURI + testdata.getTasks_subURI() + "/" + taskId + "/reopen";
        
    	JsonObject data = null;
        
    	Projects_APIHelper apiHelper= new Projects_APIHelper(test);
    	
        Response response = apiHelper.postWithAuthorizeRequest(updatedURI, data, bearerToken);
        
    	try {
    		
    		Assert.assertEquals(204, response.getStatusCode());
    		helper.writeLogs("pass", "Response code is 204 >>  Task Reopened Successfully, Task Name Is >>"+ taskName, test);
		
    	} catch (Exception e) {
			helper.writeLogs("error", "Task Reopen Response code is not 204 : "+ e.getMessage(), test);
		}
    	
    }

}
