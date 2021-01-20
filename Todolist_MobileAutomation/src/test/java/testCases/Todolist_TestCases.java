package testCases;

import java.sql.Timestamp;

import org.testng.Assert;
import org.testng.ITestClass;
import org.testng.ITestMethodFinder;
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

public class Todolist_TestCases {

	private ExtentTest test;
	private ExtentReports report;
	
	private AppiumDriver<?> driver;
    private APIHelper apiHelper = new APIHelper();
    private Helper helper = new Helper();
    private TestData testdata = new TestData();
    private String baseURI;
    private String bearerToken;
    private String globalProjectName;
    private String globalTaskName;

    
    @BeforeTest
    public void setUp() throws Exception {
        
    	testdata = testdata.ReturnTestData();
    	
    	driver = helper.returnDriver(testdata.getPlatform());
        
    	report = helper.startExtentReport(ITestClass.class.getName());
    	
    }

    @Test
    public void VerifyTheProjectHasBeenCreatedSuccessfullyViaAPIOnMobile() {
        
    	test = report.createTest(ITestMethodFinder.class.getName());
    	
    	LoginPage loginScreen = new LoginPage(driver);
        
        NavigationBar navigationBar = new NavigationBar(driver);
        
        SearchingPage searchingPage = new SearchingPage(driver);

        //create project by API
        String id = new Timestamp(System.currentTimeMillis()).toString();
        
        globalProjectName = "TestSetel_" + id;

        apiHelper.createProjectSuccessfully(baseURI, globalProjectName, bearerToken);
        
        //log in and verify on Mobile
        loginScreen.LogintoToDoList(testdata.getUserID(), testdata.getPassword());
        
        navigationBar.ClickOnMagnifyingGlassIcon();

        searchingPage.enterSearchingValue(globalProjectName);
        
        searchingPage.clickOnProjectTag();
        
        String actualSearchingResult = searchingPage.getResultProjectFirstItemValue();
        
        Assert.assertEquals(globalProjectName, actualSearchingResult);
    
    }
    
}
