package com.magic.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.magic.utils.SeleniumUtil;

public class SearchHotelPage {

	private WebDriver driver;
	private static final int WAIT_SECONDS = Integer.valueOf(SeleniumUtil.fetchUserDetails("GLOBAL_WAIT"));
	private static final By HOTELNAME_TEXTBOX = By.xpath("//span[contains(text(),'Search by Hotel or City Name')]");
	private static final By HOTELNAME_TEXTBOX_DATA = By.xpath("//div[@id='select2-drop']//input[contains(@class,'select2-input')]");
	private static final By HOTELNAME_DROPDOWN = By.xpath("//*[@class='select2-result-sub']/li[1]");
	private static final By CHECKIN_DATE = By.xpath("//div[@id='dpd1']//input[@placeholder='Check in']");
	private static final By CHECKOUT_DATE = By.xpath("//input[@placeholder='Check out']");
	private static final By CURRENT_DATE = By.xpath("(//*[@class='datepicker-days']//td[@class='day  active'])[1]");
	private static final By SEARCH_BUTTON = By.xpath("//button[@class='btn btn-lg btn-block btn-primary pfb0 loader']");

	public SearchHotelPage (WebDriver driver) {
		this.driver = driver;
	}

	public void enterHotelName(String hotelName){
		SeleniumUtil.waitForElementClickable(driver, HOTELNAME_TEXTBOX, WAIT_SECONDS).click();		
		driver.findElement(HOTELNAME_TEXTBOX_DATA).sendKeys(hotelName);
		SeleniumUtil.waitForElementClickable(driver, HOTELNAME_DROPDOWN, WAIT_SECONDS).click();
	}

	public void enterCheckInDate(String checkInDate){
		SeleniumUtil.waitForElementClickable(driver, CHECKIN_DATE, WAIT_SECONDS).click();
		SeleniumUtil.waitForElementVisibility(driver, CURRENT_DATE, WAIT_SECONDS).click();
	}

	public void enterCheckOutDate(String checkOutDate){
		SeleniumUtil.waitForElementClickable(driver, CHECKOUT_DATE, WAIT_SECONDS).click();	
		//SeleniumUtil.waitForElementVisibility(driver, CURRENT_DATE, WAIT_SECONDS).click();
	}

	public void clickSearchButton(){
		SeleniumUtil.waitForElementClickable(driver, SEARCH_BUTTON, WAIT_SECONDS).click();	
	}

}
