package com.magic.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import com.magic.utils.SeleniumUtil;

public class LoginPage {

	private WebDriver driver;

	private static final int WAIT_SECONDS = Integer.valueOf(SeleniumUtil.fetchUserDetails("GLOBAL_WAIT"));
	private static final By USERNAME_TEXT_FIELD = By.name("username");
	private static final By PASSWORD_TEXT_FIELD = By.name("password");
	private static final By LOGIN_BUTTON = By.xpath("//button[@class='btn btn-action btn-lg btn-block loginbtn']");
	private static final By DISMISS_COOKIE = By.xpath("//button[@aria-label='dismiss cookie message']");

	public LoginPage (WebDriver driver) {
		this.driver = driver;
	}

	public void clickLogin() {
		SeleniumUtil.waitForElementPresence(driver, LOGIN_BUTTON, WAIT_SECONDS).click();
	}

	public void typeUserName(String username) {
		SeleniumUtil.waitForElementPresence(driver, USERNAME_TEXT_FIELD, WAIT_SECONDS).sendKeys(username);
	}

	public void typePassword(String password) {
		SeleniumUtil.waitForElementPresence(driver, PASSWORD_TEXT_FIELD, WAIT_SECONDS).sendKeys(password);
	}

	public void clickDismissCookie() {
		SeleniumUtil.waitForElementVisibility(driver, DISMISS_COOKIE, WAIT_SECONDS).click();
	}
	
	public void loginUser(String username, String password) throws InterruptedException {

		//SeleniumUtil.turnOffImplicitWaits(driver);			
		try {
			clickDismissCookie();
			Thread.sleep(3000);	
			String text = driver.findElement(By.xpath("//div[@class='panel-heading go-text-right']")).getText();
			System.out.println(text);
			if(driver.findElement(By.xpath("//div[@class='panel-heading go-text-right']")).getText().equals("LOGIN")) {
				typeUserName(username);
				typePassword(password);
				clickLogin();
			}

		} catch (Exception e) {
			// TODO: handle exception			
			System.out.println("Login page is not displayed");
			System.out.println(e);
			throw e;
		}finally {
			SeleniumUtil.turnOnImplicitWaits(driver);
		}


	}

}
