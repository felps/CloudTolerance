package evaluation;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.apache.log4j.Logger;

@WebService
public class VoidProxy{
	
	Logger log;
	public int invoked = 0;
	
	public VoidProxy(Logger log) {
		this.log = log;
	}
	@WebMethod
	public void playRole(int parameter, int key) {
		log.info("voidProxy was invoked for key " + key);
		invoked ++;
	}
}