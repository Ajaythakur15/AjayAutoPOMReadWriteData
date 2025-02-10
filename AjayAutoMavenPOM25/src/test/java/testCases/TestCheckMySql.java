package testCases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.TestBase;
import pages.TestCheckPage2;
import util.MySqlDBUtil;

public class TestCheckMySql extends TestBase{

	private TestCheckPage2 testCheckPage2;

    @BeforeMethod
    public void setUp() {
        initializeDriver(); 
        testCheckPage2 = new TestCheckPage2();
    }

    @DataProvider
    public Object[][] getLoginData() {
        return MySqlDBUtil.getDatabaseTestData(); // Fetch test data from the database
    }
	
	@Test(priority=1,enabled=true,description ="Validate the page4")
	public void test1() {
		System.out.println("Runnin===SecondTest===test4");
	}
	
	@Test(priority=2,enabled=true,description ="Validate the page5")
	public void test2() {
		System.out.println("Runnin===SecondTest===test5");
	}
	
	@Test(priority=3, enabled=true,description ="Validate the page6")
	public void test3() {
		System.out.println("Running===SecondTest===test6");
	}
	
	@Test(priority=4,enabled=true,description ="Validate the page7")
	public void test4() {
		System.out.println("Running===SeconfTest===test7");
	}
	
	@Test(priority = 5, enabled = true, dataProvider = "getLoginData", description = "Validate Login Functionality")
    public void test5(String username, String password, String url) {
		driver.get(url); // Open the provided URL
	    testCheckPage2.LogInPage(username, password);

        // Validate successful login
        String actualUrl = driver.getCurrentUrl();
        Assert.assertTrue(actualUrl.contains("github.com"), "Login failed! Unexpected URL: " + actualUrl);
    }
	
	 @AfterMethod
	    public void tearDown() {
	        if (driver != null) {
	            driver.quit();
	        }
	        }
}
