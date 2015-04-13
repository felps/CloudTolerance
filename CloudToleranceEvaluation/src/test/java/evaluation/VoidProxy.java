package evaluation;

import java.util.HashMap;
import java.util.TreeMap;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.apache.log4j.Logger;

@WebService
public class VoidProxy{
	
	Logger log;
	public int invoked = 0;
	public TreeMap<Integer, Long> startingTime;
	public HashMap<Integer, Long> endingTimes;
	
	public VoidProxy(Logger log) {
		this.log = log;
		startingTime = new TreeMap<Integer, Long>();
		endingTimes = new HashMap<Integer, Long>();
	}
	@WebMethod
	public void playRole(int parameter, int key) {
		log.info("voidProxy was invoked for key " + key);
		invoked ++;
		endingTimes.put(key, System.currentTimeMillis());
	}
}