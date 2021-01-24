package utils;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.Gson;

public class TestData {

	private String userID;
	private String password;
	private String platform;
	private String ip;
	private String port; 
	private String hybrid; 
	private String platform_ios_xcode8;
	private String platform_ios_version; 
	private String device_ios_name; 
	private String device_android_name; 
	private String platform_android_version; 
	private String app_ios_path; 
	private String app_android_path; 
	private String appPackage; 
	private String appActivity; 
	private String base_uri;
	private String projects_subURI;
	private String tasks_subURI;
	private String bearer_token;
	
	public String getUserID() {
		return userID;
	}
	
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getHybrid() {
		return hybrid;
	}

	public void setHybrid(String hybrid) {
		this.hybrid = hybrid;
	}

	public String getPlatform_ios_xcode8() {
		return platform_ios_xcode8;
	}

	public void setPlatform_ios_xcode8(String platform_ios_xcode8) {
		this.platform_ios_xcode8 = platform_ios_xcode8;
	}

	public String getPlatform_ios_version() {
		return platform_ios_version;
	}

	public void setPlatform_ios_version(String platform_ios_version) {
		this.platform_ios_version = platform_ios_version;
	}

	public String getDevice_ios_name() {
		return device_ios_name;
	}

	public void setDevice_ios_name(String device_ios_name) {
		this.device_ios_name = device_ios_name;
	}

	public String getDevice_android_name() {
		return device_android_name;
	}

	public void setDevice_android_name(String device_android_name) {
		this.device_android_name = device_android_name;
	}

	public String getPlatform_android_version() {
		return platform_android_version;
	}

	public void setPlatform_android_version(String platform_android_version) {
		this.platform_android_version = platform_android_version;
	}

	public String getApp_ios_path() {
		return app_ios_path;
	}

	public void setApp_ios_path(String app_ios_path) {
		this.app_ios_path = app_ios_path;
	}

	public String getApp_android_path() {
		return app_android_path;
	}

	public void setApp_android_path(String app_android_path) {
		this.app_android_path = app_android_path;
	}

	public String getAppPackage() {
		return appPackage;
	}

	public void setAppPackage(String appPackage) {
		this.appPackage = appPackage;
	}

	public String getAppActivity() {
		return appActivity;
	}

	public void setAppActivity(String appActivity) {
		this.appActivity = appActivity;
	}

	public String getBase_uri() {
		return base_uri;
	}

	public void setBase_uri(String base_uri) {
		this.base_uri = base_uri;
	}

	public String getBearer_token() {
		return bearer_token;
	}

	public void setBearer_token(String bearer_token) {
		this.bearer_token = bearer_token;
	}
	
	
	
	
	
	public TestData returnTestData() {
		
		TestData testdata = null;
		
		try {
			
		    // create Gson instance
		    Gson gson = new Gson();

		    // create a reader
		    Reader reader = Files.newBufferedReader(Paths.get("TestData.Json"));

		    // convert JSON string to User object
		    testdata = gson.fromJson(reader, TestData.class);

		    // close reader
		    reader.close();

		} catch (Exception ex) {
		    ex.printStackTrace();
		}
		return testdata;
	}

	public String getProjects_subURI() {
		return projects_subURI;
	}

	public void setProjects_subURI(String projects_subURI) {
		this.projects_subURI = projects_subURI;
	}

	public String getTasks_subURI() {
		return tasks_subURI;
	}

	public void setTasks_subURI(String tasks_subURI) {
		this.tasks_subURI = tasks_subURI;
	}
	

}
