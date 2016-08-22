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
import java.util.LinkedHashSet;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
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

public class ValidateAllLinks<isLinkBroken> {
	
	WebDriver driver;
	int noOfLinks;
	int noOfValidLinks;
	List<String> allLinks;
	List<String> allValidLinks = new ArrayList<String>();	
	
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
	
	@Test(dataProvider = "getLinkData", dependsOnMethods= "allLinksInPage")
	  public void validateLinks(String url) {
		  try 
	    	{
			  URL linkURL = new URL(url);
			  String response = isLinkBroken(linkURL);
			  Boolean success = response.contains("OK");
			  if (success)
			  {
			  Assert.assertTrue(response.contains("OK"));
			  System.out.println("URL: " + url + " returned " + response);
			  if (url.contains(Util.URL_PART))
			  	{
				  allValidLinks.add(url);
				  noOfValidLinks = allValidLinks.size();				  
			  	}			  
			  }
			  else
			  {
		      System.out.println("URL: " + url + " returned " + response);	
			  }
	    	}
	    	catch(Exception exp)
	    	{
	    		System.out.println("URL: " + url + " Exception occured " + exp.getMessage());	    		
	    	} 
	  }
	
	@Test (dependsOnMethods= "validateLinks")
	public void linksInPage() {	
		List<String> finalList1 = new ArrayList<String>();
		List<String> finalList2 = new ArrayList<String>();
			for( String validUrl : allValidLinks)
			{
				try
				{
					//System.out.println(validUrl);
					driver.get(validUrl);			
					finalList1 = findAllLinks(driver);
					if (finalList1 != null)
					{
					finalList2.addAll(finalList1);
					//System.out.println(finalList2.size());
					}
				}
				catch (StaleElementReferenceException e)
				{
				       e.toString();
				       System.out.println("Trying to recover from a stale element :" + e.getMessage());				       
				}
			}
			System.out.println(finalList2.size());
			allLinks = new ArrayList<String>(new LinkedHashSet<String>(finalList2));		
			noOfLinks = allLinks.size();
			System.out.println(noOfLinks);
			
			for( int i=0; i<noOfLinks; i++)  
		    {
				String url = allLinks.get(i);
				//System.out.println(url);
				try 
		    	{
				  URL linkURL = new URL(url);
				  String response = isLinkBroken(linkURL);
				  Boolean success = response.contains("OK");
				  if (success)
				  {
				  Assert.assertTrue(response.contains("OK"));
				  System.out.println("URL: " + url + " returned " + response);
				  }
				  else
				  {
			      System.out.println("URL: " + url + " returned " + response);	
				  }
		    	}
		    	catch(Exception exp)
		    	{
		    		System.out.println("URL: " + url + " Exception occured " + exp.getMessage());	    		
		    	}
		    }	
					    
	  }
 
  public static List<String> findAllLinks(WebDriver driver) 
  {
	  //System.out.println("in the findAllLinks method");
	  List<WebElement> elementList = driver.findElements(By.tagName("a")); 
	  elementList.addAll(driver.findElements(By.tagName("img")));	  
	  
	  List<String> finalList1 = new ArrayList<String>();
	  
	  for (WebElement element : elementList) 
	  {
		  String link = element.getAttribute("href");
		  if(link != null) 
		  {			  
			  finalList1.add(link);
			  //System.out.println(element.getAttribute("href"));
		  } 
	  }
	  //System.out.println("before " + finalList1.size());
	  //System.out.println(finalList1);
	  List<String> finalList = new ArrayList<String>(new LinkedHashSet<String>(finalList1));

	    // List li2 = new ArrayList(new LinkedHashSet(li));  
	  
	  //System.out.println("after " + finalList.size());
	  //System.out.println(finalList);
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
			  data[i][0] = allLinks.get(i);
		    }		
		
		return data;
		}
	  
	  @DataProvider
	  public Object[][] getValidLink()
		{
		  
		  Object data[][] = new Object[noOfValidLinks][1];
		  
		  for( int i=0; i<noOfValidLinks; i++)  
		    {
			  data[i][0] = allValidLinks.get(i);
		  }		  
		return data;
		}
	  
	  @AfterTest
	  public void closeBrowser()
	  {
		  //driver.quit();
	  }
 	}