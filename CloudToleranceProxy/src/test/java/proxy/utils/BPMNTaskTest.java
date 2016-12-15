package proxy.utils;

import static org.junit.Assert.*;

import org.junit.Test;

import br.ime.usp.improv.proxy.choreography.BPMNTask;

public class BPMNTaskTest {

	@Test
	public void test() {
		BPMNTask task1 = new BPMNTask("name",
				"http://0.0.0.0:2430/choreography",
				"http://127.0.0.1:2431/proxy?wsdl", "addOne",
				"http://127.0.0.1:2401/Linear?wsdl");
		assertEquals("http://0.0.0.0:2430/choreography", task1.getEndpoint());
		assertEquals("http://127.0.0.1:2431/proxy?wsdl", task1.getNextLink());
		assertEquals("http://127.0.0.1:2401/Linear?wsdl", task1
				.getProvidingServicesWsdlList().get(0));
		assertEquals("addOne", task1.getMethodName());
		assertEquals("name", task1.getName());

	}

	@Test
	public void shouldReturnCorrectPort() {
		BPMNTask task1 = new BPMNTask("", "http://0.0.0.0:2430/choreography",
				"", "", "");
		assertEquals("2430", task1.getPort());
		task1 = new BPMNTask("", "http://0.0.0.0:8080/choreography",
				"", "", "");
		assertEquals("8080", task1.getPort());
		task1 = new BPMNTask("", "http://0.0.0.0:4200/choreography",
				"", "", "");
		assertEquals("4200", task1.getPort());
		task1 = new BPMNTask("", "http://0.0.0.0:010/choreography",
				"", "", "");
		assertEquals("010", task1.getPort());
		task1 = new BPMNTask("", "http://0.0.0.0:80/choreography",
				"", "", "");
		assertEquals("80", task1.getPort());
	}
}
