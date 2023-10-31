package com.fabricefo.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void testApp() {
		DemoApplication myApp = new DemoApplication();

		String result = myApp.getStatus();

		assertEquals("OK", result);

	}
}
