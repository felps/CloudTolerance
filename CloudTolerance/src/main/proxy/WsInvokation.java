package proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

import utils.ResultSetter;

public class WsInvokation implements Runnable {

	public ResultSetter result;
	public String wsEndpoint;
	public String wsdlClazzName;
	public Object serviceHandler;
	public Client client;
	public String wsdlUrl = wsEndpoint + "?wsdl";
	public String serviceMethod;
	public String paramMethod;
	public String paramClassName;
	public String paramValue;

	public void setWsdlUrl(String wsdlUrl) {
		this.wsdlUrl = wsdlUrl;
	}

	public void setResultSetter(ResultSetter resultSetter) {
		this.result = resultSetter;
	}

	public void prepareForInvoke() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException {

		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		client = dcf.createClient(wsdlUrl);

		serviceHandler = Thread.currentThread().getContextClassLoader()
				.loadClass(wsdlClazzName).newInstance();
	}

	public void setUpParameter() throws NoSuchMethodException,
			SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, ClassNotFoundException {

		if (paramMethod != null) {
			Method m = serviceHandler.getClass().getMethod(paramMethod,
					Thread.currentThread().getContextClassLoader().loadClass(paramClassName));
			m.invoke(serviceHandler, paramValue);
		}

	}

	public Object[] invoke() {
		Object[] response = null;
		try {
			setUpParameter();
			response = client.invoke(serviceMethod, paramValue);
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

		return response;
	}

	public void run() {
		Object[] response;
		response = invoke();
		result.result = response;
	}
}
