package pages;

import java.time.Duration;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import base.TestBase;

public class HomeSearchPage extends TestBase {
    
    @FindBy(xpath = "//header/div[1]/div[2]/div[1]/qbsearch-input[1]/div[1]/div[1]/div[1]/div[1]/button[1]")  
    WebElement searchBox;

    @FindBy(xpath = "//input[@id='query-builder-test']")  
    WebElement enterRepoName;
    
    @FindBy(xpath="//span[contains(text(),'Search all of GitHub')]")
    WebElement searchOnGitHub;
    
    @FindBy(xpath="//body/div[1]/div[5]/main[1]/react-app[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/div[4]/div[1]/div[1]/div[1]")
    WebElement clickOnFirstSearch;

    public HomeSearchPage() {
        PageFactory.initElements(driver, this);
    }
    
    public String verifyHomePageTitle() {
        return driver.getTitle();    
    }
    
    public void ClickOnSearchBar() {
    	searchBox.click();
    }
    
    public void EnterRepoName(String repoName) {
    	enterRepoName.sendKeys(repoName);
    }
    
    public void ClickOnFirstFind() {
    	clickOnFirstSearch.click();
    }
    

    public void enterSearchQuery(String repoName) {
    	searchBox.click();
    	enterRepoName.clear();
    	enterRepoName.sendKeys(repoName);
    	enterRepoName.sendKeys(Keys.ENTER);  // Press Enter to submit search
    	clickOnFirstSearch.click();
        System.out.println("Searching for repository: " + repoName);
    }
    
    public String getSearchResultText() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        try {
            wait.until(ExpectedConditions.visibilityOf(clickOnFirstSearch));
            String resultText = clickOnFirstSearch.getText();
            System.out.println("Search result found: " + resultText);
            return resultText;
        } catch (Exception e) {
            System.out.println("No search results found or element not visible.");
            return "No Results";
        }
    }
    public String ClickOnFirstFinds() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
        wait.until(ExpectedConditions.elementToBeClickable(clickOnFirstSearch)).click();
        String findText=clickOnFirstSearch.getText();
        System.out.println("Search result found: " + findText);
        return findText;
        } catch (Exception e) {
            System.out.println("No search results found or element not visible.");
            return "No Results";
        }
    }
}