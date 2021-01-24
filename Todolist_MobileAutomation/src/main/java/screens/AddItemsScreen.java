package screens;

import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import utils.Helper;

public class AddItemsScreen extends Helper{

	@AndroidFindBy(id="fab")
    MobileElement addItem_Button;

	@AndroidFindBy(id ="android:id/message")
    MobileElement message_Textbox;

	@AndroidFindBy(id ="android:id/button1")
    MobileElement addItem_Enter;
	
	@AndroidFindBy(id="com.todoist:id/text")
    MobileElement taskFirstItem_Result;

    @AndroidFindBy(id="checkmark")
    MobileElement firstCheckMark_Complete;
             
    public AddItemsScreen(AppiumDriver<?> driver) {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void clickOnAddItemButton() {
    	
    	clickOnTheElement(addItem_Button);
    }

    public void enterTaskName(String keyword) {
        
    	enterTextIntoTextbox(message_Textbox, keyword);
    }
    
    public void clickOnAddtask_Enter() {
        
    	clickOnTheElement(addItem_Enter);
    }

    public void clickOnTaskFirstItem() {
        
    	clickOnTheElement(taskFirstItem_Result);
    }

    public String GetResultTaskFirstItemValue() {
    	
    	return getTextFromElement(taskFirstItem_Result);
    }
        
    public void closeTheTask() {
        
    	clickOnTheElement(firstCheckMark_Complete);
    }
    
    
}
