package AutomateForm;

//************************************************************************//
//Step 1. Launch Firefox Browser
// Step 2. Goto the url: http://www.seleniumframework.com/Practiceform/
// Step 3. Automate all the fields
//************************************************************************//

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;

public class FormAutomate {
	
	static WebDriver driver;
	int scc = 0;
	static StringBuffer verificationErrors = new StringBuffer();
	Actions act;
	
	@BeforeTest
	  public void openBrowser() {		
		// This method will read initialization parameters from the class Util.java & launch Firefox
		
				File pathToBinary = new File(Util.FIREFOX_PATH);
				FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
				FirefoxProfile ffProfile = new FirefoxProfile();
				
				//Step 1. Launch Firefox Browser
				driver = new FirefoxDriver(ffBinary, ffProfile);
							
				driver.manage().window().maximize();					
				
				//Configure the Action
				  act = new Actions(driver);
	  }
	
	@BeforeMethod
	  public void goToUrl() {	
		// Step 2. Goto the url: http://www.seleniumframework.com/Practiceform/
		
				driver.get(Util.BASE_URL);
	  }

	
// Step 3. Automate all the fields

  @Test(priority=0)
  public void textArea(){	  
	  //Text area	  
	  
	  WebElement textArea = driver.findElement(By.cssSelector("#vfb-10"));
	  String text1 = "Hi seleniumframework user. This is textarea!!!";
	  
	  act.moveToElement(textArea).click()
	  		.keyDown(Keys.CONTROL).sendKeys("a").build().perform();
	  textArea.sendKeys(text1);
  }
  
  @Test(priority=0)
  public void textBox(){
	  //Text box
	  
	  WebElement textBox = driver.findElement(By.cssSelector("#vfb-9"));
	  String text2 = "Hi seleniumframework user. This is textbox!!!";
	  
	  act.moveToElement(textBox).click()
	  		.keyDown(Keys.CONTROL).sendKeys("a").build().perform();
	  textBox.sendKeys(text2);
  }
  
  @Test(priority=1)
  public void checkBox(){	  
	  //Checkbox
	  
	  List<WebElement> checkBoxElements = driver.findElements(By.cssSelector("input[type='checkbox']"));
	  checkBoxElements.get(2).click();
	  Assert.assertTrue(checkBoxElements.get(2).isSelected());
	  checkBoxElements.get(1).click();
	  Assert.assertTrue(checkBoxElements.get(1).isSelected());	  
  }
  
  @Test(priority=2)
  public void radioButton(){  
	  //Radio
	  
	  driver.findElement(By.cssSelector("#vfb-7-1")).click();
	  Assert.assertTrue(driver.findElement(By.cssSelector("#vfb-7-1")).isSelected());
  }
  
  @Test(priority=3)
  public void datePicker(){  
	  //Date
	  
	  WebElement date =driver.findElement(By.cssSelector("#vfb-8"));
	  date.clear();
	  date.click();
	  driver.findElement(By.xpath(".//*[@id='ui-datepicker-div']/table/tbody/tr[3]/td[2]/a")).click();
  }
  
  @Test(priority=4)
  public void urlText(){	  	  
	  //URL
	  
	  WebElement url = driver.findElement(By.cssSelector("#vfb-11"));
	  String urlText = "http://www.seleniumframework.com/Practiceform/";
	  url.clear();
	  url.sendKeys(urlText);
  }
  
  @Test(priority=5)
  public void dropdownSelect(){	  
	  // Select
	  WebElement selectElement = driver.findElement(By.cssSelector("#vfb-12"));
	  Select dropdown = new Select(selectElement);
	  dropdown.selectByVisibleText("Option 2");
  }
  
  @Test(priority=6)
  public void verificationTextAndSubmitSuccess() {
  	  //Verification Text box
	  WebElement verificationTextBox = driver.findElement(By.cssSelector("#vfb-3"));
	  String validVerificationText = "20";
	  
	  act.moveToElement(verificationTextBox).click()
	  		.keyDown(Keys.CONTROL).sendKeys("a").build().perform();
	  verificationTextBox.sendKeys(validVerificationText);
	 
	  //Submit
	  WebElement submit = driver.findElement(By.cssSelector("input[value='Submit']"));
	  WebElement submitSuccessMessage = driver.findElement(By.cssSelector("#form_success"));
	  submit.click();
	  WebDriverWait wdWait = new WebDriverWait(driver, 4);
	  wdWait.until(ExpectedConditions.visibilityOf(submitSuccessMessage));
	  
	  //Verify submit success message
	  String expectedSubmitSuccessMsg = "Your form was successfully submitted. Thank you for contacting us.";
	  String actualSubmitSuccessMsg = submitSuccessMessage.getText();
	  Assert.assertTrue(actualSubmitSuccessMsg.contains(expectedSubmitSuccessMsg));	  	  
  }
  
