package testCases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.TestBase;
import pages.HomeSearchPage;
import pages.LogInPage;

public class LogInPageTest extends TestBase{
	private LogInPage loginPage;
    private HomeSearchPage homePage;


 // Constructor to initialize properties
    public LogInPageTest() {
        super();
    }
	
	@BeforeMethod
	public void setUp() {
	initializeDriver();
	loginPage = new LogInPage();
	}
	
	@Test(priority=1, enabled=true, description ="Validate the login page title")
	public void loginPageTitleTest() {
		String actualTitle = loginPage.verifyHomePageTitle();
		String expectedTitle ="Login - User Registration";
		Assert.assertEquals(actualTitle,expectedTitle,"Page title does not match!");
	}
	
	@Test(priority=2, enabled=true, description ="Perform login with valid credentials.")
	public void loginTest(String username,String password) {
		//String username = prop.getProperty("username");
		//String password =prop.getProperty("password");
		homePage = loginPage.login(username, password);
		Assert.assertNotNull(homePage,"Login failed. HomePage object is null.");
	}
	
	@AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // Ensure driver cleanup to avoid memory leaks
        }
    }
}

