package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.text.RandomStringGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {
	public static WebDriver driver;
	public Logger logger;
	public Properties p;
	@BeforeClass(groups= {"Sanity","Regression","Master"})
	@Parameters({"os","browser"})
	public void setUp(String os,String br) throws IOException {
		//Loading config.properties file:
		FileReader file=new FileReader("./src//test//resources//config.properties");
		p=new Properties();
		p.load(file);
		
		logger=LogManager.getLogger(this.getClass());//this is use for generate the logger file dynamically
		
		//For remote execution:
		if(p.getProperty("execution_env").equalsIgnoreCase("remote")) {
			 // Create capability holder
		    MutableCapabilities cap;

		    switch (br.toLowerCase()) {
		        case "chrome":
		            ChromeOptions chrome = new ChromeOptions();
		            chrome.setPlatformName(os);
		            cap = chrome;
		            break;

		        case "firefox":
		            FirefoxOptions firefox = new FirefoxOptions();
		            firefox.setPlatformName(os);
		            cap = firefox;
		            break;

		        case "edge":
		            EdgeOptions edge = new EdgeOptions();
		            edge.setPlatformName(os);		            
		            cap = edge;
		            break;

		        default:
		            System.out.println("Invalid browser...");
		            return;
		    }
			//the session url:
			String sessUrl="http://localhost:4444/wd/hub";
			
			//open driver:
			 // Use URI.create() and convert to URL
            URI gridUri = URI.create(sessUrl);
			driver=new RemoteWebDriver(gridUri.toURL(),cap);
		}
		
		if(p.getProperty("execution_env").equalsIgnoreCase("local")) {
			switch(br.toLowerCase()) {
			case "chrome":driver=new ChromeDriver();break;
			case "firefox":driver=new FirefoxDriver();break;
			case "edge":driver=new EdgeDriver();break;
			default:System.out.println("This is invalid browser..");return;
			
			}
		}
		
		
		driver.manage().deleteAllCookies(); 
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		//driver.get("https://tutorialsninja.com/demo/index.php?route=account/success");
		
		//Open the url from properties file url:
		driver.get(p.getProperty("appURL"));
	}
	@AfterClass(groups= {"Sanity","Regression","Master"})
	public void tearDown() throws InterruptedException {
		Thread.sleep(2000);
		driver.quit();
	}

	public String randomString() {
		RandomStringGenerator generator=new RandomStringGenerator.Builder()
				.withinRange('a','z')
				.build();
		String randomData=generator.generate(8);
		return randomData;
	}
	public String randomNumber() {
		RandomStringGenerator generator=new RandomStringGenerator.Builder()
				.withinRange('8','9')
				.build();
		String firstDigit=generator.generate(1);
		
		RandomStringGenerator generateRem=new RandomStringGenerator.Builder()
				.withinRange('0','9')
				.build();
		String remaningDigit=generateRem.generate(9);
		
		return firstDigit+remaningDigit;
	}
	public String randomAlphanumaric() {
		RandomStringGenerator generator=new RandomStringGenerator.Builder()
				.withinRange('a','z')
				.build();
		String randomData=generator.generate(10);
		RandomStringGenerator generateNum=new RandomStringGenerator.Builder()
				.withinRange('0','9')
				.build();
		String randomNumber=generateNum.generate(5);
		return randomData+randomNumber;
	}
	
	public String captureScreen(String testName) {
		String timeStamp=new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts=(TakesScreenshot)driver;
		File sourceFile=ts.getScreenshotAs(OutputType.FILE);
		String targetFilePath=System.getProperty("user.dir")+"\\screenshots\\"+testName+"_"+timeStamp+".png";
		File targetFile=new File(targetFilePath);
		sourceFile.renameTo(targetFile);
		
		return targetFilePath;
	}
}
