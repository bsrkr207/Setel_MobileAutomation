package testCases;

import java.sql.Timestamp;

import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import io.appium.java_client.AppiumDriver;
import screens.AddItemsScreen;
import screens.LoginScreen;
import screens.NavigationBar;
import screens.SearchingScreen;
import utils.Projects_APIHelper;
import utils.Tasks_APIHelper;
import utils.Helper;
import utils.TestData;

public class Todoist_TestCases {

	static ExtentTest test;
	private ExtentReports report;
	
	private AppiumDriver<?> driver;
    private Projects_APIHelper projectsAPIHelper;
    private Tasks_APIHelper tasksAPIHelper;
    private Helper helper = new Helper();
    private TestData testdata = new TestData();
    LoginScreen loginScreen; 
    NavigationBar navigationBar; 
    SearchingScreen searchingScreen;
    AddItemsScreen addItemsScreen;
    private static String projectName;
    private String taskName;
    String id = new Timestamp(System.currentTimeMillis()).toString();
    
    
    @BeforeSuite
    public void setUp() throws Exception {
        
    	testdata = testdata.returnTestData();
    	
    	driver = helper.returnDriver(testdata.getPlatform());
        
    	report = helper.startExtentReport(new Exception().getStackTrace()[0].getClassName());
    	
    }

    @Test(priority=1)
    public void VerifyTheProjectCreatedSuccessfullyViaAPIOnMobile() {
        
    	test = report.createTest(new Exception().getStackTrace()[0].getMethodName());
    	
    	projectsAPIHelper = new Projects_APIHelper(test);
    	
    	loginScreen = new LoginScreen(driver, test);
    	navigationBar = new NavigationBar(driver);
    	searchingScreen = new SearchingScreen(driver, test);
        
        //create project by API
        projectName = "TestProjectSetel_" + id;

        projectsAPIHelper.createProjectUsingAPI(testdata.getBase_uri(), projectName, testdata.getBearer_token());
        
        //log in and verify on Mobile
        loginScreen.logintoToDoList(testdata.getUserID(), testdata.getPassword());
        
        searchingScreen.clickOnTimeSettings_Yes();
        navigationBar.clickOnMagnifyingGlassIcon();
        searchingScreen.clickOnProjectTag();
        searchingScreen.enterSearchingValue(projectName);
                
        String getSearchResult = searchingScreen.getResultProjectFirstItemValue();
        
        try {
        	Assert.assertEquals(getSearchResult, projectName);
        	
        	helper.writeLogs("pass", "<b>"+ projectName + " >> Project successfully created via API and verified on mobile </b>", test);
        	helper.writeLogsWithScreenshot("pass", projectName, test);
		} catch (Exception e) {
			
			helper.writeLogs("fail", e.getMessage(), test);
		}
        
    }
    
    
    @Test(priority=2)
    public void VerifyTheTaskCreatedSuccessfullyOnMobileByAPI() throws InterruptedException {
        
    	test = report.createTest(new Exception().getStackTrace()[0].getMethodName());
    	
    	tasksAPIHelper = new Tasks_APIHelper(test);
    	addItemsScreen = new AddItemsScreen(driver);
        searchingScreen = new SearchingScreen(driver, test);
        
    	if(projectName == null){
            
        	//create project by API
        	String testProjectName = "TestProjectSetel_" + id;
        	projectsAPIHelper.createProjectUsingAPI(testdata.getBase_uri(), testProjectName, testdata.getBearer_token());
            
            projectName = testProjectName;
        }
        String testTaskName = "TestTaskSetel_" + id;
        
        searchingScreen.clickOnResultFirstItem();
        addItemsScreen.clickOnAddItemButton();
        addItemsScreen.enterTaskName(testTaskName);
        addItemsScreen.clickOnAddtask_Enter();
        addItemsScreen.clickOnTaskFirstItem();
        taskName = testTaskName;
        helper.writeLogsWithScreenshot("pass", taskName +"_Created", test);
        Thread.sleep(5000);
        
        //verify the created task by API
        tasksAPIHelper.verifyTaskCreatedUsingAPI(testdata.getBase_uri(), testdata.getBearer_token(), testTaskName);

    }

    @Test(priority=3)
    public void VerifyTheTaskReopenedSuccessfullyViaAPIOnMobile() throws InterruptedException {
        
    	test = report.createTest(new Exception().getStackTrace()[0].getMethodName());
    	
    	tasksAPIHelper = new Tasks_APIHelper(test);
    	
    	String taskID = tasksAPIHelper.getIdBaseOnTaskName(testdata.getBase_uri(), testdata.getBearer_token(), taskName);
    	
    	//Close the task created in test case 2
    	addItemsScreen.closeTheTask(); 
    	helper.writeLogsWithScreenshot("pass", taskName +"_Closed", test);
    	
    	//Reopen closed task via API
    	tasksAPIHelper.reopenATaskUsingAPI(testdata.getBase_uri(), testdata.getBearer_token(), taskName, taskID);
    	Thread.sleep(5000);
    	
    	String getSearchResult = addItemsScreen.GetResultTaskFirstItemValue();
         
         try {
         	Assert.assertEquals(getSearchResult, taskName);
         	
         	helper.writeLogs("pass", "<b>"+ taskName + " >> Task successfully reopend via API and verified on the mobile </b>", test);
        	helper.writeLogsWithScreenshot("pass", taskName, test);
 		} catch (Exception e) {
 			
 			helper.writeLogs("fail", "Reopened Task not shown in the project", test);
 		}
    	
    }
    
    @AfterSuite
    public void tearDown() throws Exception {
    	report.flush();
    	driver.quit();
        
    }
}
