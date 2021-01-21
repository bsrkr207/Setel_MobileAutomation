package testCases;

import java.sql.Timestamp;

import org.testng.Assert;
import org.testng.ITestClass;
import org.testng.ITestMethodFinder;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import io.appium.java_client.AppiumDriver;
import pages.LoginPage;
import pages.NavigationBar;
import pages.SearchingPage;
import utils.APIHelper;
import utils.Helper;
import utils.TestData;

public class Todoist_TestCases {

	private ExtentTest test;
	private ExtentReports report;
	
	private AppiumDriver<?> driver;
    private APIHelper apiHelper;
    private Helper helper = new Helper();
    private TestData testdata = new TestData();
    private String projectName;
    private String taskName;

    
    @BeforeTest
    public void setUp() throws Exception {
        
    	testdata = testdata.ReturnTestData();
    	
    	driver = helper.returnDriver(testdata.getPlatform());
        
    	report = helper.startExtentReport(new Exception().getStackTrace()[0].getClassName());
    	
    }

    @Test
    public void VerifyTheProjectHasBeenCreatedSuccessfullyViaAPIOnMobile() {
        
    	test = report.createTest(new Exception().getStackTrace()[0].getMethodName());
    	
    	apiHelper = new APIHelper(test);
    	
    	LoginPage loginScreen = new LoginPage(driver, test);
        
        NavigationBar navigationBar = new NavigationBar(driver);
        
        SearchingPage searchingPage = new SearchingPage(driver, test);

        //create project by API
        String id = new Timestamp(System.currentTimeMillis()).toString();
        
        projectName = "TestSetel_" + id;

        apiHelper.CreateProjectSuccessfully(testdata.getBase_uri(), projectName, testdata.getBearer_token());
        
        //log in and verify on Mobile
        loginScreen.LogintoToDoList(testdata.getUserID(), testdata.getPassword());
        
        searchingPage.ClickOnTimeSettings_Yes();
        
        navigationBar.ClickOnMagnifyingGlassIcon();

        searchingPage.ClickOnProjectTag();
        
        searchingPage.EnterSearchingValue(projectName);
                
        String actualSearchingResult = searchingPage.GetResultProjectFirstItemValue();
        
        try {
        	Assert.assertEquals(projectName, actualSearchingResult);
        	
        	helper.WriteLogs("pass", "<b>"+ projectName + " >> Project successfully created via API and verified on mobile </b>", test);
        	helper.WriteLogsWithScreenshot("pass", projectName, test);
		} catch (Exception e) {
			
			helper.WriteLogs("fail", e.getMessage(), test);
		}
        
    
    }
    
    
    
    @AfterTest
    public void tearDown() throws Exception {
    	report.flush();
    	driver.quit();
        
    }
}
