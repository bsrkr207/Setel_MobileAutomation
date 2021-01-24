package screens;

import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import utils.Helper;

public class NavigationBar extends Helper{

	@AndroidFindBy(id="menu_content_search")
    MobileElement magnifyingGlass_Icon;

    @AndroidFindBy(id="quick_add_item")
    MobileElement quickAddItem_Button;

    public NavigationBar(AppiumDriver<?> driver) {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void clickOnMagnifyingGlassIcon() {
        
    	clickOnTheElement(magnifyingGlass_Icon);
    }

    public void clickOnQuickAddItemButton() {
    	clickOnTheElement(quickAddItem_Button);
    }
    
}
