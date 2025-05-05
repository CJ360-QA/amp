package utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import baseClass.BaseClass;

public class Listeners implements ITestListener{

	
	
	public ExtentSparkReporter spark;
	public ExtentReports extent;
	public ExtentTest test;
	
	String fileName;

	
	
	@Override
	public void onStart(ITestContext context) {
	
		String time=new SimpleDateFormat("dd.MM.yyyy.hh.mm.ss").format(new Date());
		fileName="myreport"+time+".html";
		
		spark= new ExtentSparkReporter(System.getProperty("user.dir")+"\\reports\\"+fileName);
		spark.config().setDocumentTitle("QA Reports");
		spark.config().setReportName("function name");
		spark.config().setTheme(Theme.DARK);
		
		extent=new ExtentReports();
		extent.attachReporter(spark);
		extent.setSystemInfo("env", "QA");
		extent.setSystemInfo("browser", "chrome");
		extent.setSystemInfo("OS", "window10");
	
		
	}
	
	
	@Override
	public void onTestSuccess(ITestResult result) {
		test= extent.createTest(result.getName());
		test.log(Status.PASS,"passed"+ result.getName());
	}

	@Override
	public void onTestFailure(ITestResult result) {
		test=extent.createTest(result.getName());
		test.log(Status.FAIL," failed"+ result.getName());
		test.log(Status.INFO," reason"+ result.getThrowable());
	
	try {
		String imgPath=new BaseClass().captureScreen(result.getName());
		test.addScreenCaptureFromPath(imgPath);
	}catch (IOException e) {
		e.printStackTrace();
	}
	
	
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		test=extent.createTest(result.getName());
		test.log(Status.SKIP," skipped" +result.getName());
		test.log(Status.INFO,"  "+ result.getThrowable());
	}



	@Override
	public void onFinish(ITestContext context) {
		extent.flush();
	}
	
	
	
}
