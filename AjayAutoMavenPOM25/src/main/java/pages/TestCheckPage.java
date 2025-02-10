package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.TestBase;

public class TestCheckPage extends TestBase{
	
	@FindBy(xpath="//input[@id='login_field']")
	WebElement userId;
	
	@FindBy(xpath="//input[@id='password']")
	WebElement userPwd;
	
	@FindBy(xpath="")
	WebElement userName;
	
	@FindBy(xpath="//body/div[1]/div[3]/main[1]/div[1]/div[4]/form[1]/div[1]/input[13]")
	WebElement logInButton;
	
	public TestCheckPage() {
		PageFactory.initElements(driver, this);
	}
	
	public String getLogInPageTitle() {
		return driver.getTitle();
		
	}
	
	public void LogInPage(String un,String pwd )
	{
		userId.clear();
		userId.sendKeys(un);
		userPwd.clear();
		userPwd.sendKeys(pwd);
		logInButton.click();
		System.out.println("Typing username: " + un);
		System.out.println("Typing password: " + pwd);

	}
}
