package org.eim.search;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringSearchApplication.class)
@WebIntegrationTest("server.port:8070")
public class SpringSearchApplicationTests {

	private RestTemplate restTemplate = new RestTemplate();

	@Test
	public void contextLoads() {
	}

}
