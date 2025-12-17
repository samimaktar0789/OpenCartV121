package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountRegistationPage extends BasePage{
	WebDriver driver;
	public AccountRegistationPage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(xpath="//input[@id='input-firstname']")
	WebElement txtFirstname;
	@FindBy(xpath="//input[@id='input-lastname']")
	WebElement txtLastname;
	@FindBy(xpath="//input[@id='input-email']")
	WebElement txtEmail;
	@FindBy(xpath="//input[@id='input-telephone']")
	WebElement txtTelephone;
	@FindBy(xpath="//input[@id='input-password']")
	WebElement txtPassword;
	@FindBy(xpath="//input[@id='input-confirm']")
	WebElement txtConfirmPassword;
	@FindBy(xpath="//input[@type='checkbox']")
	WebElement chkdPolicy;
	@FindBy(xpath="//input[@value='Continue']")
	WebElement btnContinue;
	
	@FindBy(xpath="//h1[normalize-space()='Your Account Has Been Created!']")
	WebElement msgConfirmation;
	
	public void setFirstName(String firstName) {
		txtFirstname.sendKeys(firstName);
	}
	
	public void setLastName(String lastName) {
		txtLastname.sendKeys(lastName);
	}
	public void setEmail(String email) {
		txtEmail.sendKeys(email);
	}

	public void setTelephone(String telephone) {
		txtTelephone.sendKeys(telephone);
	}
	public void setPassword(String password) {
		txtPassword.sendKeys(password);
	}
	public void setConfirmPassword(String confirmPassword) {
		txtConfirmPassword.sendKeys(confirmPassword);
	}
	public void setPrivacy() {
		chkdPolicy.click();
	}
	public void clickContinueBtn() {
		//sol-1:
		btnContinue.click();
		/*
		//sol-2:
		btnContinue.submit();
		
		//sol-3:
		Actions act=new Actions(driver);
		act.moveToElement(btnContinue).click().perform();
		
		//sol-4:
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("arguments[0].click()", btnContinue);
		
		//sol-5:
		WebDriverWait myWait=new WebDriverWait(driver,Duration.ofSeconds(10));
		myWait.until(ExpectedConditions.elementToBeClickable(btnContinue)).click();
		
		//sol-6:
		btnContinue.sendKeys(Keys.RETURN);
		*/ 
	}
	
	public String getConfirmationMsg() {
		try {
			return(msgConfirmation.getText());
		}catch(Exception e) {
			return(e.getMessage());
		}
	}
	
}
