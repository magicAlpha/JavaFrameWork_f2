package com.magic.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.magic.utils.SeleniumUtil;

public class DashboardPage {
	
	private WebDriver driver;
	static String profileName = SeleniumUtil.fetchUserDetails("PROFILENAME");
	private static final int WAIT_SECONDS = Integer.valueOf(SeleniumUtil.fetchUserDetails("GLOBAL_WAIT"));
	private static final By PROFILE_ICON = By.xpath("//ul[@class='nav navbar-nav navbar-right hidden-sm go-left']//a[@class='dropdown-toggle go-text-right'][contains(text(),'"+profileName+"')]");
	private static final By PROFILE_NAME = By.xpath("//h3[@class='RTL']");
	private static final By HOTELS_ICON = By.xpath("//span[contains(text(),'Hotels')]");
	
	
	public DashboardPage (WebDriver driver) {
        this.driver = driver;
	}
	
	public void clickProfileIcon() {
		SeleniumUtil.waitForElementClickable(driver, PROFILE_ICON, WAIT_SECONDS).click();		
	}
	
	public void clickHotelIcon() {
		SeleniumUtil.waitForElementClickable(driver, HOTELS_ICON, WAIT_SECONDS).click();		
	}
	
	public String getProfileName() {
		String profileName = SeleniumUtil.waitForElementPresence(driver, PROFILE_NAME, WAIT_SECONDS).getText();
		String[] nameArray = profileName.split(",");
		String[] profileValue = nameArray[1].split(" ");
		return profileValue[1];
	}
		
	public void selectMenuWithParameter(String menuName) {
		By MENU_WITH_PARAMETER = By.xpath("//span[text()='"+menuName+"']");
		SeleniumUtil.waitForElementPresence(driver, MENU_WITH_PARAMETER, WAIT_SECONDS).click();
	}
}
