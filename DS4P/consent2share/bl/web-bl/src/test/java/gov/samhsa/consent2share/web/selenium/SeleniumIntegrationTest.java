package gov.samhsa.consent2share.web.selenium;

import static org.junit.Assert.*;

import com.thoughtworks.selenium.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("deprecation")
public class SeleniumIntegrationTest {
	private Selenium selenium;
	 @Before
	 public void setUp() throws Exception {
	
	  selenium = new DefaultSelenium("localhost", 4444, "firefox C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe", "https://localhost:8444/");
	  selenium.start();
	 }

	@Test
	public void login_logout() throws Exception {
		selenium.open("/consent2share/");
		selenium.type("id=j_username", "albert.smith");
		selenium.type("id=j_password", "P@rr0tf1$h");
		selenium.click("id=loginButton");
		selenium.waitForPageToLoad("30000");
		selenium.click("//body[@id='myhome-page']/div/header/div/div/div[2]/a[2]/span[2]");
		selenium.click("link=Logout");
		selenium.waitForPageToLoad("30000");
		selenium.close();
	}
	
	@Test
	public void provider_search() throws Exception {
		selenium.open("/consent2share/");
		selenium.type("id=j_username", "albert.smith");
		selenium.type("id=j_password", "P@rr0tf1$h");
		selenium.click("id=loginButton");
		selenium.waitForPageToLoad("30000");
		selenium.click("css=#sidenav_providers > a > span.hidden-md");
		selenium.waitForPageToLoad("30000");
		selenium.click("id=add-provider-btn");
		selenium.waitForPageToLoad("30000");
		selenium.type("id=zip_code", "97006");
		selenium.type("id=last_name", "thomas");
		selenium.click("id=searchButton");
		Thread.sleep(10000);
		assertTrue(selenium.isElementPresent("//div[@id='resultList']/div/div/div/p/span[2]"));
		selenium.click("css=#provider_search_modal > div.modal-dialog > div.modal-content > div.modal-header > button.close");
		selenium.click("css=#sidenav_home > a > span.hidden-md");
		selenium.waitForPageToLoad("30000");
		selenium.click("//body[@id='myhome-page']/div/header/div/div/div[2]/a/span[2]");
		selenium.click("link=Logout");
		selenium.waitForPageToLoad("30000");
		selenium.close();
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
