package pages;

import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import utils.Helper;

public class SearchingPage extends Helper{

	ExtentTest test;
	
	@AndroidFindBy(id="search_edit_text")
    MobileElement Search_Textbox;

	@AndroidFindBy(xpath="//android.widget.TextView[contains(@text, 'PROJECTS')]")
	MobileElement ActionBar_Project;

	@AndroidFindBy(xpath="//android.widget.TextView[contains(@text, 'TASKS')]")
    MobileElement ActionBar_Task;

    @AndroidFindBy(id="button1")
    MobileElement TimeSetting_Popup_Yes;

    @AndroidFindBy(id="content")
    MobileElement ProjectFirstItem_Result;

    @AndroidFindBy(id="text")
    MobileElement TaskFirstItem_Result;

    @AndroidFindBy(id="checkmark")
    MobileElement FirstCheckMark_Complete;


    public SearchingPage(AppiumDriver<?> driver, ExtentTest test) {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        this.test = test;
    }

    public void EnterSearchingValue(String keyword) {
        
    	EnterTextIntoTextbox(Search_Textbox, keyword);
    	
    }

    public void ClickOnProjectTag() {
    	
    	ClickOnTheElement(ActionBar_Project);
    }

    public void ClickOnTaskTag() {
    	
    	ClickOnTheElement(ActionBar_Task);
    }

    public void ClickOnTimeSettings_Yes() {
    	
    	try {
    			ClickOnTheElement(TimeSetting_Popup_Yes);
    			WriteLogs("info", "Time settings popup - yes button selected", test);
		} catch (Exception e) {
			
			WriteLogs("info", "Time settings popup not displayed", test);
		}	
    		
    }
    
    public String GetResultProjectFirstItemValue() {
    
    	return GetTextFromElement(ProjectFirstItem_Result);
    }

    public String GetResultTaskFirstItemValue() {
    	
    	return GetTextFromElement(TaskFirstItem_Result);
    }

    public void ClickOnResultFirstItem() {
        
    	ClickOnTheElement(ProjectFirstItem_Result);
    }

    public void ClickOnFirstSearchingTaskCheckMark() {
    	
    	ClickOnTheElement(FirstCheckMark_Complete);
    }
}
