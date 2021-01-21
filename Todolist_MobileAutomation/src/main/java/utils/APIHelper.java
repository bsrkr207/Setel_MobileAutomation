package utils;

import org.testng.Assert;

import com.google.gson.JsonObject;
import com.aventstack.extentreports.ExtentTest;
import com.google.gson.JsonArray;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class APIHelper {
	
	RequestSpecification request = RestAssured.given();
	Helper helper = new Helper();
	private String projects_subURI = "/rest/v1/projects";
	private String tasks_subURI = "/rest/v1/tasks";
    ExtentTest test;
	
    public APIHelper(ExtentTest test) {
    	this.test = test;
	}
    
	public Response PostWithAuthorizeRequest(String uri, JsonObject data, String token) {
        
		Response response = null;
        
		try {
            if(data != null) {
            	
            	request.body(String.valueOf(data));
            	response = request.baseUri(uri)
            	.header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
        		.post();
            	
            } else {

            	response = request.baseUri(uri)
            	.header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
        		.post();
            }
            
        } catch(Exception e){
        	helper.WriteLogs("error", "Can NOT send Post Json & Authorize Request with URI: "+ e.getMessage(), test);
        }
        return  response;
    }
	
    public Response GetWithAuthorizeRequest(String uri, String token) {
        
    	Response response = null;
        
        try {
        	response = request.baseUri(uri)
        	.header("Authorization", "Bearer " + token)
            .header("Content-Type", "application/json")
    		.post();
     
        } catch(Exception e){
        	helper.WriteLogs("error", "Can NOT send Get Authorize Request with URI: "+ e.getMessage(), test);
        }
        return  response;
    }

    
    public void CreateProjectSuccessfully(String baseURI, String projectName, String bearerToken) {
        
    	String uri = baseURI + projects_subURI;
        
    	JsonObject data = new JsonObject();
        
    	data.addProperty("name", projectName);
        
    	Response response = PostWithAuthorizeRequest(uri, data, bearerToken);
        
    	try {
		
    		Assert.assertEquals(200, response.getStatusCode());
    		helper.WriteLogs("pass", "Response code is 200 >>  Project Created Successfully, Project name is >>"+ projectName, test);
		
    	} catch (Exception e) {
			helper.WriteLogs("error", "Create Project Response code is not 200 : "+ e.getMessage(), test);
		}
    	
    }

    
    
    public Response GetActiveTasks(String baseURI, String bearerToken) {
        
    	String uri = baseURI + tasks_subURI;
        
    	return GetWithAuthorizeRequest(uri, bearerToken);
    }

    public void VerifyTaskHasBeenCreatedSuccessfully(String baseURI, String bearerToken, String taskName) {
        
    	Response response = GetActiveTasks(baseURI, bearerToken);
        
    	boolean result = false;
        
    	try {
            JsonArray array = new JsonArray();
            
            array.add(response.body().asString());
            
            for (int i = 0; i < array.size(); i++) {
                
            	JsonObject jObject = array.getAsJsonObject();
                
            	String keyValue = jObject.get("content").toString();
                
            	if (keyValue.equals(taskName)) {
                    
            		result = true;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertTrue(result);
    }

    public String GetIdBaseOnTaskName(String baseURI, String bearerToken, String taskName) {
        
    	Response response = GetActiveTasks(baseURI, bearerToken);
        
    	String taskId = null;
        
    	try {
    		JsonArray array = new JsonArray();
            
    		array.add(response.body().asString());
    		
            for (int i = 0; i < array.size(); i++) {
                
            	JsonObject jObject = array.getAsJsonObject();
                
            	String keyValue = jObject.get("content").toString();
                
            	if (keyValue.equals(taskName)) {
                    
            		taskId = jObject.get("id").toString();
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return taskId;
    }

    public void ReopenATaskSuccessfully(String baseURI, String bearerToken, String taskName){
        
    	String taskId = GetIdBaseOnTaskName(baseURI, bearerToken, taskName);
        
    	String updatedURI = baseURI + tasks_subURI + "/" + taskId + "/reopen";
        
    	JsonObject data = null;
        
    	Response response = PostWithAuthorizeRequest(updatedURI, data, bearerToken);
        
    	try {
    		
    		Assert.assertEquals(200, response.getStatusCode());
    		helper.WriteLogs("pass", "Response code is 200 >>  Task Reopened Successfully, Task Name Is >>"+ taskName, test);
		
    	} catch (Exception e) {
			helper.WriteLogs("error", "Task Reopen Response code is not 200 : "+ e.getMessage(), test);
		}
    	
    }



}
