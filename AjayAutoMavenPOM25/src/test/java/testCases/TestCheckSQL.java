package testCases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.TestBase;
import pages.TestCheckPage;
import util.SQLDBUtil;

public class TestCheckSQL extends TestBase {

	private TestCheckPage testCheckPage;

    @BeforeMethod
    public void setUp() {
        initializeDriver();
        testCheckPage = new TestCheckPage();
    }

    @DataProvider
    public Object[][] getLoginData() {
        return SQLDBUtil.getLoginData(); // Fetch test data from SQL Server
    }

    @Test(priority = 1, enabled = true, dataProvider = "getLoginData", description = "Validate Login Functionality")
    public void testLogin(String username, String password, String url) {
        driver.get(url);  // Open URL from database
        testCheckPage.LogInPage(username, password); // Pass data from SQL Server
        
        // Validate login success by checking if URL contains "github.com"
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
