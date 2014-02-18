package gov.samhsa.consent2share.selenium;

import com.thoughtworks.selenium.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.server.RemoteControlConfiguration;
import org.openqa.selenium.server.SeleniumServer;

public class SeleniumIntegrationTest {
	private Selenium selenium;
	 @Before
	 public void setUp() throws Exception {
	  selenium = new DefaultSelenium("localhost", 4444, "*firefox", "https://localhost:8444/");
	  selenium.start();
	 }
	
	@Test
	public void test2() throws Exception {
		selenium.open("/consent2share/");
		selenium.type("id=j_username", "albert.smith");
		selenium.type("id=j_password", "P@rr0tf1$h");
		selenium.click("id=loginButton");
		selenium.waitForPageToLoad("30000");
		selenium.click("//body[@id='myhome-page']/div/header/div/div/div[2]/a[2]/span[2]");
		selenium.click("link=Logout");
		selenium.waitForPageToLoad("30000");
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
