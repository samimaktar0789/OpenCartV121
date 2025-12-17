package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC02_LoginTest extends BaseClass{
	
	@Test(groups={"Sanity","Master"})
	public void verify_login() {
		logger.info("**** Starting TC02_LoginTest********");
		try {
		HomePage hp=new HomePage(driver);
		hp.clickMyAccount();
		hp.clickLogin();
		
		LoginPage lg=new LoginPage(driver);
		lg.setEmail(p.getProperty("email"));
		lg.setPassword(p.getProperty("password"));
		lg.clickLoginBtn();
		
		MyAccountPage myAccount=new MyAccountPage(driver);
		boolean targetPage=myAccount.isMyAccountExists();
		
		Assert.assertTrue(targetPage);
		//Assert.assertEquals(targetPage, true,"Login failed...");
		//the 3rd parameter will printed in console if the test case got failed
		}catch(Exception e) {
			Assert.fail();
		}
		logger.info("**** Finished TC02_LoginTest******");

	}
}
