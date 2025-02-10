package testCases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import base.TestBase;
import pages.TestCheckPage;
import util.TestUtilExcel;

public class TestCheckExcel extends TestBase {
    
    private TestCheckPage testCheckPage; // Declare instance variable for page object
    private static final String sheetName = "logids"; // Define sheet name as a constant (Change as per your actual sheet name)

    @BeforeMethod
    public void setUp() {
        initializeDriver(); // Initialize WebDriver from TestBase
        testCheckPage = new TestCheckPage(); 
    }
    
    @DataProvider(name = "getLoginData")
    public Object[][] getLoginData() {
        TestUtilExcel testUtil = new TestUtilExcel(); // Instantiate utility class
        return testUtil.getTestData(sheetName); // Fetch data from Excel
    }

    @Test(priority = 1, enabled = true, description = "Validate the page1")
    public void test1() {
        System.out.println("Running === FirstTest === test1");
    }

    @Test(priority = 2, enabled = true, description = "Validate the page2")
    public void test2() {
        System.out.println("Running === FirstTest === test2");
    }

    @Test(priority = 3, enabled = true, description = "Validate the page3")
    public void test3() {
        System.out.println("Running === FirstTest === test3");
    }

    @Test(priority = 4, enabled = true, description = "Validate the Login Page Title")
    public void test4() {
        String title = testCheckPage.getLogInPageTitle(); // Get page title from TestCheckPage object
        Assert.assertEquals(title, "Sign in to GitHub · GitHub", "Page title does not match!"); // Assert page title matches expected
    }

    @Test(priority = 5, enabled = true, dataProvider = "getLoginData", description = "Validate Login Functionality")
    public void test5(String username, String password, String url) {
        driver.get(url); // Open the provided URL
        testCheckPage = new TestCheckPage(); // Initialize page object
        testCheckPage.LogInPage(username, password);

        // Validate successful login
        String actualUrl = driver.getCurrentUrl();
        Assert.assertTrue(actualUrl.contains("github.com"), "Login failed! Unexpected URL: " + actualUrl);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // Ensure driver cleanup to avoid memory leaks after test execution
        }
    }
}