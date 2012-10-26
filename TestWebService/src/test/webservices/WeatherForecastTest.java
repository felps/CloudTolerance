package webservices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import utils.exitmanager.ExitManagerMock;

public class WeatherForecastTest {
	private WeatherForecast weatherService;

	@Before
	public void setUp() {
		weatherService = new WeatherForecast(1,1);
	}

	@Test
	public void shouldReturnCorrectAnswer() {
		weatherService.setFailstopProbability(0);
		weatherService.setFaultyResponseProbability(0);

		int expectedResponse = 25;

		assertEquals("Normal execution should return 25 for location BSB",
				weatherService.getTemperatureForecast("BSB"), expectedResponse);

		expectedResponse = 30;

		assertEquals("Normal execution should return 30 for anything else",
				weatherService.getTemperatureForecast("GRU"), expectedResponse);

	}

	@Test
	public void shouldReturnRandomAnswer() {

		weatherService.setFailstopProbability(0);
		weatherService.setFaultyResponseProbability(1);

		int expectedResponse = 25;
		boolean gotWrongAnswers = false;

		for (int i = 0; i < 20; i++) {
			if (weatherService.getTemperatureForecast("BSB") != expectedResponse)
				gotWrongAnswers = true;
		}
		assertTrue("Should eventually return a wrong answer", gotWrongAnswers);
	}

	@Test
	public void shouldEventuallyFailstop() {
		weatherService.setFailstopProbability(1);
		weatherService.setFaultyResponseProbability(0);

		ExitManagerMock exitManagerMockup = new ExitManagerMock();
		weatherService.setExitManager(exitManagerMockup);

		weatherService.getTemperatureForecast("BSB");

		assertTrue("Exit Manager should have been called to exit gracefully",
				exitManagerMockup.exitWasCalled);
	}

}
