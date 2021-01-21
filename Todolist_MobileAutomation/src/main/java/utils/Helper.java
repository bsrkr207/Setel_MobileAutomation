package utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

import javax.annotation.processing.SupportedAnnotationTypes;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;

public class Helper {
	
	ExtentReports extent; 
	static AppiumDriver<?> driver;
	static WebDriverWait wait;
	
	//Driver initialization
	public AppiumDriver<?> returnDriver(String platform) throws Exception {
        
    	TestData testdata = new TestData();
		
		testdata = testdata.ReturnTestData();
		
        DesiredCapabilities capabilities = new DesiredCapabilities();

        if (Boolean.parseBoolean(testdata.getHybrid())) {
            capabilities.setCapability(MobileCapabilityType.AUTO_WEBVIEW, true);
        }

        String completeURL = "http://" + testdata.getIp() + ":" + testdata.getPort() + "/wd/hub";

        switch (platform.toLowerCase()) {

            case "ios":
                capabilities.setCapability(MobileCapabilityType.APP, new File(testdata.getApp_ios_path()).getAbsolutePath());
                capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, testdata.getDevice_ios_name());
                capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, testdata.getPlatform_ios_version());
                capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);

                if ( Boolean.parseBoolean(testdata.getPlatform_ios_xcode8())) {
                    capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
                }

                driver = new IOSDriver<RemoteWebElement>(new URL(completeURL), capabilities);
                break;

            case "android":

            	String path = returnProjectPath()+""+testdata.getApp_android_path();
            	
                capabilities.setCapability(MobileCapabilityType.APP, path);
                capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, testdata.getDevice_android_name());
                capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
                capabilities.setCapability("appPackage", testdata.getAppPackage());
                capabilities.setCapability("appActivity", testdata.getAppActivity());
                
                driver = new AndroidDriver<RemoteWebElement>(new URL(completeURL), capabilities);
                break;

            default:
                throw new Exception("Platform not supported");
        }

        wait = new WebDriverWait(driver, 30);
        return driver;
    }
	
	//Extension methods
    public void ClickOnTheElement(MobileElement element) {
    	wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    public void EnterTextIntoTextbox(MobileElement element, String text) {
    	wait.until(ExpectedConditions.elementToBeClickable(element)).sendKeys(text);
    }
	
    public String GetTextFromElement(MobileElement element) {
    	return wait.until(ExpectedConditions.elementToBeClickable(element)).getText();
    }

	
	//Extent report initialization
	public ExtentReports startExtentReport(String reportName) {
		
		String timestamp = new Timestamp(System.currentTimeMillis()).toString();
		
		String path = createReportsPath() +"\\"+ reportName +"_"+ timestamp.replace(":", ".") +".html";
		
		ExtentSparkReporter spark = new ExtentSparkReporter(path);
		
		try {
			
			spark.loadXMLConfig(Paths.get("spark-config.xml").toFile());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		extent = new ExtentReports();
		
		extent.setSystemInfo("OS", System.getProperty("os.name"));
		extent.setSystemInfo("Environment", "QA");
		extent.setSystemInfo("User", System.getProperty("user.name"));
		extent.setReportUsesManualConfiguration(false);
		extent.attachReporter(spark);
		
		return extent;
	}

	public void WriteLogs(String status, String msg, ExtentTest test) {
	
		status = status.toLowerCase();
		
		switch (status) {
		case "pass":
			test.log(Status.PASS, msg);
			break;

		case "fail":
			test.log(Status.FAIL, msg);
			break;

		case "warning":
			test.log(Status.WARNING, msg);
			break;

		case "info":
			test.log(Status.INFO, msg);
			break;
		}
	}
	
	public void WriteLogsWithScreenshot(String status, String screenshotName, ExtentTest test) {
		
		status = status.toLowerCase();

		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

        String image = encodeFileToBase64(scrFile);
        
		
		switch (status) {
		case "pass":
			test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromBase64String(image, screenshotName).build());
			break;

		case "fail":
			test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromBase64String(image, screenshotName).build());
			break;

		case "warning":
			test.log(Status.WARNING, MediaEntityBuilder.createScreenCaptureFromBase64String(image, screenshotName).build());
			break;

		case "info":
			test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromBase64String(image, screenshotName).build());
			break;
		}
	}
	
	
	public void endExtentReport() {
		
		extent.flush();
	}
	
	private File createReportsPath() {
		
		File dir = null;
		
		try {
			
			dir = new File("C:\\AutomationReports");
		    
			if (!dir.exists()) dir.mkdirs();
		    			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}		
		return dir;
	    
	}
	
	public String returnProjectPath() 
	{
		String root = FileSystems.getDefault().getPath("").toAbsolutePath().toString();
		return root;
	}
	
	private String encodeFileToBase64(File file) {
	    try {
	        
	    	byte[] fileContent = Files.readAllBytes(file.toPath());
	        
	    	return Base64.getEncoder().encodeToString(fileContent);
	    
	    } catch (IOException e) {
	        throw new IllegalStateException("could not read file " + file, e);
	    }
	}
	
}
