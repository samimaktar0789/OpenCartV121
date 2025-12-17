package utilities;

import java.awt.Desktop;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener
{
	public ExtentSparkReporter sparkReporter;//UI of the report
	public ExtentReports extent;//populate common info on the report
	public ExtentTest test;//creating test case entries in the report and update the status of the methods
	String reportName;
	
	
	public void onStart(ITestContext context) {
		/*SimpleDateFormat df=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		Date dt=new Date();
		String currentdatetimestamp=df.format(dt);*/	
		
		String timeStamp=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		reportName="Test-Report-"+timeStamp+".html";
		
	    sparkReporter=new ExtentSparkReporter(System.getProperty("user.dir")+"\\reports\\"+reportName);//Specify location of the file
	    sparkReporter.config().setDocumentTitle("Opencart Automation Report");//Title of the report
	    sparkReporter.config().setReportName("Opencart Functional Test");//name of the report
	    sparkReporter.config().setTheme(Theme.DARK);
	    
	    extent=new ExtentReports();
	    extent.attachReporter(sparkReporter);
	    extent.setSystemInfo("Application","Opencart");
	    extent.setSystemInfo("Module","Admin");
	    extent.setSystemInfo("Sub Module","Customers");
	    extent.setSystemInfo("User Name",System.getProperty("user.name"));
	    extent.setSystemInfo("Environment","QA");
	    
	    //If we sent any parameter from xml file then use this below code for get these value dynamically
	    String osName=context.getCurrentXmlTest().getParameter("os");
	    extent.setSystemInfo("OS", osName);
	    String browserName=context.getCurrentXmlTest().getParameter("browser");
	    extent.setSystemInfo("Browser",browserName);
	    
	    //If you add the include group name 
	    List<String> includeGroups=context.getCurrentXmlTest().getIncludedGroups();
	    if(!includeGroups.isEmpty()) {
	    		extent.setSystemInfo("Groups",includeGroups.toString());
	    }
	 }
	 
	 public void onTestSuccess(ITestResult result) {
		 test=extent.createTest(result.getTestClass().getName());//create a new test entry in the report with the current test class name
		 test.assignCategory(result.getMethod().getGroups()); //to display the group in report
		 test.log(Status.PASS,result.getName()+" got successfully executed");//update status P/F/S
		
	}
	
	 public void onTestFailure(ITestResult result) {
		 test=extent.createTest(result.getTestClass().getName());//create a new test entry in the report with the current test class name
		 test.assignCategory(result.getMethod().getGroups()); //to display the group in report
		 test.log(Status.FAIL,result.getName()+" Got failed");
		 test.log(Status.INFO, result.getThrowable().getMessage());
		 
		 //Capture the failure screenShote:
		 try {
			 //BaseClass bs=new BaseClass();
			 //String imgPath=bs.captureScreen(result.getName());
			 String imgPath=new BaseClass().captureScreen(result.getName());//captureScreen is a BaseClass method which is use for take screen shot
			 test.addScreenCaptureFromPath(imgPath);
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
	 
	 }
	 public void onTestSkipped(ITestResult result) {
		 test=extent.createTest(result.getTestClass().getName());//create a new test entry in the report with the current test class name
		 test.assignCategory(result.getMethod().getGroups()); //to display the group in report
		 test.log(Status.SKIP,result.getName()+" Got skipped");
		 test.log(Status.INFO, result.getThrowable().getMessage());
	 
	 }
	 public void onFinish(ITestContext context) {
		 extent.flush();
		 
		 //If you want to open the extent report file automatically 
		 String pathOfExtentReport=System.getProperty("user.dir")+"\\reports\\"+reportName;
		 File extentReport=new File(pathOfExtentReport);
		 try {
			Desktop.getDesktop().browse(extentReport.toURI()); 
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
	 }

}
