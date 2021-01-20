package utils;

import org.testng.Assert;

import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class APIHelper {
	
	RequestSpecification request = RestAssured.given();
	private String projects_subURI = "/rest/v1/projects";
	private String tasks_subURI = "/rest/v1/tasks";
    
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
            //logger.error("Can NOT send Post Json & Authorize Request with URI: " + e);
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
            //logger.error("Can NOT send Get Authorize Request with URI: " + e);
        }
        return  response;
    }

    
    public void createProjectSuccessfully(String baseURI, String projectName, String bearerToken) {
        
    	String uri = baseURI + projects_subURI;
        
    	JsonObject data = new JsonObject();
        
    	data.addProperty("name", projectName);
        
    	Response response = PostWithAuthorizeRequest(uri, data, bearerToken);
        
    	Assert.assertEquals(200, response.getStatusCode());
    }

    
    
    public Response getActiveTasks(String baseURI, String bearerToken) {
        
    	String uri = baseURI + tasks_subURI;
        
    	return GetWithAuthorizeRequest(uri, bearerToken);
    }

    public void verifyTaskHasBeenCreatedSuccessfully(String baseURI, String bearerToken, String taskName) {
        
    	Response response = getActiveTasks(baseURI, bearerToken);
        
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

    public String getIdBaseOnTaskName(String baseURI, String bearerToken, String taskName) {
        
    	Response response = getActiveTasks(baseURI, bearerToken);
        
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

    public void reopenATaskSuccessfully(String baseURI, String bearerToken, String taskName){
        
    	String taskId = getIdBaseOnTaskName(baseURI, bearerToken, taskName);
        
    	String updatedURI = baseURI + tasks_subURI + "/" + taskId + "/reopen";
        
    	JsonObject data = null;
        
    	Response response = PostWithAuthorizeRequest(updatedURI, data, bearerToken);
        
    	Assert.assertEquals(200, response.getStatusCode());
    	
    	
    }



}
