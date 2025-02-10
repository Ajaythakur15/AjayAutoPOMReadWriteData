package testCases;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.TestBase;
import pages.HomeSearchPage;
import pages.TestCheckPage;
import util.TestUtilExcel;

public class HomeSearchTest extends TestBase {
    private TestCheckPage testCheckPage;
    private HomeSearchPage homeSearchPage;

    @BeforeMethod
    public void setUp() {
        initializeDriver();
        testCheckPage = new TestCheckPage();
        homeSearchPage = new HomeSearchPage();
    }

    @DataProvider(name = "getLoginData")
    public Object[][] getLoginData() {
        // Using the inherited read sheet name
        return new TestUtilExcel().getTestData(SHEET_NAME_READ);
    }

    @Test(priority = 1, dataProvider = "getLoginData", description = "Validate Login and Repo Search")
    public void testRepositorySearch(String username, String password, String url, String repoName) {
        System.out.println("\n🔹 Testing Login and Repo Search for: " + username);

        driver.get(url);
        testCheckPage.LogInPage(username, password);

        homeSearchPage.enterSearchQuery(repoName);
        String searchResult = homeSearchPage.getSearchResultText();

        // Define headers (written only once)
        String[] headers = {"Username", "Password", "URL", "Repo Name", "Expected Result", "Actual Result", "Status"};

     // Determine if the test passed or failed
        boolean isTestPassed = searchResult.contains(repoName);
        String status = isTestPassed ? "PASS" : "FAIL";
        
        // Define test data to write
        String[] values = {username, password, url, repoName, "Found", searchResult, status};

        // Write to Excel
        TestUtilExcel.writeToExcel(SHEET_NAME_WRITE, headers, values);
        
        Assert.assertTrue(isTestPassed, "❌ Repo search result mismatch!");
        Assert.assertTrue(searchResult.contains(repoName), "❌ Repo search result mismatch!");

        String findSearchResult = homeSearchPage.ClickOnFirstFinds();
        System.out.println("✅ Found Repo: " + findSearchResult);
        Assert.assertTrue(findSearchResult.contains(repoName), "❌ Repo search not found!");
    }

    @AfterMethod
    public void tearDown() {
        try {
            if (driver != null) {
                driver.quit();
            }
        } catch (Exception e) {
            System.out.println("⚠️ WebDriver shutdown error: " + e.getMessage());
        }
    }

    @AfterMethod(alwaysRun = true)
    public void takeScreenshotOnFailure(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            String screenshotPath = TestUtilExcel.takeScreenshot(result.getName());
            System.out.println("🖼 Screenshot saved at: " + screenshotPath);
        }
    }
}