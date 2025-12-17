package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC03_LoginDDT extends BaseClass{
	@Test(dataProvider="LoginData",dataProviderClass=DataProviders.class,groups= {"DataDriven","Master"})//getting data provider from different class
	public void verify_loginDDT(String username,String password,String exp) {

		logger.info("**** Starting TC03_LoginDDT********");
		try {
			
			//Home Page
			HomePage hp=new HomePage(driver);
			hp.clickMyAccount();
			hp.clickLogin();
			
			//Login Page
			LoginPage lg=new LoginPage(driver);
			lg.setEmail(username);
			lg.setPassword(password);
			lg.clickLoginBtn();
			
			//MyAccount page
			MyAccountPage myAccount=new MyAccountPage(driver);
			boolean targetPage=myAccount.isMyAccountExists();
			
			/**
			 * Data is valid - login success - test pass -logout
			 * 				   login failed - test fail
			 * Data is invalid -login success - test fail - logout
			 * 					login failed - test pass
			 */
			
			if(exp.equalsIgnoreCase("Valid")) {
				if(targetPage) {
					myAccount.clickLogout();
					Assert.assertTrue(true);
				}else {
					Assert.fail();
				}
			}
			if(exp.equalsIgnoreCase("Invalid")) {
				if(targetPage) {
					myAccount.clickLogout();
					Assert.fail();
				}else {
					Assert.assertTrue(true);
				}
			}
		}catch(Exception e) {
			Assert.fail();
		}
		logger.info("**** Finished TC03_LoginDDT*****");

	}
}
