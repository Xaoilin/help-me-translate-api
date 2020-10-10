package com.xaoilin.translate;

import com.xaoilin.translate.config.TestConfig;
import com.xaoilin.translate.database.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = {TestConfig.class})
class HelpMeTranslateApplicationTests {

	@Test
	void contextLoads() {
	}

}
