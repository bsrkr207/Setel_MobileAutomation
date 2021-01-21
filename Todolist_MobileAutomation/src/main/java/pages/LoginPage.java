package pages;

import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import utils.Helper;

public class LoginPage extends Helper{

	ExtentTest test;
	
	public LoginPage(AppiumDriver<?> driver, ExtentTest test) {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        this.test = test;
	}
	
	@AndroidFindBy(id="btn_welcome_continue_with_email")
    MobileElement Welcome_ContinueWithEmail_Button;

    @AndroidFindBy(id="email_exists_input")
    MobileElement Email_Textbox;

    @AndroidFindBy(id="btn_continue_with_email")
    MobileElement ContinueWithEmail_Button;

    @AndroidFindBy(id="log_in_password")
    MobileElement Password_Textbox;

    @AndroidFindBy(id="btn_log_in")
    MobileElement Login_Button;
    
    
    public void LogintoToDoList(String emailId, String password) {
    	
    	ClickOnTheElement(Welcome_ContinueWithEmail_Button);
    	EnterTextIntoTextbox(Email_Textbox, emailId);
    	ClickOnTheElement(ContinueWithEmail_Button);
    	EnterTextIntoTextbox(Password_Textbox, password);
    	ClickOnTheElement(Login_Button);

    	WriteLogs("info", "login successfull", test);
    	WriteLogsWithScreenshot("info", "login successfull", test);
    	
    }
    
    
}


