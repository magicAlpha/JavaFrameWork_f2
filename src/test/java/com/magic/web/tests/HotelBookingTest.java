package com.magic.web.tests;

import org.testng.annotations.Test;

import com.magic.pages.DashboardPage;
import com.magic.pages.SearchHotelPage;
import com.magic.web.TestBase;

public class HotelBookingTest extends TestBase{
	
	
	@Test(priority = 1)
	public void searchHotelTest() throws InterruptedException {
	
		test = extent.createTest("Search Hotel","This test verifies whether user is able to Search the Hotel or not");
		test.assignCategory("Search Hotel Test");
		
		try {
			DashboardPage dashboardPage = new DashboardPage(driver);
			dashboardPage.clickHotelIcon();
			
			SearchHotelPage hotelPage = new SearchHotelPage(driver);
			hotelPage.enterHotelName("Pacific City");
			hotelPage.enterCheckInDate("");
			hotelPage.enterCheckOutDate("");
			hotelPage.clickSearchButton();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);
		}	
	}

}
