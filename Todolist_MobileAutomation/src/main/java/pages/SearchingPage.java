package pages;

import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class SearchingPage {

	@AndroidFindBy(id="search_edit_text")
    MobileElement txtSearch;

    @AndroidFindBy(xpath="//androidx.appcompat.app.ActionBar.c[@content-desc='Projects']")
    MobileElement tagProject;

    @AndroidFindBy(xpath="//androidx.appcompat.app.ActionBar.c[@content-desc='Tasks']")
    MobileElement tagTask;

    @AndroidFindBy(id="content")
    MobileElement resultProjectFirstItem;

    @AndroidFindBy(id="text")
    MobileElement resultTaskFirstItem;

    @AndroidFindBy(id="checkmark")
    MobileElement completeFirstCheckMark;


    public SearchingPage(AppiumDriver<?> driver) {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void enterSearchingValue(String keyword) {
        txtSearch.sendKeys(keyword);
    }

    public void clickOnProjectTag() {
        tagProject.click();
    }

    public void clickOnTaskTag() {
        tagTask.click();
    }

    public String getResultProjectFirstItemValue() {
        return resultProjectFirstItem.getText();
    }

    public String getResultTaskFirstItemValue() {
        return resultTaskFirstItem.getText();
    }

    public void clickOnResultFirstItem() {
        resultProjectFirstItem.click();
    }

    public void clickOnFirstSearchingTaskCheckMark() {
        completeFirstCheckMark.click();
    }
}
