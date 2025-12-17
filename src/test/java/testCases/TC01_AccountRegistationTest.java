package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC01_AccountRegistationTest extends BaseClass{
	
	@Test(groups={"Regression","Master"})
	public void Verify_account_registation() {
		logger.info("*** Staring TC01_AccountRegistationTest ***");
		try {
		HomePage hp=new HomePage(driver);
		hp.clickMyAccount();
		logger.info("Click my Account Link....");
		hp.clickRegister();
		logger.info("Click Register Link.....");
		
		
		AccountRegistationPage reg=new AccountRegistationPage(driver);
		
		logger.info("Providing customer details....");
		reg.setFirstName(randomString().toUpperCase());
		reg.setLastName(randomString().toUpperCase());
		reg.setEmail(randomAlphanumaric()+"@gmail.com");
		reg.setTelephone(randomNumber());
		String password=randomAlphanumaric();
		reg.setPassword(password);
		reg.setConfirmPassword(password);
		reg.setPrivacy();
		reg.clickContinueBtn();
		
		logger.info("Validating expected result...");
		Assert.assertEquals(reg.getConfirmationMsg(),"Your Account Has Been Created!");
		}catch(Exception e) {
			logger.error("The test case is failed....");
			logger.debug("debug logged.......");
			Assert.fail();
		}

		logger.info("*** Finished TC01_AccountRegistationTest ***");
	}
	
}
;