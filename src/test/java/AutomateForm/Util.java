package AutomateForm;

import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/*
 * Declaring some common parameters for scripts
 * You can change them to adapt your environment
 *
 */

public class Util {
		
		/* You can change the Path of FireFox based on your environment here */
		public static final String FIREFOX_PATH = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
								
		// Setting Base URL
		public static final String BASE_URL = "http://www.seleniumframework.com/Practiceform/";
	    
		// Time to wait when searching for a GUI element 
		public static final int WAIT_TIME = 10; 
												
		// Expected output
		public static final String HOMEPAGE_TITLE = "Selenium Framework | Practiceform";
		public static final String FORM_HEADER = "Practice Form Controls";
		
		public static final String expectedLabelText= "Fieldset"
				+ "Textarea"
				+ "This is a text area"
				+ "This is a text area"
				+ "Text"
				+ "This is a text box"
				+ "Checkbox"
				+ "Option 1"
				+ "Option 2"
				+ "Option 3"
				+ "Radio"
				+ "Option 1"
				+ "Option 2"
				+ "Option 3"				
				+ "Date"
				+ "URL"
				+ "Select"
				+ "Option 1"
				+ "Option 2"
				+ "Option 3"				
				+ " Verification"
				+ "Please enter any two digits *"
				+ "Example: 12"
				+ "Practice Form Controls"
				+ "  1) BROWSER WINDOWS"
				+ "New Browser Window"
				+ "New Message Window"
				+ "New Browser Tab"
				+ "This is a nested element"
				+ "Find me I have nothing in me!!"
				+ "I will have random ID"
				+ "2) JAVA SCRIPT ALERT"
				+ "Alert Box"
				+ "3) JAVA SCRIPT TIMING ALERT"
				+ "Timing Alert"
				+ "Seconds remaining: 37"
				+ ""
				+ "Change Color"
				+ ""
				+ "Change Color"
				+ ""
				+ "Drag Me!"
				+ ""
				+ "Drag To!";
				
		// You can change the Path of Datasheet here
		public static final String EXCELSHEET_PATH = "C:\\MVNProjects\\SeleniumFramework\\MasterData.xlsx";
		static FileInputStream fis;
		static XSSFWorkbook wb;
		static XSSFSheet sheet;
		static XSSFRow row;
		static XSSFCell cell;
		
		//Method to get Cell Data
		public static String getCellData(int rownum, int colnum) throws IOException {
			   
			   row = sheet.getRow(rownum);
			   cell = row.getCell(colnum);
			   return cell.getStringCellValue();			   
		   }
}
