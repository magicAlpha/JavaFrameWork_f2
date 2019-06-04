package com.magic.web.tests;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class DateClick {

	String url = "https://www.tripiflights.com/";
	WebDriver driver;	
	WebDriverWait wait;
	Actions action;
	int enterNumberOfMonth=3;
	Logger log;
	
	@BeforeTest
	public void OpenBrowser() {
		try {
			
			log = Logger.getLogger("devpinoyLogger");
			PropertyConfigurator
					.configure(System.getProperty("user.dir") + "/src/main/java/com/magic/Config/log4j.properties");
			String driverPath = System.getProperty("user.dir") + "/Resources/Drivers/chromedriver.exe";
			log.info("Setting browser properties");
			System.setProperty("webdriver.chrome.driver", driverPath);
			log.info("Instatiating browser driver");
			driver = new ChromeDriver();
			driver.manage().window().maximize();			
			driver.manage().timeouts().implicitlyWait(200, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(200, TimeUnit.SECONDS);
			log.info("Hitting URL : --> " + url);
			driver.get(url);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception happen !!!!!", e);
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
			System.out.println(e.getCause());
		}
	}

	@Test
	public void ClickOnDate() {
		try {

			// elements of UI
			WebElement txtDepartingDate = driver.findElement(By.xpath("//input[@id='txtDepartingDate']"));
			WebElement txtReturnDate = driver.findElement(By.xpath("//input[@id='txtReturnDate']"));
			//WebElement nextDate = driver.findElement(By.xpath("//a[@title='Next']"));

			// Wait for the element to be click for 20 seconds and will click on Dapart date
			wait = new WebDriverWait(driver, 20);
			wait.until(ExpectedConditions.elementToBeClickable(txtDepartingDate));
			log.info("selecting departing date");
			txtDepartingDate.click();			
			log.info("selecting month");
			for(int i=0;i<enterNumberOfMonth;i++)
			{				
				action =new Actions(driver);
				action.moveToElement(driver.findElement(By.xpath("//a[@title='Next']"))).build().perform();				
				driver.findElement(By.xpath("//a[@title='Next']")).click();
			}
			
			log.info("Will click on 18 day of the month");
			String selectDayToBeClick="18";
			int n2=Integer.parseInt(selectDayToBeClick);
			List<WebElement> dayNumber=driver.findElements(By.xpath("//div[@class='ui-datepicker-group ui-datepicker-group-last']//table//tbody//tr//td[@data-handler='selectDay']//a"));
			int totalNumberOfDay=dayNumber.size();
			
			for(int i=0;i<totalNumberOfDay;i++)
			{
				WebElement element=dayNumber.get(i);
				String day=element.getText();
				int n1=Integer.parseInt(day);
				
				if(n2==n1);
				{
					log.info("Clicking on month date");
					element.click();					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception happen !!!!!", e);
			System.out.println(e.getCause());
			System.out.println(e.getStackTrace());
			System.out.println(e.getCause());
		}
	}
	
	@AfterTest
	public void KillingBrowser()
	{
		try {
			log.info("Closing browser driver session");
			if(driver !=null)
			{
				driver.quit();
			}
			
		} catch (Exception e) {
			log.error("Exception happen !!!!!", e);
		}
	}

}
