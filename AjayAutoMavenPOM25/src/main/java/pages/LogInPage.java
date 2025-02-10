package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.TestBase;

public class LogInPage extends TestBase{

	
	@FindBy(xpath="//li[@id='menu-item-81']//a[contains(text(),\"Login\")]")
	
	WebElement userOnLoginPage;
	
	@FindBy(xpath="//span[@class='input-wrapper']//input[@name='username']")
	WebElement userName;
	
	@FindBy(xpath="/html/body/div[1]/div[2]/div/div/main/article/div/div/div/div/form/div/div/p[2]/span/span/input")
	WebElement userPwd;
	
	@FindBy(xpath="//input[@value=\"Login\"]")
	WebElement loginBtn;
	
	public LogInPage(){
		PageFactory.initElements(driver, this);
	}
	
	public String verifyHomePageTitle() {
		return driver.getTitle();	
	}
	
	public HomeSearchPage login(String un,String pwd) {
		userName.sendKeys(un);
		userPwd.sendKeys(pwd);
		//loginBtn.click();
		JavascriptExecutor js = (JavascriptExecutor)driver;
    	js.executeScript("arguments[0].click();", loginBtn);
		return new HomeSearchPage();
	}
}
