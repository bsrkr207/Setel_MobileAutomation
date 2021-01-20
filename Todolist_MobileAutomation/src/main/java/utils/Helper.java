package utils;

import java.io.File;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;

public class Helper {

	
	
	public static AppiumDriver<?> returnDriver(String platform) throws Exception {
        
    	AppiumDriver<?> driver;
        
    	TestData testdata = new TestData();
		
		testdata = testdata.returnTestData();
		
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

                capabilities.setCapability(MobileCapabilityType.APP, new File(testdata.getApp_android_path()).getAbsolutePath());
                capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, testdata.getDevice_android_name());
                capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);

                driver = new AndroidDriver<RemoteWebElement>(new URL(completeURL), capabilities);
                break;

            default:
                throw new Exception("Platform not supported");
        }

        return driver;
    }
	

}
