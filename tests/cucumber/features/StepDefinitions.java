package cucumber.features;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;



import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class StepDefinitions {
	WebDriver driver = null;
	ArrayList<String> tabs;
	JavascriptExecutor js;
	List<List<String>> storeData;
	List<List<String>> loginData;
	List<List<String>> chatData;
	long start,finish,totalTime;
	Actions action; 
	@Before
	public void setUp() throws Exception {
	    driver = new FirefoxDriver();
	    js = (JavascriptExecutor) driver;
	    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  }
	
	@After
	 public void tearDown() throws Exception {
	    driver.quit();
	    }
	
	
	
	
	@Given("^I navigate to sears website$")
	public void navigate() throws Throwable {
		start = System.currentTimeMillis();
		driver.get("http://www.sears.com/");
	}
	
	
	
	//Search feature
	//([^\"]*)
	@When("^I enter an ([^\\*]*)$")
	public void enterItem(String item) throws Throwable {
		 driver.findElement(By.id("keyword")).clear();
		    driver.findElement(By.id("keyword")).sendKeys(item);
	}

	@When("^I click on search button$")
	public void clickOnSearch() throws Throwable {
		driver.findElement(By.id("goBtn")).click();
	   
	}

	@Then("^I should be able to see the description of the ([^\"]*)$")
	public void validateDescription(String item) throws Throwable {
		String testString=driver.findElement(By.xpath("//div[@id='cards-holder']/div[@class='card-container ng-scope compare-card']/div[@class='card-bottom']/div[@class='card-title']/h2/a")).getText();
		assertTrue(driver.getPageSource().contains(testString));
		js.executeScript("alert('Testing :"+testString+"');");
		assertEquals(item, testString);
	    Thread.sleep(2500);


	}
	
	
	//Wish list
	@When("^I am logged in$")
	public void logIn(DataTable table) throws Throwable {
			loginData = table.raw();
			driver.findElement(By.id("signInLink")).click();
			WebElement customerFrame = driver.findElement(By.xpath("//div[@id='modalIframe']/iframe"));
	    	driver.switchTo().frame(customerFrame);
		    // ERROR: Caught exception [ERROR: Unsupported command [selectFrame | easyXDM_default5398_provider | ]]
		    driver.findElement(By.id("email")).clear();
		    driver.findElement(By.id("email")).sendKeys(loginData.get(1).get(0));
		    //driver.findElement(By.id("email")).sendKeys("selenium501@gmail.com");
		    driver.findElement(By.id("password")).click();
		    driver.findElement(By.id("password")).clear();
		    driver.findElement(By.id("password")).sendKeys(loginData.get(1).get(1));
		    //driver.findElement(By.id("password")).sendKeys("selenium501");
		  
		    driver.findElement(By.xpath("//button[@class='shcBtn shcBtnCTA signIn']")).click();
	}

	@When("^I add an item to wishlist$")
	public void addItemToWishList() throws Throwable {
	    driver.switchTo().parentFrame();
	    driver.findElement(By.linkText("Electronics")).click();
	    Thread.sleep(2000);
	    assertEquals("TVs & Electronics: Get the Best Electronics and Gadgets at Sears", driver.getTitle());
	    //driver.findElement(By.xpath("(//a[contains(text(),'Headphones')])[2]")).click();
	    driver.findElement(By.linkText("Headphones")).click();
	    Thread.sleep(5000);
	    action = new Actions(driver);
	    Thread.sleep(1000);
	    action.sendKeys(Keys.PAGE_DOWN).sendKeys(Keys.PAGE_DOWN).sendKeys(Keys.PAGE_DOWN).perform();
	    Thread.sleep(2000);
	    driver.findElement(By.linkText("JVC Wireless Headphones")).click();
	    Thread.sleep(5000);
	}

	@Then("^I should be able to see that item in my profile$")
	public void validateItem() throws Throwable {
		
		action.sendKeys(Keys.PAGE_DOWN).sendKeys(Keys.PAGE_DOWN).sendKeys(Keys.PAGE_DOWN).perform();
	    action.sendKeys(Keys.PAGE_UP).sendKeys(Keys.PAGE_UP).perform();
	    //driver.findElement(By.xpath("//div[@id='page-container']/div/div[2]/aside/div/figure[6]")).click();
	    
	    driver.findElement(By.xpath("//div[@class='share-and-save']/div[@class='add_to_list']")).click();
	    driver.findElement(By.linkText("add to list")).click();;
	    driver.findElement(By.xpath("//a[@id='addWL']")).click();
		Thread.sleep(2500);
	    driver.findElement(By.id("myProfile")).click();
	    driver.findElement(By.id("myXProfile")).click();
	    driver.findElement(By.xpath("(//a[contains(text(),'Wishlists')])[2]")).click();
	    Thread.sleep(500);
	    assertTrue(driver.getPageSource().toLowerCase().contains("jvc")); 
	}

	
	
	//Store Locator
	@When("^I click on store locator link$")
	public void clickOnStoreLocator() throws Throwable {
		driver.findElement(By.linkText("Store Locator")).click();
	}
	@When("^I enter city and state$")
	public void enterCityAndState(DataTable table) throws Throwable {
			storeData = table.raw();
			Thread.sleep(1000);
			driver.findElement(By.id("ssCity")).clear();
		    driver.findElement(By.id("ssCity")).sendKeys(storeData.get(1).get(0));
		    new Select(driver.findElement(By.xpath("//select[@id='ssCity']"))).selectByVisibleText(storeData.get(1).get(1));
	}

	@When("^I click on search$")
	public void clickOnStoreSearch() throws Throwable {
		  driver.findElement(By.xpath("//div[@id='seoLocalMain']/div[2]/div[2]/div/div/form/button")).click();
		    
	}
	@Then("^I should be able to see a store located in my city and state$")
	public void validateStore() throws Throwable {
			Thread.sleep(1000);
			assertTrue((driver.getPageSource().toLowerCase().trim().contains(storeData.get(1).get(0).toLowerCase())) 
					&& (driver.getPageSource().toLowerCase().trim().contains(storeData.get(1).get(1).toLowerCase())));
			//assertTrue((driver.getPageSource().toLowerCase().trim().contains("bloomington")) && (driver.getPageSource().toLowerCase().trim().contains("il")));
	}
	
	//Chat
	@When("^I click on the chat link$")
	public void clickOnChatLink() throws Throwable {
		   driver.findElement(By.linkText("Chat")).click();
	}
	@When("^I enter name and email id$")
	public void enterNameAndEmail(DataTable table) throws Throwable {
			chatData = table.raw();
		 	tabs = new ArrayList<String> (driver.getWindowHandles());
			driver.switchTo().window(tabs.get(1));
			driver.switchTo().frame("initialtextFrame");
		    driver.findElement(By.id("q1")).clear();
		    //driver.findElement(By.id("q1")).sendKeys("Selenium");
		    driver.findElement(By.id("q1")).sendKeys(chatData.get(1).get(0));
		    driver.findElement(By.id("q2")).clear();
		    driver.findElement(By.id("q2")).sendKeys(chatData.get(1).get(1));
		    driver.findElement(By.id("q5r0")).click();
	}

	@When("^I click on start chat$")
	public void clickOnStartChat() throws Throwable {
		 	driver.switchTo().defaultContent();
		    driver.findElement(By.cssSelector("img[alt=\"Start chat\"]")).click();
		    Thread.sleep(10000);
		    
	}
	@Then("^I should be able to chat with the customer support and get a response$")
	public void checkResponse() throws Throwable {
		 	driver.findElement(By.id("mytext")).clear();
		    driver.findElement(By.id("mytext")).sendKeys("Hi");
		    driver.findElement(By.cssSelector("img[alt=\"Send text\"]")).click();
		    Thread.sleep(20000);
		    driver.switchTo().frame("textFrame");
		    assertTrue(driver.getPageSource().toLowerCase().contains("how") || driver.getPageSource().toLowerCase().contains("hello") || driver.getPageSource().toLowerCase().contains("help"));
		    driver.switchTo().defaultContent();
		    assertEquals("Chat Window", driver.getTitle());
		    Thread.sleep(10000);
		    driver.findElement(By.cssSelector("img[alt=\"Close chat\"]")).click();
			driver.switchTo().window(tabs.get(0));
			driver.close();
	}
	
	//	Compare
	@When("^I select item 1 to compare$")
	public void selectItem1() throws Throwable {
		 driver.findElement(By.linkText("Electronics")).click();
		    Thread.sleep(3000);
		    assertEquals("TVs & Electronics: Get the Best Electronics and Gadgets at Sears", driver.getTitle());
		    driver.findElement(By.linkText("Televisions")).click();
		    Thread.sleep(3000);
		    driver.findElement(By.xpath("//div[9]/div/div[2]/div[4]/span")).click();
		    Thread.sleep(3000);
		    
	}
	@When("^I select item 2 to compare$")
	public void selectitem2() throws Throwable {
		 	driver.findElement(By.xpath("//div[2]/div[2]/div[4]/span")).click();
		    Thread.sleep(3000);
		   
	}

	@When("^I click on compare now$")
	public void clickOnCompareNow() throws Throwable {
		driver.findElement(By.xpath("//div[@id='cards-holder']/div[2]/div[2]/div[4]/p[2]")).click();
	    Thread.sleep(3000);
		    
	}
	@Then("^I should be able to see the details of both the items$")
	public void verifyDetails() throws Throwable {
		assertTrue(driver.getPageSource().toLowerCase().contains("main features") && driver.getPageSource().toLowerCase().contains("dimensions and weight") 
	    		&& driver.getPageSource().toLowerCase().contains("product overview"));
	}
	
	
	//Response time
	
	@Then("^the page should be loaded within 6 seconds$")
	public void checkResponseTime() throws Throwable {
		String waitedForElement =	driver.findElement(By.xpath("//span[@class='shcHpHeadlineBold']")).getText();
		finish = System.currentTimeMillis();
		js.executeScript("alert('Time stopped after getting element :"+waitedForElement+"');");
		Thread.sleep(1000);
		driver.switchTo().alert().accept();
		totalTime = finish - start; 
		assertTrue(totalTime < 6000); 

	}
	
}
