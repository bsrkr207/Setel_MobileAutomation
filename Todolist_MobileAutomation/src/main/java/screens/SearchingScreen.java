package screens;

import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import utils.Helper;

public class SearchingScreen extends Helper{

	ExtentTest test;
	
	@AndroidFindBy(id="search_edit_text")
    MobileElement search_Textbox;

	@AndroidFindBy(xpath="//android.widget.TextView[contains(@text, 'PROJECTS')]")
	MobileElement actionBar_Project;

	@AndroidFindBy(xpath="//android.widget.TextView[contains(@text, 'TASKS')]")
    MobileElement actionBar_Task;

    @AndroidFindBy(id="button1")
    MobileElement timeSetting_Popup_Yes;

    @AndroidFindBy(id="content")
    MobileElement projectFirstItem_Result;

    
    public SearchingScreen(AppiumDriver<?> driver, ExtentTest test) {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        this.test = test;
    }

    public void enterSearchingValue(String keyword) {
        
    	enterTextIntoTextbox(search_Textbox, keyword);
    	
    }
    
    public void clearSearchingValue() {
    	
    	clearTextFromElement(search_Textbox);
    }

    public void clickOnProjectTag() {
    	
    	clickOnTheElement(actionBar_Project);
    }

    public void clickOnTaskTag() {
    	
    	clickOnTheElement(actionBar_Task);
    }

    public void clickOnTimeSettings_Yes() {
    	
    	try {
    			clickOnTheElement(timeSetting_Popup_Yes);
    			writeLogs("info", "Time settings popup - yes button selected", test);
		} catch (Exception e) {
			
			writeLogs("info", "Time settings popup not displayed", test);
		}	
    		
    }
    
    public String getResultProjectFirstItemValue() {
    
    	return getTextFromElement(projectFirstItem_Result);
    }

    public void clickOnResultFirstItem() {
        
    	clickOnTheElement(projectFirstItem_Result);
    }
}
