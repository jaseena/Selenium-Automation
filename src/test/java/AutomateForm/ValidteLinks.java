package AutomateForm;

//************************************************************************//
//Testcase 1. Finding all the links on the page.
//	- All the links are in image <img /> and anchor tags <a/> on a web page. 
//	- All links are mentioned as href attribute of the element tag.
//	- Filter out elements that don’t have href attributes.

//Testcase 2. Iteratively checking the links if they are broken.
//	- Use  HttpURLConnection Java class. 
//	This class is used to  make HTTP requests to the webserver hosting the links.
//************************************************************************//

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ValidteLinks<isLinkBroken> {
	
	WebDriver driver;
	int noOfLinks;
	List<WebElement> allLinks;
	
	@BeforeTest
	  public void openBrowser() {
		
		// This method will read initialization parameters from the class Util.java & launch Firefox
		
				File pathToBinary = new File(Util.FIREFOX_PATH);
				FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
				FirefoxProfile ffProfile = new FirefoxProfile();
				
				//Step 1. Launch Firefox Browser
				driver = new FirefoxDriver(ffBinary, ffProfile);
								
				driver.manage().window().maximize();				
				
				// Step 2. Goto the url: http://www.seleniumframework.com/Practiceform/
				driver.get(Util.BASE_URL);
				}

	@Test
	public void allLinksInPage() {  
 
		    allLinks = findAllLinks(driver);
		    noOfLinks = allLinks.size();		    		    
	}
	
	@Test(dataProvider = "getLinkData")
	  public void validateLinks(WebElement element) {
		  try 
	    	{
			  String url = element.getAttribute("href");
			  URL linkURL = new URL(url);
			  String response = isLinkBroken(linkURL);
			  Boolean success = response.contains("OK");
			  if (success)
			  {
			  Assert.assertTrue(response.contains("OK"));
			  }
			  else
			  {
		      System.out.println("URL: " + url + " returned " + response);	
			  }
	    	}

	    	catch(Exception exp)
	    	{
	    		System.out.println("At " + element.getAttribute("innerHTML") + " Exception occured -&gt; " + exp.getMessage());	    		
	    	} 
	  }	 
 
  public static List<WebElement> findAllLinks(WebDriver driver) 
  {
	  List<WebElement> elementList = driver.findElements(By.tagName("a")); 
	  elementList.addAll(driver.findElements(By.tagName("img")));
	  List<WebElement> finalList = new ArrayList<WebElement>(); ;
	  
	  for (WebElement element : elementList) 
	  { 
		  if(element.getAttribute("href") != null) 
		  { 
			  finalList.add(element); 
		  } 
	  }
	  return finalList;
  }
  
  
	public static String isLinkBroken(URL url) throws Exception 
	{
 
		String response = "";
 
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
 
		try 
		{ 
		    connection.connect(); 
		    response = connection.getResponseMessage(); 
		    connection.disconnect(); 
		    return response; 
		}
 
		catch(Exception exp) 
		{ 
			return exp.getMessage(); 
		}  				
 
	}
 
 
	  @DataProvider
	  public Object[][] getLinkData()
		{
		  Object data[][] = new Object[noOfLinks][1];
		  for( int i=0; i<noOfLinks; i++)  
		    {
			  data[i][0] =  allLinks.get(i);
		    }		
		
		return data;
		}
	  
	  @AfterTest
	  public void closeBrowser()
	  {
		  driver.quit();
	  }
 	}