package AutomateForm;

import org.testng.annotations.Test;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
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
				driver.manage().timeouts().implicitlyWait(Util.WAIT_TIME, TimeUnit.SECONDS);
				
				// Step 2. Goto the url: http://www.seleniumframework.com/Practiceform/
				driver.get(Util.BASE_URL);
				
				//Configure the Action
				  act = new Actions(driver);
	  }
	
	@BeforeMethod
	  public void clearData() {
		
		// This method will set the browser to default content				
				driver.get(Util.BASE_URL);
	  }

// Step 3. Automate all the fields
	
  @Test
  public void textArea(){			
	  
	  
	  //Text area	  
	  WebElement textArea = driver.findElement(By.cssSelector("#vfb-10"));
	  String text1 = "Hi seleniumframework user. This is textarea!!!";
	  
	  act.moveToElement(textArea).click()
	  		.keyDown(Keys.CONTROL).sendKeys("a").build().perform();
	  textArea.sendKeys(text1);
  }
  
  @Test
  public void textBox(){
	  //Text box  
	  WebElement textBox = driver.findElement(By.cssSelector("#vfb-9"));
	  String text2 = "Hi seleniumframework user. This is textbox!!!";
	  
	  act.moveToElement(textBox).click()
	  		.keyDown(Keys.CONTROL).sendKeys("a").build().perform();
	  textBox.sendKeys(text2);
  }
  
  @Test
  public void checkBox(){	  
	  //Checkbox
	  List<WebElement> checkBoxElements = driver.findElements(By.cssSelector("input[type='checkbox']"));
	  checkBoxElements.get(2).click();
	  Assert.assertTrue(checkBoxElements.get(2).isSelected());
	  checkBoxElements.get(1).click();
	  Assert.assertTrue(checkBoxElements.get(1).isSelected());	  
  }
  
  @Test
  public void radioButton(){  
	  //Radio
	  driver.findElement(By.cssSelector("#vfb-7-1")).click();
	  Assert.assertTrue(driver.findElement(By.cssSelector("#vfb-7-1")).isSelected());
  }
  
  @Test
  public void datePicker(){  
	  //Date
	  WebElement date =driver.findElement(By.cssSelector("#vfb-8"));
	  date.clear();
	  date.click();
	  driver.findElement(By.xpath(".//*[@id='ui-datepicker-div']/table/tbody/tr[5]/td[1]/a")).click();
  }
  
  @Test
  public void urlText(){	  	  
	  //URL
	  WebElement url = driver.findElement(By.cssSelector("#vfb-11"));
	  url.clear();
	  url.sendKeys("http://www.seleniumframework.com/Practiceform/");
  }
  
  @Test
  public void dropdownSelect(){	  
	  // Select
	  WebElement selectElement = driver.findElement(By.cssSelector("#vfb-12"));
	  Select dropdown = new Select(selectElement);
	  dropdown.selectByVisibleText("Option 2");
  }
  
  @Test(dataProvider = "getData")
  public void verificationTextAndSubmit(String verificationText) {
  	  //Verification Text box  
	  WebElement verificationTextBox = driver.findElement(By.cssSelector("#vfb-3"));
	  act.moveToElement(verificationTextBox).click()
	  		.keyDown(Keys.CONTROL).sendKeys("a").build().perform();
	  verificationTextBox.sendKeys(verificationText);
	  //int val = Integer.valueOf(verificationText);
	  
	  
	  //Submit
	  WebElement submit = driver.findElement(By.cssSelector("input[value='Submit']"));
	  submit.click();
	  
	  //Verify submit success message
	  String expectedSubmitSuccessMsg = "Your form was successfully submitted. Thank you for contacting us.";
	  String actualSubmitSuccessMsg = driver.findElement(By.cssSelector("#form_success")).getText();
	  Assert.assertTrue(actualSubmitSuccessMsg.contains(expectedSubmitSuccessMsg));
  }
  
  @DataProvider
  public Object[][] getData()
	{
	  Object data[][] = new Object[5][1];	  
	    data[0][0] =  "20";
	    data[1][0] = "tt";
	    data[2][0] =  "text";
	    data[3][0] = "1+";
	    data[4][0] = "0";
	return data;
	}
  
	  @Test
	  public void browser(){	  
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
	  	  
	  //Verify New Message Window button
	  //driver.findElement(By.cssSelector("button[onclick='newMsgWin()']")).click();
	  //System.out.println(driver.switchTo().alert().getText());
	  //System.out.println(driver.getTitle());
	  //driver.switchTo().alert().dismiss();
	  //System.out.println(driver.getTitle());
	  
	  //Verify New Browser Tab button
	  driver.findElement(By.cssSelector("button[onclick='newBrwTab()']")).click();
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
	  
	  
	  //Verify the nested element
	  String expectedNestedElementText = "Find me I have nothing in me!!";
	  WebElement nestedElement = driver.findElement(By.xpath(".//*[@class='wpb_wrapper']/p[4]/a[2]"));	  
	  Assert.assertTrue(nestedElement.getText().equals(expectedNestedElementText));
	  
	  //Verify random ID element
	  String expectedRandomIDElementText = "I will have random ID";
	  WebElement randomIDElement = driver.findElement(By.xpath(".//*[@class='wpb_wrapper']/p[5]"));	  
	  Assert.assertTrue(randomIDElement.getText().equals(expectedRandomIDElementText));
	  }
	  
	  @Test
	  public void alertJava(){
	  //Verify JAVA Script Alert
	  String alertMsg = "Please share this website with your friends and in your organization.";
	  WebElement alertButton = driver.findElement(By.cssSelector("#alert"));
	  alertButton.click();
	  Assert.assertTrue(driver.switchTo().alert().getText().equals(alertMsg));
	  System.out.println(driver.switchTo().alert().getText());
	  driver.switchTo().alert().accept();
	  
	  }
	  
	  @Test
	  public void timingAlertJava(){
	  //Verify JAVA Script Timing Alert
	  String timingAlertMsg = "Please share this website with your friends and in your organization.";
	  WebElement timingAlertButton = driver.findElement(By.cssSelector("#alert"));
	  timingAlertButton.click();
	  Assert.assertTrue(driver.switchTo().alert().getText().equals(timingAlertMsg));
	  System.out.println(driver.switchTo().alert().getText());
	  driver.switchTo().alert().accept();
	  
	  }
	  
	  @Test
	  public void clockTimer(){
	  //Verify clock
	  String clockText = "Seconds remaining";
	  WebElement clockElement = driver.findElement(By.cssSelector("#clock"));
	  System.out.println(clockElement.getText());
	  Assert.assertTrue(clockElement.getText().contains(clockText));
	  //Seconds remaining	  
	  }
	  
	  @Test
	  public void changeColorButton(){
	  //Verify Change Color button
	  WebElement changeColorButton = driver.findElement(By.cssSelector("#colorVar"));
	  System.out.println(changeColorButton.getAttribute("style"));	  
	  System.out.println(changeColorButton.getAttribute("style"));
	  //Assert.assertTrue(changeColorButton.getAttribute("style").equals("color: red;"));
}
	  
	  @Test
	  public void changeColorButtonOnDoubleClick(){
	  //Verify Change Color button (on double click)
	  WebElement changeColorButton1 = driver.findElement(By.cssSelector("#doubleClick"));
	  System.out.println(changeColorButton1.getAttribute("style"));	  
	  act.moveToElement(changeColorButton1).doubleClick().build().perform();
	  System.out.println(changeColorButton1.getAttribute("style"));
	  Assert.assertTrue(changeColorButton1.getAttribute("style").contains("color: orange;"));
}
	  
	  @Test
	  public void dragAndDrop(){
	  //Verify Drag and Drop
	  WebElement dragToElement = driver.findElement(By.xpath(".//*[@id='dragb']"));
	  System.out.println(dragToElement.getText());
	  WebElement dragMeElement = driver.findElement(By.xpath(".//*[@id='draga']"));
	  System.out.println(dragMeElement.getText());
	  //Actions builder = new Actions(driver);
	  
	  //Action dragAndDrop = builder.clickAndHold(dragMeElement).moveToElement(dragToElement).click().release(dragToElement).build();
	  //dragAndDrop.perform();
	  
	  act.dragAndDrop(dragMeElement, dragToElement).build().perform();
	  //act.moveToElement(dragMeElement).clickAndHold().moveToElement(dragToElement).build().perform();
	  //act.release().build().perform();
	  //act.clickAndHold(dragMeElement).moveToElement(dragToElement).
	  //release(dragMeElement).build().perform();
	  //act.
	  //System.out.println(dragMeElement.getText());
	  //act.dragAndDrop(dragToElement, dragMeElement).build().perform();
	  System.out.println(dragToElement.getText());
}
	  
	  @Test
	  public void verifyTitle(){	  	  	  
	  //Verify Page Title and Form Header Title
	  		String homePageTitle = driver.getTitle();
			String formHeaderTitle = driver.findElement(By.cssSelector(".wpb_wrapper>h2")).getText();
						
			try {
			org.testng.Assert.assertTrue(homePageTitle.contains(Util.HOMEPAGE_TITLE));
			org.testng.Assert.assertTrue(formHeaderTitle.contains(Util.FORM_HEADER));
			}
			catch (Error e) {
				verificationErrors.append(e.toString());
				System.out.println(verificationErrors);
			}
	 
	  
  }
 
  @AfterTest
  public void closeBrowser() {
	  //driver.quit();
  }

}
