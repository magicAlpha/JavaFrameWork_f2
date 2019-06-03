package com.magic.web.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.magic.web.TestBase;

public class FailTest extends TestBase{

	@Test
	public void failTest(){
		test = extent.createTest("Fail Verification","This test verify fail functionality");
		test.assignCategory("Failed Test");
		Assert.fail();
	}
}
