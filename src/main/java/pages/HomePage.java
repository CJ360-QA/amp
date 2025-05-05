package pages;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import baseClass.BaseClass;

public class HomePage extends BaseClass{
	
	public HomePage() {
		PageFactory.initElements(driver,this);
	}
	
	
	@FindBy(xpath="//div[@type='text']") WebElement txtUser;
	@FindBy(xpath="//a//span[contains(@type, 'text')]") WebElement txtpass;
	@FindBy(xpath="//span[starts-with(@class, 'text')]") WebElement sub;
	@FindBy(xpath="//input[starts-with()='text']") List<WebElement> dd ;
	@FindBy(xpath="//span[text()='text']") WebElement lips ;
	@FindBy(xpath="//input[@type='name' and @class='text]") WebElement nam;
	@FindBy(xpath="//span[@type='text' or @class='text']") WebElement iin;
	@FindBy(css="a[type='text']") WebElement yel;
	
	
	
	public void signup(String name, String pass) {
		txtUser.sendKeys(name);
		txtpass.sendKeys(pass);
		sub.click();
		Select slt=new Select(lips);
		slt.selectByVisibleText("indina");
	}
	
	public void verifyTitle() {
		Assert.assertEquals(driver.getTitle(), "chinmaya");
		Assert.assertTrue(iin.getText().contains(prop.getProperty("username")));
	}

}
