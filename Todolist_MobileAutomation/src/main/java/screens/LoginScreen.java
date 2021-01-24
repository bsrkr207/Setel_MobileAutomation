package screens;

import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import utils.Helper;

public class LoginScreen extends Helper{

	ExtentTest test;
	
	public LoginScreen(AppiumDriver<?> driver, ExtentTest test) {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        this.test = test;
	}
	
	@AndroidFindBy(id="btn_welcome_continue_with_email")
    MobileElement welcome_ContinueWithEmail_Button;

    @AndroidFindBy(id="email_exists_input")
    MobileElement email_Textbox;

    @AndroidFindBy(id="btn_continue_with_email")
    MobileElement continueWithEmail_Button;

    @AndroidFindBy(id="log_in_password")
    MobileElement password_Textbox;

    @AndroidFindBy(id="btn_log_in")
    MobileElement login_Button;
    
    
    public void logintoToDoList(String emailId, String password) {
    	
    	clickOnTheElement(welcome_ContinueWithEmail_Button);
    	enterTextIntoTextbox(email_Textbox, emailId);
    	clickOnTheElement(continueWithEmail_Button);
    	enterTextIntoTextbox(password_Textbox, password);
    	clickOnTheElement(login_Button);

    	writeLogs("info", "login successfull", test);
    	
    }
    
    
}


