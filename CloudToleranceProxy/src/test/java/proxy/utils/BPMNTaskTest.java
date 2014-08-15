package proxy.utils;

import static org.junit.Assert.*;

import org.junit.Test;

import proxy.choreography.BPMNTask;

public class BPMNTaskTest {

	@Test
	public void test() {
		BPMNTask task1 = new BPMNTask("name", "http://0.0.0.0:2430/choreography",
				"http://127.0.0.1:2431/proxy?wsdl", "addOne",
				"http://127.0.0.1:2401/Linear?wsdl" );
		assertEquals("http://0.0.0.0:2430/choreography", task1.getEndpoint());
		assertEquals("http://127.0.0.1:2431/proxy?wsdl", task1.getNextLink());
		assertEquals("http://127.0.0.1:2401/Linear?wsdl", task1.getProvidingServicesWsdlList().get(0));
		assertEquals("addOne", task1.getMethodName());
		assertEquals("name", task1.getName());
		
		
	}

}