  @Test(dataProvider = "getData", priority=6)
  public void verificationTextAndSubmitError(String verificationText) {
  	  //Verification Text box
	  WebElement verificationTextBox = driver.findElement(By.cssSelector("#vfb-3"));
	  act.moveToElement(verificationTextBox).click()
	  		.keyDown(Keys.CONTROL).sendKeys("a").build().perform();
	  verificationTextBox.sendKeys(verificationText);
	 
	  //Submit
	  WebElement submit = driver.findElement(By.cssSelector("input[value='Submit']"));
	  WebElement submitErrorMessage = driver.findElement(By.cssSelector(".vfb-error"));
	  submit.click();
	  WebDriverWait wdWait = new WebDriverWait(driver, 4);
	  wdWait.until(ExpectedConditions.visibilityOf(submitErrorMessage));
	  Assert.assertTrue(submitErrorMessage.isDisplayed());	   
  }
  
  @DataProvider
  public Object[][] getData()
	{
	  Object data[][] = new Object[5][1];	  
	    data[0][0] =  "200";
	    data[1][0] = "tt";
	    data[2][0] =  "text";
	    data[3][0] = "1+";
	    data[4][0] = "0";
	return data;
	}
  
	  @Test(priority=17)
	  public void newBrowserWindow(){	  
	  //Verify New Browser Window button
	  driver.findElement(By.cssSelector("button[onclick='newBrwWin()']")).click();
	  //Get all the window handles in a set
	  Set <String> handles =driver.getWindowHandles();
	  Iterator<String> it = handles.iterator();
	  //iterate through your windows
	  while (it.hasNext())
	  {
	  String parent = it.next();
	  String newwin = it.next();
	  driver.switchTo().window(newwin);
	  driver.manage().window().maximize();
	  //perform actions on new window
	  Assert.assertTrue(driver.getCurrentUrl().equals("http://www.seleniumframework.com/"));
	  driver.close();
	  driver.switchTo().window(parent);
	  }
	  }
	  
	  @Test(priority=17)
	  public void newMessageWindow(){	  
	  //Verify New Message Window button
	  driver.findElement(By.cssSelector("button[onclick='newMsgWin()']")).click();
	  //Get all the window handles in a set
	  Set <String> handles1 =driver.getWindowHandles();
	  Iterator<String> it1 = handles1.iterator();
	  //iterate through your windows
	  while (it1.hasNext())
	  {
	  String parent1 = it1.next();
	  String newwin1 = it1.next();
	  driver.switchTo().window(newwin1);
	  driver.manage().window().maximize();
	  //perform actions on new window
	  Assert.assertTrue(driver.getCurrentUrl().equals("http://www.seleniumframework.com/"));
	  driver.close();
	  driver.switchTo().window(parent1);
	  }
	  }
	  
	  @Test(priority=17)
	  public void newBrowserTab(){
	  //Verify New Browser Tab button
	  driver.findElement(By.cssSelector("button[onclick='newBrwTab()']")).click();
	  //Get all the window handles in a set
	  Set <String> handles11 =driver.getWindowHandles();
	  Iterator<String> it11 = handles11.iterator();
	  //iterate through your windows
	  while (it11.hasNext())
	  {
	  String parent11 = it11.next();
	  String newwin11 = it11.next();
	  driver.switchTo().window(newwin11);
	  driver.manage().window().maximize();
	  //perform actions on new window
	  Assert.assertTrue(driver.getCurrentUrl().equals("http://www.seleniumframework.com/"));
	  driver.close();
	  driver.switchTo().window(parent11);
	  }
	  }
	  
	  @Test(priority=8)
	  public void nestedAndRandomIDElements(){
	  //Verify the nested element
	  String expectedNestedElementText = "Find me I have nothing in me!!";
	  WebElement nestedElement = driver.findElement(By.xpath(".//*[@class='wpb_wrapper']/p[4]/a[2]"));	  
	  Assert.assertTrue(nestedElement.getText().equals(expectedNestedElementText));
	  
	  //Verify random ID element
	  String expectedRandomIDElementText = "I will have random ID";
	  WebElement randomIDElement = driver.findElement(By.xpath(".//*[@class='wpb_wrapper']/p[5]"));	  
	  Assert.assertTrue(randomIDElement.getText().equals(expectedRandomIDElementText));
	  }
	  
	  @Test(priority=9)
	  public void alertJava(){
	  //Verify JAVA Script Alert
	  String alertMsg = "Please share this website with your friends and in your organization.";
	  WebElement alertButton = driver.findElement(By.cssSelector("#alert"));
	  alertButton.click();
	  Assert.assertTrue(driver.switchTo().alert().getText().equals(alertMsg));
	  //System.out.println(driver.switchTo().alert().getText());
	  driver.switchTo().alert().accept();
	  
	  }
	  
