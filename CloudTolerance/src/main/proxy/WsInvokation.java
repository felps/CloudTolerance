package proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

import utils.ResultSetter;

public abstract class WsInvokation implements Runnable {

	private ResultSetter result;
	private String wsEndpoint;
	private String wsdlClazzName;
	private Object serviceHandler ;
	private Client client;
	private String wsdlUrl = wsEndpoint+"?wsdl";
	
	public void setWsdlUrl(String wsdlUrl) {
		this.wsdlUrl = wsdlUrl;
	}

	public void setResultSetter(ResultSetter resultSetter) {
		this.result = resultSetter;
	}

	public void prepareForInvoke() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		client = dcf.createClient(wsdlUrl);

		serviceHandler = Thread.currentThread().getContextClassLoader()
				.loadClass(wsdlClazzName).newInstance();
	}

	public void setUpParameter(String paramMethod, String param) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		Method m = serviceHandler.getClass().getMethod(paramMethod, String.class);
		m.invoke(serviceHandler, param);

	}
	
	public void invoke(String serviceMethod, String param, String paramMethod) {
		try {
			setUpParameter(paramMethod, param);
			client.invoke(serviceMethod, serviceHandler);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return;
	}
}
