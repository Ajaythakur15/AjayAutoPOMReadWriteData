package util;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
 
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
 
import base.TestBase;
 
public class TestUtilExcel extends TestBase {
 
    public static final long PAGE_LOAD_TIMEOUT = 20;
    public static final long IMPLICIT_WAIT = 20;
    
    private static final String TESTDATA_PATH = System.getProperty("user.dir") + "/src/main/java/testdata/AjayLogInData.xlsx";
    private static final String TESTRESULTS_PATH = System.getProperty("user.dir") + "/src/main/java/testoutput/TestResults.xlsx";
 
    private static Workbook workbook;
    private static Sheet sheet;
    private JavascriptExecutor js;
 
    /**
     * Fetches test data from the specified Excel sheet.
     * 
     * @param sheetName The name of the sheet.
     * @return A 2D array containing the test data.
     */
    public Object[][] getTestData(String sheetName) {
        try (FileInputStream file = new FileInputStream(TESTDATA_PATH)) {
            workbook = WorkbookFactory.create(file);
            sheet = workbook.getSheet(sheetName);
            if (sheet == null) throw new IllegalArgumentException("Sheet " + sheetName + " not found!");

            int rows = sheet.getPhysicalNumberOfRows(); 
            int cols = sheet.getRow(0).getPhysicalNumberOfCells(); 
            Object[][] data = new Object[rows - 1][cols];

            for (int i = 1; i < rows; i++) { 
                Row row = sheet.getRow(i);
                if (row == null) continue;
                for (int j = 0; j < cols; j++) {
                    data[i - 1][j] = row.getCell(j) != null ? row.getCell(j).toString() : "";
                }
            }
            return data;

        } catch (IOException e) {
            throw new RuntimeException("Error reading test data: " + e.getMessage());
        }
    }
    
    /**
     * Writes test results to an Excel file.
     * @param sheetName The sheet name.
     * @param rowNumber Row number.
     * @param colNumber Column number.
     * @param value Value to write.
     */
    public static void writeToExcel(String sheetName, String[] headers, String[] values) {
        File file = new File(TESTRESULTS_PATH);
        
        System.out.println("⚡ Attempting to write to: " + TESTRESULTS_PATH);
        Workbook workbook;
        Sheet sheet;

        try {
            FileInputStream fis = null;
            if (file.exists()) {
                fis = new FileInputStream(file);
                workbook = new XSSFWorkbook(fis);
                fis.close();
            } else {
                workbook = new XSSFWorkbook(); // Create new workbook if file doesn't exist
            }

            sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                System.out.println("📄 Sheet '" + sheetName + "' does not exist. Creating new sheet.");
                sheet = workbook.createSheet(sheetName);
            }

            int lastRowNum = sheet.getLastRowNum();
            if (lastRowNum == 0) {
                Row headerRow = sheet.createRow(0);
                for (int i = 0; i < headers.length; i++) {
                    headerRow.createCell(i).setCellValue(headers[i]);
                }
            }

            // Append new data to the next available row
            Row row = sheet.createRow(sheet.getLastRowNum() + 1);
            for (int i = 0; i < values.length; i++) {
                row.createCell(i).setCellValue(values[i]);
            }

            FileOutputStream fos = new FileOutputStream(TESTRESULTS_PATH);
            workbook.write(fos);
            fos.close();
            workbook.close();

            System.out.println("🎉 Data written successfully!");

        } catch (IOException e) {
            throw new RuntimeException("Error writing to Excel: " + e.getMessage());
        }
    }
 
    /**
     * Captures a screenshot and saves it in the "screenshots" folder.
     * 
     * @throws IOException If an error occurs during file operations.
     */
    public static void takeScreenshotAtEndOfTest() throws IOException {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String screenshotPath = System.getProperty("user.dir") + "/screenShots/" + System.currentTimeMillis() + ".png";
        FileUtils.copyFile(scrFile, new File(screenshotPath));
    }
 
    /**
     * Displays runtime information using jQuery growl notifications.
     * 
     * @param messageType The type of message (e.g., "error", "info", "warning").
     * @param message     The message to display.
     */
    public void runTimeInfo(String messageType, String message) {
        js = (JavascriptExecutor) driver;
 
        js.executeScript("if (!window.jQuery) {"
                + "var jquery = document.createElement('script'); jquery.type = 'text/javascript';"
                + "jquery.src = 'https://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js';"
                + "document.getElementsByTagName('head')[0].appendChild(jquery);}");
 
        js.executeScript("$.getScript('https://the-internet.herokuapp.com/js/vendor/jquery.growl.js');");
        js.executeScript("$('head').append('<link rel=\"stylesheet\" "
                + "href=\"https://the-internet.herokuapp.com/css/jquery.growl.css\" type=\"text/css\" />');");
 
        switch (messageType.toLowerCase()) {
            case "error":
                js.executeScript("$.growl.error({ title: 'ERROR', message: '" + message + "' });");
                break;
            case "info":
                js.executeScript("$.growl.notice({ title: 'Notice', message: '" + message + "' });");
                break;
            case "warning":
                js.executeScript("$.growl.warning({ title: 'Warning!', message: '" + message + "' });");
                break;
            default:
                System.out.println("Invalid message type: " + messageType);
        }
    }

	public static String takeScreenshot(String testName) {
		// TODO Auto-generated method stub
		try {
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String screenshotPath = System.getProperty("user.dir") + "/screenshots/" + testName + "_" + System.currentTimeMillis() + ".png";
            FileUtils.copyFile(scrFile, new File(screenshotPath));
            return screenshotPath;
        } catch (IOException e) {
            throw new RuntimeException("Error capturing screenshot: " + e.getMessage());
        }
	}
}