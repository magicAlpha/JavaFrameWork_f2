package com.magic.utils;

import java.awt.AWTException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


public class SeleniumUtil {

	public static void turnOffImplicitWaits(WebDriver driver) {
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
	}

	public static void turnOnImplicitWaits(WebDriver driver) {
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}

	public static final WebElement waitForElementVisibility(WebDriver driver, By findByCondition, int waitInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, waitInSeconds);
		wait.until(ExpectedConditions.visibilityOfElementLocated(findByCondition));
		return driver.findElement(findByCondition);
	}

	public static final WebElement waitForElementPresence(WebDriver driver, By findByCondition, int waitInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, waitInSeconds);
		wait.until(ExpectedConditions.presenceOfElementLocated(findByCondition));
		return driver.findElement(findByCondition);
	}

	public static final WebElement waitForElementClickable(WebDriver driver, By findByCondition, int waitInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, waitInSeconds);
		wait.until(ExpectedConditions.elementToBeClickable(findByCondition));
		return driver.findElement(findByCondition);
	}

	public static int countWindow(WebDriver driver) {
		Set<String> windowHandle = driver.getWindowHandles();
		int count = windowHandle.size();
		return count;
	}

	public static void switchWindowByIndex(WebDriver driver,int windowno)
	{
		Set<String> window=driver.getWindowHandles();
		driver.switchTo().window(window.toArray()[windowno].toString());
	}

	public static void closeCurrentBrowserTab(WebDriver driver){
		driver.close();
	}

	public static String fetchUserDetails(String key){

		FileInputStream file = null;
		try {
			file = new FileInputStream("./Properties/details.properties");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Properties property = new Properties();
		try {
			property.load(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return property.getProperty(key);
	}

	public static long getLoadTimeInSeconds(WebDriver driver, By waitForLoadElement,int waitInSeconds){

		StopWatch pageLoad = new StopWatch();
		pageLoad.start();
		SeleniumUtil.waitForElementVisibility(driver, waitForLoadElement, waitInSeconds);
		pageLoad.stop();

		long pageLoadTime_ms = pageLoad.getTime();
		long pageLoadTime_Seconds = pageLoadTime_ms / 1000;
		System.out.println("Total Loading Time in milliseconds: " + pageLoadTime_ms);
		System.out.println("Total Loading Time in seconds: " + pageLoadTime_Seconds);

		return pageLoadTime_Seconds;
	}

	public static WebElement scrollToViewElement(WebDriver driver, By findByCondition){
		WebElement element = driver.findElement(findByCondition);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		return driver.findElement(findByCondition);
	}

	public static void scrollUp(WebDriver driver) throws InterruptedException, AWTException{
		Thread.sleep(2000);
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,-250)", "");
	}

	public static boolean isElementPresent(WebDriver driver,By locator){
		turnOffImplicitWaits(driver);
		boolean result = false;
		try {
			driver.findElement(locator).isDisplayed();
			result = true;
		} catch (Exception e) {
			turnOnImplicitWaits(driver);
		}finally {
			turnOnImplicitWaits(driver);
		}
		return result;
	}

	public static void hoverElement(WebDriver driver, By locator) {
		Actions action = new Actions(driver);
		WebElement we = driver.findElement(locator);
		action.moveToElement(we).build().perform();
	}

	public static String getScreenshot(WebDriver driver, String screenshotName) throws Exception {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "/FailedTestsScreenshots/" + screenshotName + ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}

	public static void actionScrollToBottom(WebDriver driver) {
		Actions actions = new Actions(driver);
		for(int i=1;i<=100;i++) {
			actions.sendKeys(Keys.PAGE_DOWN).perform();
		}
	}

	public static void actionScrollToTop(WebDriver driver) {

		Actions actions = new Actions(driver);

		for(int i=1;i<=100;i++) {

			actions.sendKeys(Keys.PAGE_UP).perform();
		}

	}

	public static void selectDropDownByValue(WebDriver driver, By locator, String value) {
		Select dropdown = new Select(driver.findElement(locator));
		dropdown.selectByValue(value);
	}

	public static void refreshPage(WebDriver driver) {
		driver.navigate().refresh();
	}

	public static ArrayList getfileNamesFromFolder(String path) {

		ArrayList listOfFilesArray = new ArrayList();

		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				System.out.println("File " + listOfFiles[i].getName());
				listOfFilesArray.add(listOfFiles[i].getName());
			} else if (listOfFiles[i].isDirectory()) {
				System.out.println("Directory " + listOfFiles[i].getName());
			}
		}

		System.out.println(listOfFilesArray);

		return listOfFilesArray;

	}
}