	  @Test(priority=10)
	  public void timingAlertJava(){
	  //Verify JAVA Script Timing Alert
	  String timingAlertMsg = "Please share this website with your friends and in your organization.";
	  WebElement timingAlertButton = driver.findElement(By.cssSelector("#alert"));
	  timingAlertButton.click();
	  Assert.assertTrue(driver.switchTo().alert().getText().equals(timingAlertMsg));
	  //System.out.println(driver.switchTo().alert().getText());
	  driver.switchTo().alert().accept();
	  
	  }
	 
	
	  @Test(priority=11)
	  public void clockTimer(){
	  //Verify clock
	  WebElement clockElement = driver.findElement(By.cssSelector("#clock"));
	  String clockText = "Seconds remaining: ";
	  String finalClockText = "Buzz Buzz";
	  int timerCount = 40;
	  if (clockElement.getText().contains("39"))
	  {
		  timerCount = 39;
	  }
	  else if (clockElement.getText().contains("38"))
	  {
		  timerCount = 38;		 
	  }
	  else if (clockElement.getText().contains("37"))
	  {
		  timerCount = 37;		 
	  }
	  
	  for (int i=timerCount; i>0; i--)
	  {
		  String timerText = clockText+i;
		  Assert.assertTrue(clockElement.getText().contains(timerText));
		  WebDriverWait wdWait = new WebDriverWait(driver, 4);
		  wdWait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(clockElement, timerText)));
	  }
	  
	  Assert.assertTrue(clockElement.getText().equals(finalClockText));	  	  
	  }
	  

	  @Test(priority=12)
	  public void changeColorButton(){
	  //Verify Change Color button
	  WebElement changeColorButton = driver.findElement(By.cssSelector("#colorVar"));
	  WebElement clockElement = driver.findElement(By.cssSelector("#clock"));
	  Assert.assertTrue(changeColorButton.getAttribute("style").equals("color: white;"));
	  WebDriverWait wdWait = new WebDriverWait(driver, 4);
	  wdWait.until(ExpectedConditions.attributeToBe(changeColorButton, "style", "color: red;"));
	  Assert.assertTrue(changeColorButton.getAttribute("style").equals("color: red;"));
	  Assert.assertTrue(clockElement.getText().equals("Seconds remaining: 36"));
}

	  @Test(priority=12)
	  public void changeColorButtonOnDoubleClick(){
	  //Verify Change Color button (on double click)
	  WebElement changeColorButton1 = driver.findElement(By.cssSelector("#doubleClick"));
	  //System.out.println(changeColorButton1.getAttribute("style"));	  
	  act.moveToElement(changeColorButton1).doubleClick().build().perform();
	  //System.out.println(changeColorButton1.getAttribute("style"));
	  Assert.assertTrue(changeColorButton1.getAttribute("style").contains("color: orange;"));
}
	  
	  @Test(priority=13)
	  public void dragAndDrop(){
	  //Verify Drag and Drop
	  WebElement dragToElement = driver.findElement(By.xpath(".//*[@id='dragb']"));
	  WebElement dragMeElement = driver.findElement(By.xpath(".//*[@id='draga']"));
	  act.dragAndDrop(dragMeElement, dragToElement).build().perform();	  	  
}

	  @Test(priority=14)
	  public void periodicElements(){
	  //Verify periodic Elements
	  List<WebElement> periodicElements = driver.findElements(By.cssSelector("#periodicElement"));
	  System.out.println(periodicElements.size());
	  //WebElement clockElement = driver.findElement(By.cssSelector("#clock"));
	  System.out.println(periodicElements.get(0).isDisplayed());
	  //Assert.assertTrue(periodicElements.isEmpty());
	 /* 
	  for (int i=40; i<10; i++)
	  {
		  
	  //WebDriverWait wdWait = new WebDriverWait(driver, 5);
	  //wdWait.until(ExpectedConditions.elementToBeClickable(element).v.visibilityOf(periodicElements.add(i, element);.get(i)));
	  System.out.println(periodicElements.get(i).getText());
	  System.out.println("Seconds remaining: "+(36-4*i));
	  Assert.assertTrue(periodicElements.get(i).getText().equals("Element"+i));
	  Assert.assertTrue(clockElement.getText().equals("Seconds remaining: "+(36-4*i)));
	  }
	  */
	  
	  }
	  
	  
	  
	  @Test(priority=15)
	  public void verifyTitle(){	  	  	  
	  //Verify Page Title and Form Header Title
	  		String homePageTitle = driver.getTitle();
			String formHeaderTitle = driver.findElement(By.cssSelector(".wpb_wrapper>h2")).getText();
						
			try {
			Assert.assertTrue(homePageTitle.contains(Util.HOMEPAGE_TITLE));
			Assert.assertTrue(formHeaderTitle.contains(Util.FORM_HEADER));
			}
			catch (Error e) {
				verificationErrors.append(e.toString());
				System.out.println(verificationErrors);
			}	
			  
  }
 	 
  @AfterTest
  public void closeBrowser() {
	  driver.quit();
  }

}
