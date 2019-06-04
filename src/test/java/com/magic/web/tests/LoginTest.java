package com.magic.web.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.magic.pages.DashboardPage;
import com.magic.pages.LoginPage;
import com.magic.utils.GenericUtil;
import com.magic.utils.SeleniumUtil;
import com.magic.web.TestBase;

public class LoginTest extends TestBase{
	
	String username = SeleniumUtil.fetchUserDetails("USERNAME");
	String password = SeleniumUtil.fetchUserDetails("PASSWORD");
	//String server = SeleniumUtil.fetchUserDetails("SERVER");
	String expectedProfileName = SeleniumUtil.fetchUserDetails("PROFILENAME");
	
	@Test
	public void loginTest() throws Exception {
		
		test = extent.createTest("Login Verification","This test verify whether the user is able to login or not");
		test.assignCategory("Login");
		
		System.out.println(GenericUtil.getCurrentTimeMinusSecond());
		
		LoginPage loginPage = new LoginPage(driver);
		loginPage.loginUser(username, password);
		
		DashboardPage dashboardPage = new DashboardPage(driver);
		dashboardPage.clickProfileIcon();
		String profileName = dashboardPage.getProfileName();
		test.info("Profile name appearing on the dashboard page is: "+profileName);		
		Assert.assertEquals(profileName, expectedProfileName);	
	}

}
