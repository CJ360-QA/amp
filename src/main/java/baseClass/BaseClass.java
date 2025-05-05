package baseClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {

	
	public static Properties prop;
	public static WebDriver driver;
	public static Logger logger;
	
	@BeforeClass
	public void setup() {
		logger=LogManager.getLogger(this.getClass());
		
		if(driver==null) {
			try {
				prop=new Properties();
				FileInputStream fis=new FileInputStream("C:\\Users\\chinm\\OneDrive\\Desktop\\mainPractice\\AMP\\src\\main\\resources\\configProps\\config.properties");
				prop.load(fis);
			} catch(IOException e) {
				e.printStackTrace();
			}
			
			String browser=prop.getProperty("browser");
			
			if(browser.equalsIgnoreCase("chrome")) {
				WebDriverManager.chromedriver().setup();
				
				ChromeOptions opt=new ChromeOptions();
				opt.addArguments("--start-maximized");
//				opt.addArguments("--headless");
//				opt.addArguments("--incognito");
				
				driver=new ChromeDriver(opt);
			}
			else if(browser.equalsIgnoreCase("edge")) {
				WebDriverManager.edgedriver().setup();
				
				EdgeOptions opt=new EdgeOptions();
				opt.addArguments("--headless");
				
				driver=new EdgeDriver(opt);
			}
			else {
				throw new RuntimeException("invalid Driver"+ browser);
			}
			driver.get(prop.getProperty("url"));
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			driver.manage().deleteAllCookies();
		}
	}
	
	@AfterClass
	public void teardown() {
		if(driver != null) {
			driver.quit();
			System.out.println("********session end********");
		}
	}
	
	public String captureScreen(String ss) throws IOException{
		String timeStamp=new SimpleDateFormat("dd.MM.yyyy.hh.mm.ss").format(new Date());
		
		TakesScreenshot ts=(TakesScreenshot)driver;
		File src=ts.getScreenshotAs(OutputType.FILE);
		
		String fileDrop=System.getProperty("user.dir")+"\\screenshots\\"+ss+timeStamp+".png";
		File trg=new File(fileDrop);
		
		FileUtils.copyFile(src, trg);
return(fileDrop);
	}
	
	
	@DataProvider (name="dp")
	public Object [][]loginData(){
		
		Object data[][]= {{"chinmaya@gmail.com", "password"},
				{"chinmaya@gmail.com", "password"},
				{"chinmayakabi33@gmail.com", "Chinmaya@1234"},
				{"chinmaya@gmail.com", "password"},
				{"chinmaya@gmail.com", "password"},
		};
		return data;
	}
	
	
}
