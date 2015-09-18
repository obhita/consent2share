package com.feisystems.polrep;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.feisystems.polrep.config.PolRepApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PolRepApplication.class)
@WebAppConfiguration
public class PolRepApplicationTests {

	@Test
	public void contextLoads() {
	}

}
