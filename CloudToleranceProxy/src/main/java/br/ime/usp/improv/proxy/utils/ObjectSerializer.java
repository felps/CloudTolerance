package br.ime.usp.improv.proxy.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ObjectSerializer {

	public static byte[] serialize(Serializable obj) throws IOException {
	    ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    ObjectOutputStream os = new ObjectOutputStream(bos);
	    os.writeObject(obj);

	    byte[] data = bos.toByteArray();
	    os.close();
		return data;
	}
	
	public static Object deserialize(byte[] serial) throws IOException, ClassNotFoundException {
		ByteArrayInputStream bis = new ByteArrayInputStream(serial);
	    ObjectInputStream oInputStream = new ObjectInputStream(bis);
	    Object restored = oInputStream.readObject();            
	    oInputStream.close();
		return restored;
	    
	}
}
