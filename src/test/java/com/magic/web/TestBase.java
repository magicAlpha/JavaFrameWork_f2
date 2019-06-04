package com.magic.web;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.magic.utils.SendEmailUtil;
import com.magic.utils.GenericUtil;
import com.magic.utils.SeleniumUtil;

public class TestBase {

	public static WebDriver driver;
	public static WebDriverWait wait;
	public static Capabilities cap;
	
	String hostName = com.magic.utils.SeleniumUtil.fetchUserDetails("HOST_NAME");
	String portID = SeleniumUtil.fetchUserDetails("PORT_ID");
	String senderEmail = SeleniumUtil.fetchUserDetails("SENDER_EMAIL_ID");
	String senderPassword = SeleniumUtil.fetchUserDetails("SENDER_EMAIL_PASSWORD");
	String receiverEmail = SeleniumUtil.fetchUserDetails("RECEIVER_EMAIL_ID");
	String ccEmail = SeleniumUtil.fetchUserDetails("CC_EMAIL_ID");
	private static final int WAIT_SECONDS = Integer.valueOf(SeleniumUtil.fetchUserDetails("GLOBAL_WAIT"));
	private static final String ENVIRONMENT = SeleniumUtil.fetchUserDetails("PRODUCT_URL");
	
	String [] receiverEmailArray = receiverEmail.split(";");
	String [] ccEmailArray = ccEmail.split(";");

	static String reportPath = System.getProperty("user.dir")+"/Report/MAGIC_QA_AUTOMATION_REPORT.html";

	public static ExtentReports extent;
	public static ExtentTest test;

	@BeforeSuite
	public static void beforeSuite() throws MalformedURLException, Exception {

		GenericUtil genericUtil = new GenericUtil();
		genericUtil.cleanUpFolder("FailedTestsScreenshots");
		genericUtil.cleanUpFolder("Report");
		extent = createInstance(reportPath);
		
		if(System.getProperty("os.name").contains("Linux")) {
			driver = getLinuxDriver();
		}else if(System.getProperty("os.name").contains("Windows")) {
			driver = getWindowsDriver();
		}else {
			System.out.println("Invalid OS");
		}
		cap = ((RemoteWebDriver) driver).getCapabilities();
		//driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(WAIT_SECONDS, TimeUnit.SECONDS);
		driver.get(ENVIRONMENT);
	}

	public static WebDriver getLinuxDriver() throws MalformedURLException, Exception{
		new DesiredCapabilities();
		URL serverurl = new URL("http://localhost:9515");
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		driver = new RemoteWebDriver(serverurl,capabilities);
		return driver;
	}

	public static WebDriver getWindowsDriver() throws MalformedURLException, Exception{
		System.setProperty("webdriver.chrome.driver",".\\Resources\\Drivers\\chromedriver.exe");
		DesiredCapabilities caps = DesiredCapabilities.chrome();
		LoggingPreferences logPrefs = new LoggingPreferences();
		logPrefs.enable(LogType.BROWSER, Level.ALL);
		caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
		driver = new ChromeDriver(caps);
		return driver;
	}


	@AfterMethod
	public synchronized  void afterMethod(ITestResult result) throws Exception {
		
		if (result.getStatus() == ITestResult.FAILURE){
			test.log(Status.FAIL, MarkupHelper.createLabel(result.getName()+": Test Case Failed due to below issues: ", ExtentColor.RED));
        	test.fail(result.getThrowable());
        	String screenShotPath = SeleniumUtil.getScreenshot(driver, result.getName());
        	test.addScreenCaptureFromPath(screenShotPath);
		}
        else if (result.getStatus() == ITestResult.SKIP){
        	
        	test.log(Status.SKIP, MarkupHelper.createLabel(result.getName()+": Test Case Skipped: ", ExtentColor.ORANGE));
            test.skip(result.getThrowable());}
        else{
        	test.log(Status.PASS, MarkupHelper.createLabel(result.getName()+": Test Case Passed: ", ExtentColor.GREEN));
            test.pass("Test passed");}

	}


	@AfterSuite
	public void generateReport() throws Exception {
		extent.setSystemInfo("Browser", cap.getBrowserName());
		extent.setSystemInfo("Browser Version", cap.getVersion());
		extent.flush();
		
		driver.quit();

		SendEmailUtil.SendEmailNow(hostName, portID, senderEmail, senderPassword, receiverEmailArray, ccEmailArray, 0, reportPath);

	}



	public static ExtentReports createInstance(String fileName) {
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setTheme(Theme.STANDARD);
		htmlReporter.config().setDocumentTitle("MAGIC SOFTWARE AUTOMATION REPORT");
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setReportName("MAGIC-QA AUTOMATION REPORT");

		extent = new ExtentReports();
		extent.setSystemInfo("OS Name:", System.getProperty("os.name"));
		extent.setSystemInfo("OS Version:", System.getProperty("os.version"));
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.attachReporter(htmlReporter);

		return extent;
	}

}
