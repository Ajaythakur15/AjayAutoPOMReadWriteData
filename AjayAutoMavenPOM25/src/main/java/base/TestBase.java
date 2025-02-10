package base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import util.TestUtilExcel;
import util.WebEventListener;

public class TestBase {
    // WebDriver, Properties, and WebEventListener instances
    public static WebDriver driver;
    public static Properties prop;
    public static WebEventListener eventListener;

    // Define sheet names for reading and writing
    protected static final String SHEET_NAME_READ = "AjayDataRead";  // Sheet for reading data
    protected static final String SHEET_NAME_WRITE = "AjayDataWrite"; // Sheet for writing results

    // Constructor to load properties file
    public TestBase() {
        try {
            prop = new Properties();
            FileInputStream ip = new FileInputStream(
                    System.getProperty("user.dir") + "/src/main/java/config/config.properties");
            prop.load(ip);
        } catch (IOException e) {
            throw new RuntimeException("Configuration file not found or could not be loaded", e);
        }
    }

    /**
     * Initializes the WebDriver, applies the WebEventListener, and configures browser settings.
     */
    public static void initializeDriver() {
        String browserName = prop.getProperty("browser");

        if (browserName.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browserName.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else {
            throw new IllegalArgumentException("Browser not supported: " + browserName);
        }

        // Set driver options and timeouts
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestUtilExcel.PAGE_LOAD_TIMEOUT));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TestUtilExcel.IMPLICIT_WAIT));
        driver.get(prop.getProperty("url"));
    }

    /**
     * Cleans up WebDriver resources and quits the browser session.
     */
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null; // Clear the reference to avoid memory leaks
        }
    }
}