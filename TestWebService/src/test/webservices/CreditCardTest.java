package webservices;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import utils.exitmanager.ExitManagerMock;


public class CreditCardTest {
	private CreditCard cardService ;
	
	@Before
	public void setUp(){
		cardService = new CreditCard(1,1);
	}

	@Test
	public void shouldReturnCorrectAnswer() {
		cardService.setFailstopProbability(0);
		cardService.setFaultyResponseProbability(0);
		
		boolean expectedResponse = true;
		
		assertEquals("Normal execution should return true", cardService.verifyCreditAvailability() , expectedResponse);
		
	}	
	@Test
	public void shouldReturnRandomAnswer() {
		
		cardService.setFailstopProbability(0);
		cardService.setFaultyResponseProbability(1);
		
		boolean expectedResponse = true;
		boolean gotWrongAnswers = false;
		
		
		for(int i=0; i<20; i++){
			if (cardService.verifyCreditAvailability() != expectedResponse)
				gotWrongAnswers = true;
		}
		assertTrue("Should eventually return a wrong answers", gotWrongAnswers);
	}
	
	@Test
	public void shouldEventuallyFailstop(){
		cardService.setFailstopProbability(1);
		cardService.setFaultyResponseProbability(0);
		
		ExitManagerMock exitManagerMockup = new ExitManagerMock();
		cardService.setExitManager(exitManagerMockup);
		
		cardService.verifyCreditAvailability();
		
		assertTrue("Exit Manager should have been called to exit gracefully", exitManagerMockup.exitWasCalled);
	}
	

}
